package xyz.xcyd.wechat.offiaccount.config;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;

/**
 *
 */
//@Configuration
@ImportResource("classpath:ureport-console-context.xml")
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor());
    }


    @Bean
    public AuthInterceptor authInterceptor() {
        System.out.println("初始化Auth");
        return new AuthInterceptor();
    }

    @Bean
    public ServletRegistrationBean<Servlet> registrationBean() {
        return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
    }
}
