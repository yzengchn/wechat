package xyz.xcyd.wechat.offiaccount.proxy;

import com.google.common.collect.ImmutableMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * 代理HTTP请求
 */
@Configuration
@ConfigurationProperties(prefix = "config")
public class ProxyServletConfiguration {

    private Map<String, String> httpProxy;

    public void setHttpProxy(Map<String, String> httpProxy){
        this.httpProxy = httpProxy;
    }


    @Bean
    public Servlet createProxyServlet() {
        // 我在这里实现自己的业务逻辑
        return new BusinessProxyServlet();
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), httpProxy.get("servletUrl"));
        Map<String, String> params = ImmutableMap.of("targetUri", httpProxy.get("targetUrl"), "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean registration(HiddenHttpMethodFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

}
