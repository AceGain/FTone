package cn.acegain.tone.base.security;

import cn.acegain.tone.system.entity.SysUser;
import cn.acegain.tone.system.service.SysUserService;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Override
    public AuthDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = QueryWrapper.create().eq(SysUser::getAccount, username);
        SysUser user = userService.getOne(queryWrapper);
        AuthDetails authDetails = new AuthDetails();
        authDetails.setName(user.getName());
        authDetails.setStatus(user.getStatus());
        authDetails.setAccount(user.getAccount());
        authDetails.setPassword(user.getPassword());
        authDetails.setEmail(user.getEmail());
        authDetails.setPhone(user.getPhone());
        return authDetails;
    }

}
