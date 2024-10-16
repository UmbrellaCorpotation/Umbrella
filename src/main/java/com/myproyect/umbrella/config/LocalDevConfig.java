package com.myproyect.umbrella.config;


import java.io.File;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
@Profile("local") // Esta configuración solo se aplicará cuando el perfil 'local' esté activo
public class LocalDevConfig {

    @SneakyThrows
    public LocalDevConfig(final TemplateEngine templateEngine) {
        final ClassPathResource applicationYml = new ClassPathResource("application.yml");
        if (applicationYml.isFile()) {
            File sourceRoot = applicationYml.getFile().getParentFile();
            // Busca la raíz del proyecto donde está el archivo mvnw o mvnw.cmd
            while (sourceRoot.listFiles((dir, name) -> name.equals("mvnw")).length != 1) {
                sourceRoot = sourceRoot.getParentFile();
            }
            // Configura Thymeleaf para que cargue plantillas desde el sistema de archivos
            final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
            fileTemplateResolver.setPrefix(sourceRoot.getPath() + "/src/main/resources/templates/home");
            fileTemplateResolver.setSuffix(".html");
            fileTemplateResolver.setCacheable(false);
            fileTemplateResolver.setCharacterEncoding("UTF-8");
            fileTemplateResolver.setCheckExistence(true);
            templateEngine.setTemplateResolver(fileTemplateResolver);
        }
    }
}
