FROM maven:3.9.9-eclipse-temurin-21 AS deps

WORKDIR /root/app
COPY backend/transaction_server/pom.xml transaction_server/pom.xml
COPY backend/transaction_client/pom.xml transaction_client/pom.xml

COPY ./backend/pom.xml .

# if you have modules that depends each other, you may use -DexcludeArtifactIds as follows
RUN mvn \
    -B \
    -e \
    -C org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline \
    -DexcludeArtifactIds=backend

FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /root/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /root/app/ /root/app
COPY backend/transaction_server/pom.xml /root/app/transaction_server
COPY backend/transaction_server/.mvn /root/app/transaction_server/.mvn
COPY backend/transaction_server/mvnw /root/app/transaction_server
COPY backend/transaction_server/src /root/app/transaction_server/src

# use -o (--offline) if you didn't need to exclude artifacts.
# if you have excluded artifacts, then remove -o flag
RUN mvn \
    -f /root/app/transaction_server/pom.xml \
    clean install \
    -DskipTests

# At this point, BUILDER stage should have your .jar or whatever in some path
FROM eclipse-temurin:21-jdk
WORKDIR /root
COPY --from=builder /root/app/transaction_server/target/transaction_server-1.0.1.jar .

EXPOSE 8080

CMD [ "java", "-jar", "/root/transaction_server-1.0.1.jar" ]
