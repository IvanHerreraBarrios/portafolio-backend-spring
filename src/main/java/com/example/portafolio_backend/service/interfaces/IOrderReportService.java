package com.example.portafolio_backend.service.interfaces;

import net.sf.jasperreports.engine.JRException;

public interface IOrderReportService {
    public byte[] generateOrderPdf(Long orderId, String customerEmail) throws JRException;
}
