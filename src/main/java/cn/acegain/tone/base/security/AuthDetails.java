package cn.acegain.tone.base.security;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Slf4j
@Data
@Accessors(chain = true)
public class AuthDetails implements UserDetails {

    /**
     * 认证渠道；
     * Web端、Android端、ios端、小程序等
     */
    private String channel;

    /**
     * 认证模式
     * 密码、邮箱、短信、链接
     */
    private String mode;

    private String name;

    private String status;

    private String account;

    private String password;

    private String phone;

    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

}
