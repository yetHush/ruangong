package bigdata.filesystem.comn.base;

import bigdata.filesystem.comn.result.RespResult;
import bigdata.filesystem.comn.utils.FillerUtil;
import bigdata.filesystem.comn.utils.TokenUtil;
import bigdata.filesystem.dto.LoginResultDTO;
import bigdata.filesystem.entity.ActionLog;
import bigdata.filesystem.mapper.ActionLogMapper;
import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Date;

@Component
@Slf4j
public class BaseController {

    @Autowired
    protected Validator validator;
    @Autowired
    private ActionLogMapper actionLogMapper;

    private ConcurrentHashSet<String> fileUploadIdSet = new ConcurrentHashSet<>();

    /**
     *  * @description: 返回操作成功后的数据
     *  * @return
     */
    protected Response respSuccessResult(String msg) {
        RespResult respResult = new RespResult();
        respResult.setCode("000");
        respResult.setMsg(msg);
        FillerUtil.fillResult(respResult);
        successLog(msg, null);
        return Response.status(Status.OK).entity(respResult).build();
    }

    protected Response respSuccessResult(String msg, Object entity) {
        RespResult respResult = new RespResult();
        respResult.setCode("000");
        respResult.setMsg(msg);
        respResult.setEntity(entity);
        FillerUtil.fillResult(respResult);
        successLog(msg, entity);
        return Response.status(Status.OK).entity(respResult).build();
    }

    public void successLog(String msg, Object entity) {
        ActionLog actionLog = new ActionLog();
        String url = TokenUtil.getUrl();
        actionLog.setLogName(TokenUtil.getLogName(url));
        actionLog.setLogType(TokenUtil.getLogType());
        actionLog.setLineNum(TokenUtil.getLineNum(url));
        actionLog.setClazz(TokenUtil.getClazz(url));
        actionLog.setMethod(TokenUtil.getMethod(url));
        actionLog.setIpaddr(TokenUtil.getIp());
        actionLog.setBrowser(TokenUtil.getBrowser());
        actionLog.setOs(TokenUtil.getOS());
        actionLog.setUrl(url);
        actionLog.setMessage(msg);
        String userNum = null;
        try {
            userNum = TokenUtil.getUserNum();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        if (StringUtils.equalsIgnoreCase(url, "/user/login")) {
            LoginResultDTO loginResultDTO = (LoginResultDTO) entity;
            userNum = loginResultDTO.getUserNum();
        } else if (StringUtils.equalsIgnoreCase(url, "/file/upload/large/file")) {
            if (fileUploadIdSet.contains(entity.toString())) {
                return;
            } else {
                fileUploadIdSet.add(entity.toString());
            }
        } else if (StringUtils.equalsIgnoreCase(url, "/file/upload/large/file/info")) {
            fileUploadIdSet.remove(entity.toString());
        }
        actionLog.setCreateUser(userNum);
        Date now = new Date();
        actionLog.setCreateTime(now);
        actionLog.setStatus(BaseConstant.STATUS_NORMAL);
        actionLogMapper.insertSelective(actionLog);
    }
}
