package cn.acegain.tone;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ToneRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (context.isActive()) {
            String banner = "\n";
            banner += """
                    ------------------------------------------------
                    Server：Stone Web
                    DateTime：{}
                    ------------------------------------------------""";
            log.info(banner, DateUtil.now());
        }
    }
}
