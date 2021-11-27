package com.bjfu.fcro.config.threadpools.scheduletask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 从yml文件中读取定时任务线程池配置参数
 * */

@ConfigurationProperties(prefix = "task.pool")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Primary
public class TaskThreadPoolConfig {

    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;

    private String threadNamePrefix;

}
