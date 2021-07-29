package xyz.xcyd.wechat.offiaccount.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 */
@Slf4j
@Configuration
@EnableAsync
@AllArgsConstructor
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnExpression("${config.thread.enabled:false}")
public class ThreadPoolConfig {

    private ThreadPoolProperties properties;

    /**
     * 初始化异步线程池
     * @return
     */
    @Bean
    public Executor asyncServicePool() {

        VisiableThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.properties.getCorePoolSize());
        executor.setMaxPoolSize(this.properties.getMaxPoolSize());
        executor.setQueueCapacity(this.properties.getQueueCapacity());
        executor.setKeepAliveSeconds(this.properties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(String.format("%s-", this.properties.getThreadPrefix()));

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        log.info("初始化异步线程池完成");
        return executor;
    }

    class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

        private void showThreadPoolInfo(String prefix) {
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

            if (null == threadPoolExecutor) {
                return;
            }

            log.info("{}: {},任务总数 [{}], 已完成数 [{}], 活跃线程数 [{}], 队列大小 [{}]",
                    this.getThreadNamePrefix(),
                    prefix,
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        }

        @Override
        public void execute(Runnable task) {
            showThreadPoolInfo("1.execute");
            super.execute(task);
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            showThreadPoolInfo("2.execute");
            super.execute(task, startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            showThreadPoolInfo("1.submit");
            return super.submit(task);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            showThreadPoolInfo("2.submit");
            return super.submit(task);
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            showThreadPoolInfo("1.submitListenable");
            return super.submitListenable(task);
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            showThreadPoolInfo("2.submitListenable");
            return super.submitListenable(task);
        }
    }

}