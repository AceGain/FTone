package cn.acegain.tone.base.security;

import cn.acegain.tone.base.jwt.JwtService;
import cn.acegain.tone.common.constant.Jose;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    private final AuthService authService;

    private final TimedCache<String, Object> tokenCache;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken unAuthToken = (JwtAuthenticationToken) authentication;
        String token = unAuthToken.getToken();

        // 1. 解析 JWT 获取 jti
        JWT jwt = jwtService.parse(token);
        String jti = jwt.getPayload(Jose.Payload.JWT_ID).toString();

        // 2. 检查缓存并刷新 - null 则拒绝
        if (tokenCache.get(jti) == null) {
            throw new InvalidJwtTokenException("认证过期！");
        }

        // 3. 验证签名
        if (!jwtService.verify(token)) {
            throw new InvalidJwtTokenException("认证无效！");
        }

        // 4. 验证过期
        if (!jwtService.validate(token)) {
            throw new InvalidJwtTokenException("认证失效！");
        }

        // 5. 查询用户
        String account = jwt.getPayload(Jose.Payload.SUBJECT).toString();
        AuthDetails authDetails = authService.loadUserByUsername(account);

        // 6. 检查用户状态
        // TODO 检查服务请求渠道、Token 颁发渠道、用户可访问渠道是否一致;
        // TODO 检测用户状态；如注销、冻结、限制等;

        // 7. 刷新 Token
        jwtService.refresh(token);

        String channel = jwt.getPayload(Jose.Payload.AUDIENCE).toString();
        return JwtAuthenticationToken.authenticated(channel, token, authDetails.getAuthorities());
    }

    /**
     * 指定此 Provider 支持的 Authentication 类型
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
