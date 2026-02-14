package cn.acegain.tone.system.api;

import cn.acegain.tone.base.jwt.JwtService;
import cn.acegain.tone.base.security.AuthService;
import cn.acegain.tone.common.Result;
import cn.acegain.tone.common.api.BaseApi;
import cn.acegain.tone.system.entity.AuthForm;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
public class AuthApi implements BaseApi {

    @Autowired
    private TimedCache<String, Object> timedCache;

    @Autowired
    @Qualifier("tokenCache")
    private TimedCache<String, Object> tokenCache;

    @Autowired
    private AbstractCaptcha captchaService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/captcha")
    public Result<?> captcha() throws IOException {
        // 生成新的验证码
        captchaService.createCode();
        String code = captchaService.getCode();
        String uuid = IdUtil.fastUUID();
        // 缓存验证码，用于校验
        timedCache.put(uuid, code, Duration.ofMinutes(5).toMillis());
        log.info("uuid：{}，验证码：{}", uuid, code);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("uuid", uuid);
        data.put("code", code);
        data.put("image", captchaService.getImageBase64());
        return Result.success(data);
    }

    @PostMapping("/auth")
    public Result<?> auth(@RequestBody AuthForm authForm) {
//        Object code = timedCache.get(authForm.getUuid());
//        if (code == null) {
//            return Result.failure("300", "验证码已过期");
//        }
//        String codeStr = code.toString();
//        if (!StrUtil.equalsIgnoreCase(codeStr, authForm.getCode())) {
//            return Result.failure("300", "验证码错误");
//        }

        String uuid = IdUtil.fastUUID();
        authForm.setUuid(uuid);
        String token = jwtService.create(authForm);
        tokenCache.put(uuid, token, Duration.ofMinutes(10).toMillis());

        try{
            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(authForm.getAccount(), authForm.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure("401","username or password invalid");
        }
        return Result.success(token);
    }

}
