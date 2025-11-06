# Usamos JDK 21 Alpine
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado
COPY build/libs/users-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto que usa Spring Boot
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]
