package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.UserCatalogRightBindDTO;
import bigdata.filesystem.dto.UserCatalogRightUnbindDTO;
import bigdata.filesystem.dto.query.GetCatalogRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetCatalogRightsDTO;
import bigdata.filesystem.service.CatalogRightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RequestMapping("/user/catalog/right")
@RestController
public class UserCatalogRightController extends BaseController {
    @Autowired
    private CatalogRightService catalogRightService;

    /**
     * @Description: 用户解绑目录权限
     * @param: [userCatalogRightUnbindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021-01-19 19:05
     */
    @RightRequired
    @Description("用户解绑目录权限")
    @ApiOperation("用户解绑目录权限")
    @PostMapping("/unbind")
    public Response userCatalogRightUnbind(@RequestBody UserCatalogRightUnbindDTO userCatalogRightUnbindDTO) {
        BeanValidators.validateWithException(validator, userCatalogRightUnbindDTO);
        catalogRightService.userCatalogRightUnbind(userCatalogRightUnbindDTO);
        return respSuccessResult(BaseMessage.USER_CATALOG_RIGHT_UNBIND_SUCCESS.getMsg());
    }

    /**
     * @Description: 用户绑定目录权限
     * @param: [userCatalogRightBindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021-01-19 19:08
     */
    @RightRequired
    @Description("用户绑定目录权限")
    @ApiOperation("用户绑定目录权限")
    @PostMapping("/bind")
    public Response userCatalogRightBind(@RequestBody UserCatalogRightBindDTO userCatalogRightBindDTO) {
        BeanValidators.validateWithException(validator, userCatalogRightBindDTO);
        catalogRightService.userCatalogRightBind(userCatalogRightBindDTO);
        return respSuccessResult(BaseMessage.USER_CATALOG_RIGHT_BIND_SUCCESS.getMsg());
    }

    @RightRequired
    @GetMapping("/getCatalogRights")
    @Description("查询用户目录权限")
    @ApiOperation("查询用户目录权限")
    public Response getCatalogRights(GetCatalogRightsDTO getCatalogRightsDTO) {
        Object result = catalogRightService.getCatalogRights(getCatalogRightsDTO);
        return respSuccessResult(BaseMessage.SUCCESS_GET_ALL.getMsg(), result);
    }

    @RightRequired
    @PostMapping("/getCatalogRights4CheckBox")
    @Description("查询用户目录权限")
    @ApiOperation("查询用户目录权限")
    public Response getCatalogRights4CheckBox(@RequestBody GetCatalogRights4CheckBoxDTO getCatalogRights4CheckBoxDTO) {
        Object result = catalogRightService.getCatalogRights4CheckBox(getCatalogRights4CheckBoxDTO);
        return respSuccessResult(BaseMessage.SUCCESS_GET_ALL.getMsg(), result);
    }
}
