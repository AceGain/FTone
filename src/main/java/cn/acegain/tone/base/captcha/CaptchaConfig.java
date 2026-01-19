package cn.acegain.tone.base.captcha;

import cn.hutool.captcha.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfig {

    @Bean
    public AbstractCaptcha captcha(CaptchaProperties properties) {
        int width = properties.getWidth();
        int height = properties.getHeight();
        int length = properties.getLength();
        int stain = properties.getStain();
        CaptchaType type = properties.getType();
        return switch (type) {
            case Circle -> new CircleCaptcha(width, height, length, stain);
            case Shear -> new ShearCaptcha(width, height, length, stain);
            case Gif -> new GifCaptcha(width, height, length, stain);
            default -> new LineCaptcha(width, height, length, stain);
        };
    }

}