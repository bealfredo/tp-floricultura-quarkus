FROM eclipse-temurin:17-jre as runtime
WORKDIR /work/
COPY target/quarkus-app/ /work/quarkus-app/
# COPY src/main/resources/import.sql /work/quarkus-app/quarkus/import.sql
# Garantimos que o arquivo também estará no classpath padrão
# COPY src/main/resources/import.sql /work/quarkus-app/import.sql
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/work/quarkus-app/quarkus-run.jar"]