package bigdata.filesystem.config;

//import bigdata.filesystem.comn.utils.FileUtil;
import bigdata.filesystem.entity.DmUser;
import bigdata.filesystem.service.RightService;
import bigdata.filesystem.service.TokenService;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.power.common.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RightService rightService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        DmUser dmUser = null;
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (!passToken.required()) {
                // 执行认证
                dmUser = tokenService.checkToken(token);
            }
        }
//        //检查有没有需要用户权限的注解
//        else if (method.isAnnotationPresent(LoginRequired.class)) {
//            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
//            if (loginRequired.required()) {
//                // 执行认证
//                tokenService.checkToken(token);
//            }
//        }
        else {
            // 执行认证
            dmUser = tokenService.checkToken(token);
        }

        if (method.isAnnotationPresent(RightRequired.class)) {
            RightRequired passToken = method.getAnnotation(RightRequired.class);
            if (passToken.required()) {
                // 成功获取到用户后校验接口权限
                String url = httpServletRequest.getRequestURI();
                Long catalogId = null;
                Long fileId = null;
                String catalogIdStr = httpServletRequest.getParameter("catalogId");
                if (StringUtils.isBlank(catalogIdStr)) {
                    catalogIdStr = httpServletRequest.getParameter("parentCatalogId");
                    if (StringUtils.isNotBlank(catalogIdStr)) {
                        catalogId = Long.valueOf(catalogIdStr);
                    }
                } else {
                    catalogId = Long.valueOf(catalogIdStr);
                }
                String fileIdStr = httpServletRequest.getParameter("fileId");
                if (StringUtils.isNotBlank(fileIdStr)) {
                    fileId = Long.valueOf(fileIdStr);
                }

                // 获取输入
                if (null == catalogId && null == fileId) {
                    String input = "";
                    if (httpServletRequest != null) {
                        if (httpServletRequest instanceof ContentCachingRequestWrapper) {
                            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) httpServletRequest;
//                        String input = StringUtils.toEncodedString(wrapper.getContentAsByteArray(),
//                                Charset.forName(wrapper.getCharacterEncoding()));
//                        StringBuilder stringBuilder = new StringBuilder();
                            ServletInputStream inputStream = wrapper.getInputStream();
                            byte[] charBuffer = new byte[128];
                            while (inputStream.read(charBuffer) > 0) {
//                            stringBuilder.append(charBuffer, 0, bytesRead);
                            }
//                        String input = stringBuilder.toString();
                            input = StringUtils.toEncodedString(wrapper.getContentAsByteArray(),
                                    Charset.forName(wrapper.getCharacterEncoding()));
                        } else if (httpServletRequest instanceof MultipartHttpServletRequest) {
                            MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
                            Map<String, String[]> paramMap = request.getParameterMap();
                            if (!paramMap.isEmpty()) {
                                for (String key : paramMap.keySet()) {
                                    String[] paramValue = paramMap.get(key);
                                    if (paramValue.length >= 1) {
                                        input = paramValue[0];
                                    }
                                }
                            }
                        }
                        if (StringUtils.isNotEmpty(input)) {
                            // 设置操作人员和备注
//                    JSONTokener jsonTokener = new JSONTokener(input);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = JSONObject.parseObject(input);
                                if (null == catalogId) {
                                    Object catalogIdObj = jsonObject.get("parentCatalogId");
                                    catalogIdStr = null == catalogIdObj ? "" : catalogIdObj.toString();
                                    if (StringUtils.isNotBlank(catalogIdStr)) {
                                        catalogId = Long.valueOf(catalogIdStr);
                                    }
                                }
                                if (null == catalogId) {
                                    Object catalogIdObj = jsonObject.get("catalogId");
                                    catalogIdStr = null == catalogIdObj ? "" : catalogIdObj.toString();
                                    if (StringUtils.isNotBlank(catalogIdStr)) {
                                        catalogId = Long.valueOf(catalogIdStr);
                                    }
                                }
                                if (null == fileId) {
                                    Object fileIdObj = jsonObject.get("fileId");
                                    fileIdStr = null == fileIdObj ? "" : fileIdObj.toString();
                                    if (StringUtils.isNotBlank(fileIdStr)) {
                                        fileId = Long.valueOf(fileIdStr);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                rightService.checkRight(dmUser.getUserId(), UrlUtil.simplifyUrl(url), catalogId, fileId);
            }
        }
//        else {
//            // 成功获取到用户后校验接口权限
//            String url = httpServletRequest.getRequestURI();
//            Long catalogId = null;
//            Long fileId = null;
//            rightService.checkRight(user.getUserId(), url, catalogId, fileId);
//        }


        //检查有没有需要校验文件类型的注解
        if (method.isAnnotationPresent(ValidateFileType.class)) {
            ValidateFileType validateFileType = method.getAnnotation(ValidateFileType.class);
            if (validateFileType.required()) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httpServletRequest;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//                for (String key : fileMap.keySet()) {
//                    FileUtil.checkFileType(fileMap.get(key), validateFileType.fileTypes());
//                }
            }
        }
        return true;
    }
}
