package cn.acegain.tone.base.security;

import cn.acegain.tone.base.jwt.JwtService;
import cn.acegain.tone.common.constant.Jose;
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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken unAuthToken = (JwtAuthenticationToken) authentication;
        String token = unAuthToken.getToken();
        // 验证 token 有效性,防止Token伪造;
        JWT jwt = jwtService.parse(token);
        if (!jwt.verify()) {
            throw new InvalidJwtTokenException("无效的 JWT Token");
        }
        // TODO 检查服务请求渠道、Token 颁发渠道、用户可访问渠道是否一致;
        // TODO 检测用户状态；如注销、冻结、限制等;
        String account = jwt.getPayload(Jose.Payload.SUBJECT).toString();
        // 查询用户信息，查询不到时抛出 UsernameNotFoundException 异常。
        AuthDetails authDetails = authService.loadUserByUsername(account);
        // 创建已经认证 authentication 对象并返回；
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
