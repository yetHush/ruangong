package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseConstant;
import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.FileTypeConstants;
import bigdata.filesystem.comn.exception.ErrorMsgException;
import bigdata.filesystem.comn.utils.BeanValidators;
//import bigdata.filesystem.comn.utils.FileUtil;
import bigdata.filesystem.comn.utils.HttpServletUtil;
import bigdata.filesystem.config.PassToken;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.config.ValidateFileType;
import bigdata.filesystem.dto.*;
//import bigdata.filesystem.dto.query.GetFilesDTO;
//import bigdata.filesystem.service.FileService;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import cn.novelweb.tool.upload.local.pojo.UploadFileParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import java.io.IOException;

@RequestMapping("/file")
@RestController
public class FileController extends BaseController {
//    @Autowired
//    private FileService fileService;

    /**
     * @Description: 断点续传判断是否此部分已经上传了
     * @param: [lfileMd5CheckDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: yangqh
     * @date: 2020/12/19/019 15:10
     */
//    @RightRequired
//    @PostMapping(value = "/lfile/md5/check")
//    @Description("断点续传判断")
//    @ApiOperation("断点续传判断")
//    public Response checkFileMd5(@RequestBody LfileMd5CheckDTO lfileMd5CheckDTO) {
//        BeanValidators.validateWithException(validator, lfileMd5CheckDTO);
//        FileUtil.checkFileMd5(lfileMd5CheckDTO);
//        return respSuccessResult(BaseMessage.TEMP_FILE_QUERY_MD5_SUCCESS.getMsg(), null);
//    }
//
//    @RightRequired
//    @GetMapping("/download/**")
//    @Description("文件下载")
//    @ApiOperation("文件下载")
//    public void download(HttpServletRequest request, HttpServletResponse response) {
//        String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
//        String filePath = StringUtils.removeStart(fullPath, "/file/download");
//        fileService.download(response, filePath);
//        this.successLog(BaseMessage.FILE_DOWNLOAD_SUCCESS.getMsg(), null);
//    }

    /**
     * @Description: 上传大文件
     * @param: [fileUploadDTO, file]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-15 15:06
     */
//    @RightRequired
//    @ValidateFileType(fileTypes = FileTypeConstants.OFFICE)
//    @PostMapping("/upload/large/file")
//    @Description("上传大文件")
//    @ApiOperation("上传大文件")
//    public Response uploadLargeFile(@RequestParam("fileBreakpointParamStr") String fileBreakpointParamStr,
//                                    @RequestParam("file") MultipartFile file, HttpServletRequest request) {
//        FileBreakpointParam fileBreakpointParam = JSONUtil.toBean(fileBreakpointParamStr, FileBreakpointParam.class);
//        UploadFileParam chunkParam = new UploadFileParam();
//        BeanUtils.copyProperties(fileBreakpointParam, chunkParam);
//        chunkParam.setFile(file);
//        LFileUploadDTO lFileUploadDTO = JSONUtil.toBean(JSONUtil.toJsonStr(fileBreakpointParam.getEntity()), LFileUploadDTO.class);
//        BeanValidators.validateWithException(validator, lFileUploadDTO);
//        fileService.uploadLargeFile(chunkParam, lFileUploadDTO, request);
//        return respSuccessResult(BaseMessage.FILE_UPLOAD_SUCCESS.getMsg(), fileBreakpointParam.getId());
//    }
//
//    @RightRequired
//    @PostMapping("/upload/large/file/info")
//    @Description("上传大文件信息")
//    @ApiOperation("上传大文件信息")
//    public Response uploadLargeFile(@RequestBody FileUploadDTO fileUploadDTO) {
//        BeanValidators.validateWithException(validator, fileUploadDTO);
//        fileService.uploadLargeFileInfo(fileUploadDTO);
//        return respSuccessResult(BaseMessage.FILE_UPLOAD_SUCCESS.getMsg(), fileUploadDTO.getId());
//    }

    /**
     * @Description: 上传小文件
     * @param: [fileUploadDTO, file]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-15 15:06
     */
//    @RightRequired
//    @ValidateFileType(fileTypes = FileTypeConstants.OFFICE)
//    @PostMapping("/upload/small/file")
//    @Description("上传小文件")
//    @ApiOperation("上传小文件")
//    public Response uploadSmallFile(@RequestParam("fileUploadDTO") String fileUploadDTOStr, @RequestParam("file") MultipartFile file) {
//        FileUploadDTO fileUploadDTO = JSONUtil.toBean(fileUploadDTOStr, FileUploadDTO.class);
//        BeanValidators.validateWithException(validator, fileUploadDTO);
//        fileService.uploadSmallFile(fileUploadDTO, file);
//        return respSuccessResult(BaseMessage.FILE_UPLOAD_SUCCESS.getMsg());
//    }

    /**
     * @Description: 冻结文件
     * @param: [fileFreezeDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-15 15:07
     */
//    @RightRequired
//    @PostMapping("/freeze")
//    @Description("冻结文件")
//    @ApiOperation("冻结文件")
//    public Response fileFreeze(@RequestBody FileFreezeDTO fileFreezeDTO) {
//        BeanValidators.validateWithException(validator, fileFreezeDTO);
//        fileService.freezeFile(fileFreezeDTO);
//        return respSuccessResult(BaseMessage.FILE_FREEZE_SUCCESS.getMsg());
//    }

    /**
     * @Description: 解冻文件
     * @param: [fileUnfreezeDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-15 15:08
     */
//    @RightRequired
//    @PostMapping("/unfreeze")
//    @Description("解冻文件")
//    @ApiOperation("解冻文件")
//    public Response fileUnfreeze(@RequestBody FileUnfreezeDTO fileUnfreezeDTO) {
//        BeanValidators.validateWithException(validator, fileUnfreezeDTO);
//        fileService.unfreezeFile(fileUnfreezeDTO);
//        return respSuccessResult(BaseMessage.FILE_UNFREEZE_SUCCESS.getMsg());
//    }

//    @RightRequired
//    @PostMapping("/soft/delete")
//    @Description("软删除文件")
//    @ApiOperation("软删除文件")
//    public Response softDeleteFile(@RequestBody FileDeleteDTO fileDeleteDTO) {
//        BeanValidators.validateWithException(validator, fileDeleteDTO);
//        fileService.softDeleteFile(fileDeleteDTO);
//        return respSuccessResult(BaseMessage.FILE_DELETE_SUCCESS.getMsg());
//    }

//    @RightRequired
//    @PostMapping("/hard/delete")
//    @Description("硬删除文件")
//    @ApiOperation("硬删除文件")
//    public Response hardDeleteFile(@RequestBody FileDeleteDTO fileDeleteDTO) {
//        BeanValidators.validateWithException(validator, fileDeleteDTO);
//        fileService.hardDeleteFile(fileDeleteDTO);
//        return respSuccessResult(BaseMessage.FILE_DELETE_SUCCESS.getMsg());
//    }

//    @RightRequired
//    @PostMapping("/update")
//    @Description("删除文件")
//    @ApiOperation("删除文件")
//    public Response fileUpdate(@RequestBody FileUpdateDTO fileUpdateDTO) {
//        BeanValidators.validateWithException(validator, fileUpdateDTO);
//        fileService.fileUpdate(fileUpdateDTO);
//        return respSuccessResult(BaseMessage.FILE_UPDATE_SUCCESS.getMsg());
//    }

    /**
     * @Description: 获取验证码
     * @param: []
     * @return: javax.ws.rs.core.Response
     * @auther: yangqh
     * @date: 2021-02-55 15:08
     */
    @PassToken
    @GetMapping("/getCaptcha")
    @Description("获取验证码")
    @ApiOperation("获取验证码")
    public void getCaptcha() {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 38, 4, 4);
        HttpSession httpSession = HttpServletUtil.getRequest().getSession();
        httpSession.setAttribute(BaseConstant.CAPTCHA_SESSION_KEY, captcha.getCode());

        HttpServletResponse response = HttpServletUtil.getResponse();
        response.setHeader("cook","JSESSIONID=" + httpSession.getId());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode("captcha.jpg") + "\"");
        response.addHeader("Content-Length", "" + captcha.getImageBytes().length);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "cook,Content-Disposition" );
        response.setContentType("application/octet-stream;charset=UTF-8");

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new ErrorMsgException(BaseMessage.EXCEPTION.getMsg());
        }
        captcha.write(outputStream);
    }

//    @RightRequired
//    @GetMapping("/getFiles")
//    @Description("获取文件")
//    @ApiOperation("获取文件")
//    public Response getFiles(GetFilesDTO getFilesDTO) {
//        Object result = fileService.getFiles(getFilesDTO);
//        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
//    }

}









