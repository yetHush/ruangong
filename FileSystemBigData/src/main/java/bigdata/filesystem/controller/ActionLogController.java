package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.ActionLogDeleteDTO;
import bigdata.filesystem.dto.ActionLogUnfreezeDTO;
import bigdata.filesystem.dto.query.GetLogsDTO;
import bigdata.filesystem.entity.ActionLog;
import bigdata.filesystem.service.ActionLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

@RequestMapping("/log")
@RestController
public class ActionLogController extends BaseController {
    @Autowired
    private ActionLogService actionLogService;

    /**
     * @Description: 恢复日志
     * @param: [logId, updateUser]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2020/12/27 0:28
     */
    @RightRequired
    @PostMapping("/unfreeze")
    @Description("恢复日志")
    @ApiOperation("恢复日志")
    public Response unfreezeActionLog(@RequestBody ActionLogUnfreezeDTO actionLogUnfreezeDTO) {
        actionLogService.unfreezeActionLog(actionLogUnfreezeDTO);
        return respSuccessResult(BaseMessage.LOG_UNFREEZE_SUCCESS.getMsg());
    }


    /**
     * @Description: 删除日志（status设置）
     * @param: [logId, updateUser]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2020/12/27 0:28
     */
    @RightRequired
    @PostMapping("/delete")
    @Description("删除日志")
    @ApiOperation("删除日志")
    public Response deleteActionLog(@RequestBody ActionLogDeleteDTO actionLogDeleteDTO) {
        actionLogService.deleteActionLog(actionLogDeleteDTO);
        return respSuccessResult(BaseMessage.LOG_DELETE_SUCCESS.getMsg());
    }

    @RightRequired
    @PostMapping("/hard/delete")
    @Description("硬删除日志")
    @ApiOperation("硬删除日志")
    public Response hardDeleteActionLog(@RequestBody ActionLogDeleteDTO actionLogDeleteDTO) {
        actionLogService.hardDeleteActionLog(actionLogDeleteDTO);
        return respSuccessResult(BaseMessage.LOG_DELETE_SUCCESS.getMsg());
    }


    @RightRequired
    @GetMapping("/getLogs")
    @Description("查询日志")
    @ApiOperation("查询日志")
    public Response getLogs(GetLogsDTO getLogsDTO) {
        Object result = actionLogService.getLogs(getLogsDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

    @RightRequired
    @GetMapping("/getHomeData")
    @Description("查询首页数据")
    @ApiOperation("查询首页数据")
    public Response getHomeData() {
        Object result = actionLogService.getHomeData();
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

}
