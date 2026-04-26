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

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    public String create(AuthForm authForm) {
        JWT jwt = JWT.create();
        jwt.setPayload(Jose.Payload.ISSUER, properties.getIssuer());
        jwt.setPayload(Jose.Payload.SUBJECT, authForm.getAccount());
        jwt.setPayload(Jose.Payload.AUDIENCE, authForm.getChannel());
        DateTime issuedAt = DateUtil.date();
        jwt.setPayload(Jose.Payload.ISSUED_AT, issuedAt);
        DateTime expiresAt = DateUtil.offsetMinute(issuedAt, properties.getOffset());
        jwt.setPayload(Jose.Payload.EXPIRES_AT, expiresAt);
        jwt.setPayload(Jose.Payload.JWT_ID, authForm.getUuid());
        jwt.setKey(properties.getKey());
        return jwt.sign();
    }

    public JWT parse(String token) {
        return JWT.of(token).setKey(properties.getKey());
    }

    /**
     * 验证 JWT token 是否有效;
     *
     * @param token token
     * @return true/false
     */
    public boolean verify(String token) {
        return JWT.of(token).setKey(properties.getKey()).verify();
    }

    /**
     * 验证 JWT token 是否有效,且在有效期内;
     *
     * @param token token
     * @return true/false
     */
    public boolean validate(String token) {
        return JWT.of(token).setKey(properties.getKey()).validate(properties.getLeeway());
    }

    public void refresh(String token) {
        // TODO 刷新 JWT token
    }

}
