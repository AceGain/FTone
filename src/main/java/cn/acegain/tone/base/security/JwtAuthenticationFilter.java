package cn.acegain.tone.base.security;

import cn.acegain.tone.common.Result;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("NullableProblems")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTH = "T-Auth";
    private static final String HEADER_CHANNEL = "T-Channel";

    private final AuthenticationManager authenticationManager;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            // 获取 JWT Token
            String token = request.getHeader(HEADER_AUTH);
            if (StrUtil.isNotBlank(token)) {
                String channel = request.getHeader(HEADER_CHANNEL);
                // 创建未认证的 JwtAuthenticationToken
                JwtAuthenticationToken noAuthToken = JwtAuthenticationToken.unauthenticated(channel, token);
                noAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 委托 AuthenticationManager 进行认证
                Authentication authToken = authenticationManager.authenticate(noAuthToken);
                if (authToken.isAuthenticated()) {
                    // 将已认证的 JwtAuthenticationToken 放入 SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            log.error("认证失败: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(JSONUtil.toJsonStr(Result.failure("401", "认证失败")));
            response.getWriter().flush();
        }
    }

}
