package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.UserRoleBindDTO;
import bigdata.filesystem.dto.UserRoleUnbindDTO;
import bigdata.filesystem.dto.query.GetUserRolesDTO;
import bigdata.filesystem.service.UserRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RequestMapping("/user/role")
@RestController
public class UserRoleController extends BaseController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * @Description: 用户绑定角色
     * @param: [UserRoleBindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-12 10:23
     */
    @RightRequired
    @PostMapping("/bind")
    @Description("用户绑定角色")
    @ApiOperation("用户绑定角色")
    public Response userRoleBind(@RequestBody UserRoleBindDTO userRoleBindDTO) {
        BeanValidators.validateWithException(validator, userRoleBindDTO);
        userRoleService.userRoleBind(userRoleBindDTO);
        return respSuccessResult(BaseMessage.USER_ROLE_BIND_SUCCESS.getMsg());
    }

    /**
     * @Description: 用户解绑角色
     * @param: [userRoleUnbindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-13 21:27
     */
    @RightRequired
    @PostMapping("/unbind")
    @Description("用户解绑角色")
    @ApiOperation("用户解绑角色")
    public Response userRoleUnbind(@RequestBody UserRoleUnbindDTO userRoleUnbindDTO) {
        BeanValidators.validateWithException(validator, userRoleUnbindDTO);
        userRoleService.userRoleUnbind(userRoleUnbindDTO);
        return respSuccessResult(BaseMessage.USER_ROLE_UNBIND_SUCCESS.getMsg());
    }

    @RightRequired
    @GetMapping("/getUserRoles")
    @Description("查询用户角色绑定")
    @ApiOperation("查询用户角色绑定")
    public Response getUserRoles(GetUserRolesDTO getUserRolesDTO) {
//        BeanValidators.validateWithException(validator, userRoleUnbindDTO);
        Object result = userRoleService.getUserRoles(getUserRolesDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

}
