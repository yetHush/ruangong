package bigdata.filesystem.comn.utils;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.exception.ErrorMsgException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet工具类，获取当前request和response
 */

@Component
public class HttpServletUtil {

    /**
     * 获取当前请求的request对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ErrorMsgException(BaseMessage.REQUEST_EMPTY.getMsg());
        } else {
            HttpServletRequest httpServletRequest = requestAttributes.getRequest();
            return httpServletRequest;
        }
    }

    /**
     * 获取当前请求的response对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ErrorMsgException(BaseMessage.REQUEST_EMPTY.getMsg());
        } else {
            HttpServletResponse httpServletResponse = requestAttributes.getResponse();
            return httpServletResponse;
        }
    }
}
