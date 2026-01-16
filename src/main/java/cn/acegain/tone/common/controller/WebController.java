package cn.acegain.tone.common.controller;

import cn.acegain.tone.common.Result;
import cn.acegain.tone.common.entity.BaseEntity;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.web.bind.ServletRequestUtils;

import java.util.List;

@Slf4j
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unchecked"})
public class WebController<T extends BaseEntity, S extends IService<T>> extends BaseController {

    protected final Class<T> clazz;

    @Autowired
    protected S service;

    public WebController() {
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        ResolvableType baseControllerType = resolvableType.as(BaseController.class);
        this.clazz = (Class<T>) baseControllerType.getGeneric(0).resolve();
    }

    protected Page<T> buildPage() {
        HttpServletRequest request = super.getRequest();
        int pageNumber = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        return new Page<>(pageNumber, pageSize);
    }

    protected Result<T> save(T entity) {
        try {
            service.save(entity);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error();
        }
    }

    protected Result<T> removeById(Integer id) {
        try {
            service.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error();
        }
    }

    protected Result<T> updateById(T entity) {
        try {
            service.updateById(entity);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error();
        }
    }

    protected Result<List<T>> list(T entity) {
        try {
            QueryWrapper wrapper = QueryWrapper.create(entity);
            List<T> data = service.list(wrapper);
            return Result.success(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error();
        }
    }

    protected Result<Page<T>> page(T entity) {
        try {
            Page<T> page = this.buildPage();
            QueryWrapper wrapper = QueryWrapper.create(entity);
            Page<T> data = service.page(page, wrapper);
            return Result.success(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error();
        }
    }


}
