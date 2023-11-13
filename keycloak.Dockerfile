FROM quay.io/keycloak/keycloak:latest as builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# change these values to point to a running postgres instance
ENV KC_DB=mysql
ENV KC_DB_URL=jdbc:mysql://database-1.cxlpgmyhdwip.us-east-1.rds.amazonaws.com:3306/keycloack?createDatabaseIfNotExist=true
ENV KC_DB_USERNAME=admin
ENV KC_DB_PASSWORD=QUanEooESeFCuhD74kxH
ENV KC_HOSTNAME=34.199.239.78

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]