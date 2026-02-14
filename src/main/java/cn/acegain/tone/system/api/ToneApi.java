package cn.acegain.tone.system.api;

import cn.acegain.tone.common.api.BaseApi;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ToneApi implements BaseApi {

    @Autowired
    protected Environment environment;

    @GetMapping("/")
    public String index() {
        return "启动成功🎉🎉🎉";
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("Name", environment.getProperty("spring.application.name"));
        info.put("Version", environment.getProperty("spring.application.version"));
        info.put("DateTime", DateUtil.now());
        return info;
    }

}
