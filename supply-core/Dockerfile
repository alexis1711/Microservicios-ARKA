FROM eclipse-temurin:21.0.8_9-jdk-alpine

RUN jlink --no-header-files --no-man-pages --compress=zip-8 \
--strip-debug --add-modules java.base,java.logging,java.naming\
,java.management,java.instrument,java.desktop,java.security.jgss\
,jdk.unsupported --output /jre

FROM alpine:3.21.4
WORKDIR /app

COPY --from=0 /jre /jre
COPY build/libs/supply-core.jar app.jar

EXPOSE 8080

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["/jre/bin/java", "-jar", "app.jar"]