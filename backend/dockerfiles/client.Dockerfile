FROM maven:3.9.9-eclipse-temurin-21 AS deps

WORKDIR /root/app
COPY backend/transaction_server/pom.xml transaction_server/pom.xml
COPY backend/transaction_client/pom.xml transaction_client/pom.xml

COPY ./backend/pom.xml .

# if you have modules that depends each other, you may use -DexcludeArtifactIds as follows
#RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=spring_kafka
RUN mvn -B \
    -e \
    -C \
    org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline \
    -DexcludeArtifactIds=backend

# Copy the dependencies from the DEPS stage with the advantage
# of using docker layer caches. If something goes wrong from this
# line on, all dependencies from DEPS were already downloaded and
# stored in docker's layers.
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /root/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /root/app/ /root/app
COPY backend/transaction_client/pom.xml /root/app/transaction_client
COPY backend/transaction_client/.mvn /root/app/transaction_client/.mvn
COPY backend/transaction_client/mvnw /root/app/transaction_client
COPY backend/transaction_client/src /root/app/transaction_client/src

# use -o (--offline) if you didn't need to exclude artifacts.
# if you have excluded artifacts, then remove -o flag
RUN mvn \
    -f /root/app/transaction_client/pom.xml \
    clean install \
    -DskipTests

# At this point, BUILDER stage should have your .jar or whatever in some path
FROM eclipse-temurin:21-jdk
WORKDIR /root
COPY --from=builder /root/app/transaction_client/target/transaction_client-1.0.1.jar .

EXPOSE 8084

CMD [ "java", "-jar", "/root/transaction_client-1.0.1.jar" ]
