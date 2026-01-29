package cn.acegain.tone.base.jwt;

import cn.acegain.tone.common.constant.Jose;
import cn.acegain.tone.system.entity.AuthForm;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class JwtService {

    private final JwtProperties properties;

    private final String issuer;

    private final byte[] key;

    private final int offset;

    private final int leeway;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
        this.issuer = properties.getIssuer();
        this.key = properties.getKey();
        this.offset = properties.getOffset();
        this.leeway = properties.getLeeway();
    }

    public String create(AuthForm authForm) {
        JWT jwt = JWT.create();
        jwt.setPayload(Jose.Payload.ISSUER, this.issuer);
        jwt.setPayload(Jose.Payload.SUBJECT, authForm.getAccount());
        jwt.setPayload(Jose.Payload.AUDIENCE, authForm.getChannel());
        DateTime issuedAt = DateUtil.date();
        jwt.setPayload(Jose.Payload.ISSUED_AT, issuedAt);
        DateTime expiresAt = DateUtil.offsetMinute(issuedAt, this.offset);
        jwt.setPayload(Jose.Payload.EXPIRES_AT, expiresAt);
        jwt.setPayload(Jose.Payload.JWT_ID, authForm.getUuid());
        jwt.setKey(this.key);
        return jwt.sign();
    }

    public JWT parse(String token) {
        return JWT.of(token).setKey(this.key);
    }

    /**
     * 验证 JWT token 是否有效;
     *
     * @param token token
     * @return true/false
     */
    public boolean verify(String token) {
        return JWT.of(token).setKey(this.key).verify();
    }

    /**
     * 验证 JWT token 是否有效,且在有效期内;
     *
     * @param token token
     * @return true/false
     */
    public boolean validate(String token) {
        JWT jwt = JWT.of(token);
        jwt.setKey(this.key);
        return jwt.validate(0);
    }

    public void refresh(String token) {
        // TODO 刷新 JWT token
    }

}
