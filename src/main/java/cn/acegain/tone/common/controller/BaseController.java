package cn.acegain.tone.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class BaseController {

    protected HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new NullPointerException("Unable to obtain the servlet request!");
        }
        if (!(attributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            throw new IllegalStateException("Unable to parse the servlet request!");
        }
        return servletRequestAttributes.getRequest();
    }

    protected HttpServletResponse getResponse() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new NullPointerException("Unable to obtain the servlet response!");
        }
        if (!(attributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            throw new IllegalStateException("Unable to parse the servlet response!");
        }
        return servletRequestAttributes.getResponse();
    }

}
