FROM gradle:jdk18 as builder
WORKDIR /usr/app
COPY . .
RUN gradle --no-daemon installDist

FROM ibm-semeru-runtimes:open-18-jre-focal

WORKDIR /usr/app
COPY --from=builder /usr/app/build/install/auth-service .

LABEL org.opencontainers.image.source = "https://github.com/Traewelldroid/auth-service/"

ENTRYPOINT ["/usr/app/bin/auth-service"]
