package cn.acegain.tone.base;

import cn.hutool.cache.impl.TimedCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.Duration;

@Slf4j
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfig {

    @Bean
    @Primary
    public TimedCache<String, Object> timedCache() {
        TimedCache<String, Object> timedCache = new TimedCache<>(Duration.ofMinutes(5).toMillis());
        timedCache.schedulePrune(10 * 1000);
        return timedCache;
    }

    @Bean
    @Qualifier("tokenCache")
    public TimedCache<String, Object> tokenCache() {
        TimedCache<String, Object> timedCache = new TimedCache<>(Duration.ofMinutes(5).toMillis());
        timedCache.schedulePrune(10 * 1000);
        return timedCache;
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> contextRefreshedListener() {
        return event -> {
            // TODO 缓存初始化
            //Cache cache = event.getApplicationContext().getBean("tokenCache", Cache.class);
        };
    }

    @Bean
    public ApplicationListener<ContextClosedEvent> contextClosedListener() {
        return event -> {
            // TODO 缓存持久化
        };
    }

}
