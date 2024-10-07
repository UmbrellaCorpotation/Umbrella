# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Variables de entorno para H2
ENV H2_VERSION=2.1.214
ENV H2_HOME=/opt/h2
ENV PATH=$H2_HOME/bin:$PATH

# Instalar wget, descargar H2 JAR, y limpiar
RUN apk add --no-cache wget && \
    mkdir -p $H2_HOME/bin && \
    wget -O /tmp/h2.jar https://repo1.maven.org/maven2/com/h2database/h2/$H2_VERSION/h2-$H2_VERSION.jar && \
    cp /tmp/h2.jar $H2_HOME/bin/h2-$H2_VERSION.jar && \
    rm /tmp/h2.jar && \
    apk del wget

# Establecer el directorio de trabajo
WORKDIR /opt/h2/bin

# Exponer los puertos necesarios
EXPOSE 8085 9095

# Comando para iniciar H2 con los puertos deseados y permitir la creación remota de bases de datos
CMD ["java", "-cp", "h2-2.1.214.jar", "org.h2.tools.Server", "-tcpPort", "9095", "-tcpAllowOthers", "-webPort", "8085", "-webAllowOthers", "-ifNotExists"]

