package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.FileTypeConstants;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.config.ValidateFileType;
import bigdata.filesystem.dto.AutoCreateDTO;
import bigdata.filesystem.dto.AutoDeleteDTO;
import bigdata.filesystem.dto.AutoUpdateDTO;
//import bigdata.filesystem.dto.FileUploadDTO;
import bigdata.filesystem.dto.query.GetAutosDTO;
import bigdata.filesystem.dto.query.GetLogsDTO;
import bigdata.filesystem.service.AutoService;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;

@RequestMapping("/auto")
@RestController
public class AutoController extends BaseController {
    @Autowired
    private AutoService autoService;

    /**
     * @Description: 创建自动填表数据
     * @param: [autoCreateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:22
     */
    @RightRequired
    @Description("创建自动填表数据")
    @ApiOperation("创建自动填表数据")
    @PostMapping("/create")
    public Response createAuto(@RequestBody AutoCreateDTO autoCreateDTO) {
        // 处理DTO转换
        BeanValidators.validateWithException(validator, autoCreateDTO);

        autoService.createAuto(autoCreateDTO);

        return respSuccessResult(BaseMessage.AUTO_CREATE_SUCCESS.getMsg());
    }

    /**
     * @Description: 更新自动填表数据
     * @param: [autoUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:22
     */
    @RightRequired
    @Description("更新自动填表数据")
    @ApiOperation("更新自动填表数据")
    @PostMapping("/update")
    public Response updateAuto(@RequestBody AutoUpdateDTO autoUpdateDTO) {
        // 处理DTO转换
        BeanValidators.validateWithException(validator, autoUpdateDTO);

        autoService.updateAuto(autoUpdateDTO);

        return respSuccessResult(BaseMessage.AUTO_UPDATE_SUCCESS.getMsg());
    }

    /**
     * @Description: 删除自动填表数据
     * @param: [autoDeleteDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:22
     */
    @RightRequired
    @Description("删除自动填表数据")
    @ApiOperation("删除自动填表数据")
    @PostMapping("/delete")
    public Response deleteAuto(@RequestBody AutoDeleteDTO autoDeleteDTO) {
        // 处理DTO转换
        BeanValidators.validateWithException(validator, autoDeleteDTO);

        autoService.deleteAuto(autoDeleteDTO);

        return respSuccessResult(BaseMessage.AUTO_DELETE_SUCCESS.getMsg());
    }

    @RightRequired
    @GetMapping("/getAutos")
    @Description("查询自动填表数据")
    @ApiOperation("查询自动填表数据")
    public Response getAutos(GetAutosDTO getAutosDTO) {
        Object result = autoService.getAutos(getAutosDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }


    @RightRequired
    @ValidateFileType(fileTypes = {FileTypeConstants.XLS, FileTypeConstants.XLSX})
    @PostMapping("/excel2json")
    @Description("解析Excel数据为Json自动填表数据")
    @ApiOperation("解析Excel数据为Json自动填表数据")
    public Response uploadSmallFile(@RequestParam("file") MultipartFile file) {
        autoService.excel2json(file);
        return respSuccessResult(BaseMessage.AUTO_CREATE_SUCCESS.getMsg());
    }
}
