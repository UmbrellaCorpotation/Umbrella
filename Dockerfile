# Usar la imagen oficial de Tomcat
FROM tomcat:9.0.95-jdk17-temurin-jammy

# Eliminar la aplicación web predeterminada (opcional)
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copiar el archivo WAR generado al directorio de webapps de Tomcat
COPY ./target/data-analysis-system-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Exponer el puerto 8080
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
