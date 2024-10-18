package co.com.bancolombia.aplicacionbancaria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuditoriaInterceptor AUDITORIA_INTERCEPTOR;

    public WebConfig(AuditoriaInterceptor auditoriaInterceptor) {
        this.AUDITORIA_INTERCEPTOR = auditoriaInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(AUDITORIA_INTERCEPTOR);
    }
}
