package cn.acegain.tone.system.controller;

import cn.acegain.tone.common.Result;
import cn.acegain.tone.common.controller.WebController;
import cn.acegain.tone.system.entity.SysUser;
import cn.acegain.tone.system.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/system/user")
public class SysUserController extends WebController<SysUser, SysUserService> {

    @GetMapping("/page")
    public Result<Page<SysUser>> page(SysUser sysUser) {
        return super.page(sysUser);
    }

}
