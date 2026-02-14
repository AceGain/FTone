package cn.acegain.tone.common.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public interface BaseApi {

    default HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new NullPointerException("Unable to obtain the servlet request!");
        }
        if (!(attributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            throw new IllegalStateException("Unable to parse the servlet request!");
        }
        return servletRequestAttributes.getRequest();
    }

    default HttpServletResponse getResponse() {
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
