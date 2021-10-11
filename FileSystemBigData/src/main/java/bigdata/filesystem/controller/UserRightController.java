package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.UserRightBindDTO;
import bigdata.filesystem.dto.UserRightUnbindDTO;
import bigdata.filesystem.dto.query.GetUserRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetUserRightsDTO;
import bigdata.filesystem.service.UserRightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RequestMapping("/user/right")
@RestController
public class UserRightController extends BaseController {
    @Autowired
    private UserRightService userRightService;

    /**
     * @Description: 用户解绑权限
     * @param: [userRightUnbindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-13 0:11
     */
    @RightRequired
    @PostMapping("/unbind")
    @Description("用户解绑权限")
    @ApiOperation("用户解绑权限")
    public Response userRightUnbind(@RequestBody UserRightUnbindDTO userRightUnbindDTO) {
        BeanValidators.validateWithException(validator, userRightUnbindDTO);
        userRightService.userRightUnbind(userRightUnbindDTO);
        return respSuccessResult(BaseMessage.USER_RIGHT_UNBIND_SUCCESS.getMsg());
    }

    /**
     * @Description: 用户绑定权限
     * @param: [userRightBindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-13 0:11
     */
    @RightRequired
    @PostMapping("/bind")
    @Description("用户绑定权限")
    @ApiOperation("用户绑定权限")
    public Response userRightBind(@RequestBody UserRightBindDTO userRightBindDTO) {
        BeanValidators.validateWithException(validator, userRightBindDTO);
        userRightService.userRightBind(userRightBindDTO);
        return respSuccessResult(BaseMessage.USER_RIGHT_BIND_SUCCESS.getMsg());
    }


    @RightRequired
    @GetMapping("/getUserRights")
    @Description("查询用户权限")
    @ApiOperation("查询用户权限")
    public Response getUserRights(GetUserRightsDTO getUserRightsDTO) {
        Object result = userRightService.getUserRights(getUserRightsDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

    @RightRequired
    @PostMapping("/getUserRights4CheckBox")
    @Description("查询用户权限")
    @ApiOperation("查询用户权限")
    public Response getUserRights4CheckBox(@RequestBody GetUserRights4CheckBoxDTO getUserRights4CheckBoxDTO) {
        Object result = userRightService.getUserRights4CheckBox(getUserRights4CheckBoxDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

}
