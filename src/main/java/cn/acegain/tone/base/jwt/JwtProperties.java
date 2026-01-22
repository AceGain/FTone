package cn.acegain.tone.base.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("hutool.jwt")
public class JwtProperties {

    private String header;

    private String prefix;

    private String secret;

    private Long expire;

}
