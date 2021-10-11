package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.RoleRightBindDTO;
import bigdata.filesystem.dto.RoleRightUnbindDTO;
import bigdata.filesystem.dto.query.GetRoleRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetRoleRightsDTO;
import bigdata.filesystem.service.RoleRightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RequestMapping("/role/right")
@RestController
public class RoleRightController extends BaseController {
    @Autowired
    private RoleRightService roleRightService;

    /**
     * @Description: 角色解绑权限
     * @param: [roleRightUnbindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021-01-13 22:41
     */
    @RightRequired
    @Description("角色解绑权限")
    @ApiOperation("角色解绑权限")
    @PostMapping("/unbind")
    public Response roleRightUnbind(@RequestBody RoleRightUnbindDTO roleRightUnbindDTO) {
        BeanValidators.validateWithException(validator, roleRightUnbindDTO);
        roleRightService.roleRightUnbind(roleRightUnbindDTO);
        return respSuccessResult(BaseMessage.ROLE_RIGHT_UNBIND_SUCCESS.getMsg());
    }

    /**
     * @Description: 角色绑定权限
     * @param: [roleRightBindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021-01-13 22:25
     */
    @RightRequired
    @Description("角色绑定权限")
    @ApiOperation("角色绑定权限")
    @PostMapping("/bind")
    public Response roleRightBind(@RequestBody RoleRightBindDTO roleRightBindDTO) {
        BeanValidators.validateWithException(validator, roleRightBindDTO);
        roleRightService.roleRightBind(roleRightBindDTO);
        return respSuccessResult(BaseMessage.ROLE_RIGHT_BIND_SUCCESS.getMsg());
    }

    @RightRequired
    @GetMapping("/getRoleRights")
    @Description("查询角色权限")
    @ApiOperation("查询角色权限")
    public Response getRoleRights(GetRoleRightsDTO getRoleRightsDTO) {
        Object result = roleRightService.getRoleRights(getRoleRightsDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

    @RightRequired
    @PostMapping("/getRoleRights4CheckBox")
    @Description("查询角色权限")
    @ApiOperation("查询角色权限")
    public Response getRoleRights4CheckBox(@RequestBody GetRoleRights4CheckBoxDTO getRoleRights4CheckBoxDTO) {
        Object result = roleRightService.getRoleRights4CheckBox(getRoleRights4CheckBoxDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }
}
