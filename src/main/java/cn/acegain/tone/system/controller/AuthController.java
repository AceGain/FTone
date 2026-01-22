package cn.acegain.tone.system.controller;

import cn.acegain.tone.common.Result;
import cn.acegain.tone.common.controller.BaseController;
import cn.acegain.tone.system.entity.AuthAo;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController implements BaseController {

    private final AbstractCaptcha captcha;

    private final TimedCache<String, Object> timedCache;

    @GetMapping("/captcha")
    public Result<?> captcha() throws IOException {
        // 生成新的验证码
        captcha.createCode();
        String code = captcha.getCode();
        String uuid = IdUtil.fastUUID();
        // 缓存验证码，用于校验
        timedCache.put(uuid, code, Duration.ofMinutes(5).toMillis());
        log.info("uuid：{}，验证码：{}", uuid, code);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("uuid", uuid);
        data.put("code", code);
        data.put("image", captcha.getImageBase64());
        return Result.success(data);
    }

    @PostMapping("/auth")
    public Result<?> auth(@RequestBody AuthAo authAo) {
        Object code = timedCache.get(authAo.getUuid());
        if (code == null) {
            return Result.failure("300", "验证码已过期");
        }
        String codeStr = code.toString();
        if (!StrUtil.equalsIgnoreCase(codeStr, authAo.getCode())) {
            return Result.failure("300", "验证码错误");
        }

        return Result.success();
    }

}
