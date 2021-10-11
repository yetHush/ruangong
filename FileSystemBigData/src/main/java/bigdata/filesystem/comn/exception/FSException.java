package bigdata.filesystem.comn.exception;

import bigdata.filesystem.comn.base.BaseConstant;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.result.RespResult;
import bigdata.filesystem.comn.utils.FillerUtil;
import bigdata.filesystem.comn.utils.TokenUtil;
import bigdata.filesystem.config.ContentCachingRequestWrapper;
import bigdata.filesystem.entity.ActionLog;
import bigdata.filesystem.mapper.ActionLogMapper;
import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

@ControllerAdvice({"bigdata.filesystem"})
@Slf4j
public class FSException {

    @Value("${base.detail.rowCount}")
    private Integer detailRowCount;

    @Autowired
    private ActionLogMapper actionLogMapper;

    private ConcurrentHashSet<String> fileUploadIdSet = new ConcurrentHashSet<>();

    /**
     * <p>异常处理</p>
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ErrorMsgException.class)
    @ResponseBody
    public Response exceptionHandler(ErrorMsgException exception, HttpServletRequest req) {
        //记录异常
        this.errorLog(exception);
        if (exception.getRespResult() != null) {
            return Response.status(Status.SERVICE_UNAVAILABLE).entity(exception.getRespResult()).build();
        }
        RespResult result = new RespResult();
        result.setCode(BaseMessage.EXCEPTION.getMsg());
        result.setMsg(BaseMessage.NO_MESSAGE.getMsg());
        FillerUtil.fillResult(result);
        return Response.status(Status.SERVICE_UNAVAILABLE).entity(result).build();
    }

    /**
     * <p>异常处理</p>
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response exceptionHandler(RuntimeException exception) {
        exception.printStackTrace();
        RespResult result = this.getRespResult(exception);
        FillerUtil.fillResult(result);
        //记录异常
        this.errorLog(exception);
        return Response.status(Status.SERVICE_UNAVAILABLE).entity(result).build();
    }

    /**
     * <p>异常处理</p>
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception exception) {
        exception.printStackTrace();
        RespResult result = this.getRespResult(exception);
        FillerUtil.fillResult(result);
        //记录异常
        this.errorLog(exception);
        return Response.status(Status.SERVICE_UNAVAILABLE).entity(result).build();
    }

    /**
     * <p>异常处理</p>
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Response exceptionHandler(ConstraintViolationException exception) {
        exception.printStackTrace();
        RespResult result = this.getRespResult(exception);
        FillerUtil.fillResult(result);
        //记录异常
        this.errorLog(exception);
        return Response.status(Status.SERVICE_UNAVAILABLE).entity(result).build();
    }

    /**
     * 获取ErrorCodeResult，将Exception中的消息放到detail中，将Message放到msg中
     *
     * @param exception
     * @return
     */
    private RespResult getRespResult(Exception exception) {
        RespResult result = new RespResult();
        result.setCode(BaseMessage.EXCEPTION.getMsg());
        result.setMsg(exception.getMessage());
        return result;
    }

    /**
     * 打印数据到detail字段中，仅打印固定行数的数据
     *
     * @param exception
     */
    private String getDetail(Exception exception) {
        StringBuilder sb = new StringBuilder(exception + "\n");
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        for (int index = 0; index < detailRowCount && index < stackTraceElements.length; index++) {
            StackTraceElement stackTraceElement = stackTraceElements[index];
            sb.append("\tat " + stackTraceElement + "\n");
        }
        log.error("{}", exception.getMessage(), exception);
        return sb.toString();
    }

    private void errorLog(Exception exception) {
        log.error("{}", exception.getMessage(), exception);
        ActionLog actionLog = new ActionLog();
        actionLog.setLogType(BaseConstant.LOG_TYPE_ERROR);
        String clazz = null;
        String method = null;
        String message = null;
        Integer lineNum = null;
        StackTraceElement stackTraceElement = null;
        if (exception instanceof ErrorMsgException) {
            stackTraceElement = exception.getStackTrace()[1];
            message = ((ErrorMsgException) exception).getRespResult().getMsg();
        } else {
            stackTraceElement = exception.getStackTrace()[0];
//            message = this.getDetail(exception);
            message = BaseMessage.NO_MESSAGE.getMsg();
        }
        clazz = stackTraceElement.getClassName();
        method = stackTraceElement.getMethodName();
        lineNum = stackTraceElement.getLineNumber();
        actionLog.setLineNum(lineNum);
        actionLog.setClazz(clazz);
        actionLog.setMethod(method);
        actionLog.setIpaddr(TokenUtil.getIp());
        actionLog.setBrowser(TokenUtil.getBrowser());
        actionLog.setOs(TokenUtil.getOS());
        String url = TokenUtil.getUrl();
        if (StringUtils.equalsIgnoreCase(url, "/upload/large/file") || StringUtils.equalsIgnoreCase(url, "/upload/large/file/info")) {
            String input = null;
            HttpServletRequest httpServletRequest = TokenUtil.getRequest();
            if (httpServletRequest instanceof ContentCachingRequestWrapper) {
                ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) httpServletRequest;
                ServletInputStream inputStream = null;
                try {
                    inputStream = wrapper.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                input = org.apache.commons.lang3.StringUtils.toEncodedString(wrapper.getContentAsByteArray(),
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
            String entity = null;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(input)) {
                // 设置操作人员和备注
//                    JSONTokener jsonTokener = new JSONTokener(input);
                JSONObject jsonObject = null;
                try {
                    jsonObject = JSONObject.parseObject(input);
                    Object fileIdObj = jsonObject.get("id");
                    entity = null == fileIdObj ? "" : fileIdObj.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.equalsIgnoreCase(url, "/file/upload/large/file")) {
                if (fileUploadIdSet.contains(entity)) {
                    return;
                } else {
                    fileUploadIdSet.add(entity);
                }
            } else if (StringUtils.equalsIgnoreCase(url, "/file/upload/large/file/info")) {
                fileUploadIdSet.remove(entity);
            }
        }
        actionLog.setUrl(url);
        actionLog.setLogName(TokenUtil.getLogName(url));
        actionLog.setMessage(message);
        actionLog.setStatus(BaseConstant.STATUS_NORMAL);
        String userNum = null;
        try {
            userNum = TokenUtil.getUserNum();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        actionLog.setCreateUser(userNum);
        Date now = new Date();
        actionLog.setCreateTime(now);
        actionLogMapper.insertSelective(actionLog);
    }
}
