package cn.acegain.tone.base.security;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("NullableProblems")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTH = "T-Auth";
    private static final String HEADER_CHANNEL = "T-Channel";

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 获取 JWT Token
            String token = request.getHeader(HEADER_AUTH);
            String channel = request.getHeader(HEADER_CHANNEL);
            if (StrUtil.isNotBlank(token)) {
                // 创建未认证的 JwtAuthenticationToken
                JwtAuthenticationToken noAuthToken = JwtAuthenticationToken.unauthenticated(channel, token);
                noAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 委托 AuthenticationManager 进行认证
                Authentication authToken = authenticationManager.authenticate(noAuthToken);
                if (authToken.isAuthenticated()){
                    System.out.println("认证成功!");
                }
                // 将已认证的 JwtAuthenticationToken 放入 SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            log.error("认证失败: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
    }

}
