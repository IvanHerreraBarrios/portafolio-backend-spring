# ===== STAGE 1: BUILD =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Solo pom primero (para cache de dependencias)
COPY pom.xml .

# 🔥 descarga dependencias una sola vez (cache layer)
RUN mvn dependency:go-offline -B

# 2. Ahora código fuente
COPY src ./src

# 3. Build sin limpiar todo siempre
RUN mvn package -DskipTests -B


# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=$PORT"]