package xyz.xcyd.wechat.offiaccount.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池属性
 */
@Data
@ConfigurationProperties(prefix = "config.thread")
public class ThreadPoolProperties {

    /**
     * 线程名字前缀
     */
    private String threadPrefix;

    /**
     * 核心线程池大小
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 队列容量
     */
    private int queueCapacity;

    /**
     * 活跃时间
     */
    private int keepAliveSeconds;


}
