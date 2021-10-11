package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetRolesDTO;
import bigdata.filesystem.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RequestMapping("/role")
@RestController
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * @Description: 创建角色
     * @param: [roleUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2020/12/15 16:36
     */
    @RightRequired
    @Description("创建角色")
    @ApiOperation("创建角色")
    @PostMapping("/create")
    public Response createRole(@RequestBody RoleCreateDTO roleCreateDTO) {
        BeanValidators.validateWithException(validator, roleCreateDTO);
        roleService.createRole(roleCreateDTO);
        return respSuccessResult(BaseMessage.ROLE_CREATE_SUCCESSS.getMsg());
    }


    /**
     * @Description: 更新角色
     * @param: [RoleUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @author: lirunyi
     * @date: 2021/1/10 11:23
     */
    @RightRequired
    @PostMapping("/update")
    @Description("更新角色")
    @ApiOperation("更新角色")
    public Response updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO) {

        BeanValidators.validateWithException(validator, roleUpdateDTO);

        roleService.updateRole(roleUpdateDTO);

        return respSuccessResult(BaseMessage.ROLE_UPDATE_SUCCESS.getMsg());

    }

    /**
     * @Description: 冻结角色
     * @param: [roleId, updateRole]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/10 17:39
     */
    @RightRequired
    @PostMapping("/freeze")
    @Description("冻结角色")
    @ApiOperation("冻结角色")
    public Response freezeRole(@RequestBody RoleFreezeDTO roleFreezeDTO) {
        BeanValidators.validateWithException(validator, roleFreezeDTO);
        roleService.freezeRole(roleFreezeDTO);
        return respSuccessResult(BaseMessage.FREEZE_ROLE_SUCCESS.getMsg());
    }

    /**
     * @Description: 解冻角色
     * @param: [roleId, updateRole]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/10 17:41
     */
    @RightRequired
    @PostMapping("/unfreeze")
    @Description("解冻角色")
    @ApiOperation("解冻角色")
    public Response unfreezeRole(@RequestBody RoleUnfreezeDTO roleUnfreezeDTO) {
        BeanValidators.validateWithException(validator, roleUnfreezeDTO);
        roleService.unfreezeRole(roleUnfreezeDTO);
        return respSuccessResult(BaseMessage.UNFREEZE_ROLE_SUCCESS.getMsg());
    }

    /**
     * @Description: 删除角色
     * @param: [roleId, updateUser]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-11 17:13
     */
    @RightRequired
    @PostMapping("/delete")
    @Description("删除角色")
    @ApiOperation("删除角色")
    public Response deleteRole(@RequestBody RoleDeleteDTO roleDeleteDTO) {
        roleService.deleteRole(roleDeleteDTO);
        return respSuccessResult(BaseMessage.DELETE_ROLE_SUCCESS.getMsg());
    }

    @RightRequired
    @GetMapping("/getRoles")
    @Description("查询角色")
    @ApiOperation("查询角色")
    public Response getRoles(GetRolesDTO getRolesDTO) {
        Object result = roleService.getRoles(getRolesDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

}
