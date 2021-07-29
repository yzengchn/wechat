package xyz.xcyd.wechat.offiaccount.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置管理
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "config")
public class ProjectProperties {

    AppidPurpose purpose = new AppidPurpose();

    /**
     * 多公众号各自功能目的
     */
    @Data
    public class AppidPurpose {
        /**
         * 提醒服务（定时推送）
         */
        private String remind;
    }
}
