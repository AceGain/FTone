package cn.acegain.tone.base.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

@Data
@ConfigurationProperties("tone.jwt")
public class JwtProperties {

    private String issuer;

    private String secret;

    private int offset;

    private int leeway;

    public byte[] getKey() {
        Assert.notNull(this.secret, "secret cannot be null");
        return this.secret.getBytes(StandardCharsets.UTF_8);
    }

}
