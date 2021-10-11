package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.RightCreateDTO;
import bigdata.filesystem.dto.RightDeleteDTO;
import bigdata.filesystem.dto.RightUpdateDTO;
import bigdata.filesystem.dto.query.GetRightsDTO;
import bigdata.filesystem.service.RightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RequestMapping("/right")
@RestController
public class RightController extends BaseController {
    @Autowired
    private RightService rightService;

    /**
     * @Description: 创建权限
     * @param: [rightCreateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/12 11:22
     */
    @RightRequired
    @PostMapping("/create")
    @Description("创建权限")
    @ApiOperation("创建权限")
    public Response createRight(@RequestBody RightCreateDTO rightCreateDTO) {
        // 处理DTO转换
        BeanValidators.validateWithException(validator, rightCreateDTO);

        rightService.createRight(rightCreateDTO);

        return respSuccessResult(BaseMessage.RIGHT_CREATE_SUCCESS.getMsg());
    }

    /**
     * @Description: 更新权限
     * @param: [RightUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @author: lirunyi
     * @date: 2021/1/11 11:35
     */
    @RightRequired
    @PostMapping("/update")
    @Description("更新权限")
    @ApiOperation("更新权限")
    public Response updateRight(@RequestBody RightUpdateDTO rightUpdateDTO) {

        BeanValidators.validateWithException(validator, rightUpdateDTO);

        rightService.updateRight(rightUpdateDTO);

        return respSuccessResult(BaseMessage.RIGHT_UPDATE_SUCCESS.getMsg());

    }

    @RightRequired
    @GetMapping("/getRights")
    @Description("查询权限")
    @ApiOperation("查询权限")
    public Response getRights(GetRightsDTO getRightsDTO) {

        Object result = rightService.getRights(getRightsDTO);

        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);

    }

    /**
     * @return
     * @Author lirunyi
     * @Description deleteRight
     * @Date 14:47 2021/3/30
     * @Param
     **/
    @RightRequired
    @Description("删除权限")
    @ApiOperation("删除权限")
    @PostMapping("/delete")
    public Response deleteRight(@RequestBody RightDeleteDTO rightDeleteDTO) {
        BeanValidators.validateWithException(validator, rightDeleteDTO);
        rightService.deleteRight(rightDeleteDTO);

        return respSuccessResult(BaseMessage.DELETE_RIGHT_SUCCESS.getMsg());

    }
}































