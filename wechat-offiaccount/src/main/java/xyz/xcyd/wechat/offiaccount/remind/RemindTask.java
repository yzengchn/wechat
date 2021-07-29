package xyz.xcyd.wechat.offiaccount.remind;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务提醒
 */
@Slf4j
@Component
public class RemindTask implements DisposableBean, SmartInitializingSingleton {

    private static Map<String, ScheduledFuture> scheduledTasks = new ConcurrentHashMap<>(16);

    @Resource(name = "timingTask")
    private TaskScheduler taskScheduler;

    @Bean("timingTask")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("timing-");
        executor.setRemoveOnCancelPolicy(true);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("定时任务提醒初始化");
    }

    /**
     * 添加定时任务
     * @param task
     * @param cron
     * @param key
     * @return
     */
    public boolean addScheduledTask(Runnable task, String cron, String key) {
        String paramLog = String.format("定时任务Key:%s, cron:%s", key, cron);
        try {
            log.info("┌准备添加>>{}", paramLog);
            removeScheduledTask(key);
            CronTask cronTask = new CronTask(task, cron);
            scheduledTasks.put(key, this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger()));
            log.info("└添加完成>>{}", paramLog);
        }catch (Exception e) {
            log.error("添加定时任务异常{}", paramLog, e);
            return false;
        }
        return true;
    }


    /**
     * 移除定时任务
     * @param key
     */
    public static void removeScheduledTask(String key) {
        if(scheduledTasks.containsKey(key)) {
            ScheduledFuture scheduledFuture = scheduledTasks.remove(key);
            log.info("移除>>{}", key);
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
        }
    }


    @Override
    public void destroy() throws Exception {
        for (ScheduledFuture future : scheduledTasks.values()) {
            future.cancel(true);
        }
        scheduledTasks.clear();
    }
}
