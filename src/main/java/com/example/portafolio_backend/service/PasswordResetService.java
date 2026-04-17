package com.example.portafolio_backend.service;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.persistance.entity.PasswordResetTokenEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICustomerRepository;
import com.example.portafolio_backend.persistance.crud.IPasswordResetTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {



    private final IPasswordResetTokenRepository tokenRepo;

    private final ICustomerRepository userRepo;

    private final JavaMailSender mailSender;

    @Value("${app.frontend.reset-password-url}")
    private String RESET_PASSWORD_URL;

    @Value("${mail.from}")
    private String MAIL_FROM;

    @Transactional
    public void sendResetEmail(String email) {
        CustomerEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        tokenRepo.deleteAllByUser(user);

        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepo.save(resetToken);

        String resetUrl = RESET_PASSWORD_URL + "?token=" + token;

        // Creamos HTML con botón
        String htmlMsg = "<p>Hello " + user.getName() + ",</p>" +
                "<p>Click the button below to reset your password:</p>" +
                "<a href='" + resetUrl + "' " +
                "style='display:inline-block;padding:10px 20px;font-size:16px;" +
                "color:white;background-color:#D2691E;text-decoration:none;" +
                "border-radius:5px;'>Reset password</a>" +
                "<p>If you did not request this, you can ignore this email.</p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Reset password");
            helper.setText(htmlMsg, true); // true = HTML
            helper.setFrom(MAIL_FROM);

            mailSender.send(message);

        } catch (MessagingException e) {

            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }

    public void resetPassword(String token, String newPassword) {

        Optional<PasswordResetTokenEntity> opt = tokenRepo.findByToken(token);


        PasswordResetTokenEntity resetToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expired token");
        }

        CustomerEntity user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepo.save(user);

        tokenRepo.delete(resetToken); // una vez usado, borramos el token
    }
}
