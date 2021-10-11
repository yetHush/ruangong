package bigdata.filesystem.comn.utils;

import bigdata.filesystem.comn.base.BaseConstant;
import com.auth0.jwt.JWT;
import com.power.common.util.IpUtil;
import com.power.common.util.UrlUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

@Component
@Slf4j
public class TokenUtil {

    public static String getTokenUserNum() {
        String token = getRequest().getHeader("token");// 从 http 请求头中取出 token
        String userNum = JWT.decode(token).getAudience().get(0);
        return userNum;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 获取用户IP地址
     */
    public static String getIp() {
        HttpServletRequest request = TokenUtil.getRequest();
        // 反向代理时获取真实ip
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }

    public static String getBrowser() {

        HttpServletRequest request = TokenUtil.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        String browser = userAgent.getBrowser().toString();

        return browser;
    }

    public static String getOS() {
        HttpServletRequest request = TokenUtil.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        String os = userAgent.getOperatingSystem().toString();
        return os;
    }

    public static String getUrl() {
        HttpServletRequest request = TokenUtil.getRequest();
        return request.getRequestURI();
    }

    public static String getUserNum() {
        String userNum = TokenUtil.getTokenUserNum();
        if (StringUtils.isBlank(userNum)) {
            HttpServletRequest request = TokenUtil.getRequest();
            userNum = request.getHeader("userNum");
        }
        return userNum;
    }

    public static String getLogType() {
        HttpServletRequest request = TokenUtil.getRequest();
        String url = request.getRequestURI();
        if (url.contains("get")) {
            return BaseConstant.LOG_TYPE_GET;
        } else if (url.contains("create")) {
            return BaseConstant.LOG_TYPE_CREATE;
        } else if (url.contains("delete")) {
            return BaseConstant.LOG_TYPE_DELETE;
        } else if (url.contains("upload")) {
            return BaseConstant.LOG_TYPE_UPLOAD;
        } else {
            return BaseConstant.LOG_TYPE_UPDATE;
        }
    }

    public static String getLogName(String url) {
        url = UrlUtil.simplifyUrl(url);
        HttpServletRequest request = TokenUtil.getRequest();
        WebApplicationContext wc = TokenUtil.getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator
                .hasNext(); ) {
            RequestMappingInfo info = iterator.next();
            String targetUrl = info.getPatternsCondition().getPatterns().iterator().next();
            if (StringUtils.equalsIgnoreCase(url, targetUrl)) {
                HandlerMethod handlerMethod = map.get(info);
                Method method = handlerMethod.getMethod();
                if (method.isAnnotationPresent(Description.class)) {
                    Description description = method.getAnnotation(Description.class);
                    return description.value();
                } else if (method.isAnnotationPresent(ApiOperation.class)) {
                    ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                    return apiOperation.value();
                } else {
                    String methodName = handlerMethod.getMethod().getName();
                    return methodName;
                }
            }
        }
        return null;
    }

    public static String getClazz(String url) {
        HttpServletRequest request = TokenUtil.getRequest();
        WebApplicationContext wc = TokenUtil.getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator
                .hasNext(); ) {
            RequestMappingInfo info = iterator.next();
            String targetUrl = info.getPatternsCondition().getPatterns().iterator().next();
            if (StringUtils.equalsIgnoreCase(url, targetUrl)) {
                HandlerMethod handlerMethod = map.get(info);
                String clazz = handlerMethod.getMethod().getDeclaringClass().getName();
                return clazz;
            }
        }
        return null;
    }

    public static String getMethod(String url) {
        HttpServletRequest request = TokenUtil.getRequest();
        WebApplicationContext wc = TokenUtil.getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator
                .hasNext(); ) {
            RequestMappingInfo info = iterator.next();
            String targetUrl = info.getPatternsCondition().getPatterns().iterator().next();
            if (StringUtils.equalsIgnoreCase(url, targetUrl)) {
                HandlerMethod handlerMethod = map.get(info);
                String method = handlerMethod.getMethod().getName();
                return method;
            }
        }
        return null;
    }

    public static Integer getLineNum(String url) {
        HttpServletRequest request = TokenUtil.getRequest();
        WebApplicationContext wc = TokenUtil.getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator
                .hasNext(); ) {
            RequestMappingInfo info = iterator.next();
            String targetUrl = info.getPatternsCondition().getPatterns().iterator().next();
            if (StringUtils.equalsIgnoreCase(url, targetUrl)) {
                HandlerMethod handlerMethod = map.get(info);
                String clazz = handlerMethod.getMethod().getDeclaringClass().getName();
                ClassPool pool = ClassPool.getDefault();
                CtClass cc = null;
                try {
                    cc = pool.get(clazz);
                    String methodName = handlerMethod.getMethod().getName();
                    CtMethod methodX = cc.getDeclaredMethod(methodName);
                    Integer lineNumber = methodX.getMethodInfo().getLineNumber(0);
                    return lineNumber;
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }


    private static WebApplicationContext getWebApplicationContext(ServletContext sc) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
    }

}