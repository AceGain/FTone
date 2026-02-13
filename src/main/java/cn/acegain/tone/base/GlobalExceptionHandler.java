package cn.acegain.tone.base;

import cn.acegain.tone.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Order
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return Result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务异常！");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        Object code = HttpServletResponse.SC_BAD_REQUEST;
        String message = e.getMessage();
        if (message.contains("Required request body is missing")) {
            return Result.error(code, "请求体不能为空！");
        }
        log.error(message);
        return Result.error(code, "服务异常！");
    }

}
