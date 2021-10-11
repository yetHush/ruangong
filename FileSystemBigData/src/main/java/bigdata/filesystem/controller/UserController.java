package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.FileTypeConstants;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.comn.utils.RsaUtil;
import bigdata.filesystem.config.PassToken;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.config.ValidateFileType;
import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetUsersDTO;
import bigdata.filesystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;

@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * @Description: 创建无头像用户
     * @param: [userCreateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2020/12/16 22:35
     */
    @RightRequired
    @PostMapping("/noavatar/create")
    @Description("创建用户")
    @ApiOperation("创建用户")
    public Response createNoAvatarUser(@RequestParam("userCreateDTO") String userCreateDTOStr) {
        // 处理DTO转换
        UserCreateDTO userCreateDTO = RsaUtil.decrypt(userCreateDTOStr, UserCreateDTO.class);
        BeanValidators.validateWithException(validator, userCreateDTO);

        userService.createNoAvatarUser(userCreateDTO);

        return respSuccessResult(BaseMessage.USER_CREATE_SUCCESSS.getMsg());
    }

    /**
     * @Description: 创建带头像用户
     * @param: [avatar, userCreateDTOStr]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2020/12/25 10:44
     */
    @RightRequired
    @ValidateFileType(fileTypes = FileTypeConstants.IMAGE)
    @PostMapping("/avatar/create")
    @Description("创建有头像的用户")
    @ApiOperation("创建有头像的用户")
    public Response createAvatarUser(@RequestParam("avatar") MultipartFile avatar, @RequestParam("userCreateDTOStr") String userCreateDTOStr) {
        // 处理DTO转换
        UserCreateDTO userCreateDTO = RsaUtil.decrypt(userCreateDTOStr, UserCreateDTO.class);
        BeanValidators.validateWithException(validator, userCreateDTO);
        userService.createAvatarUser(userCreateDTO, avatar);
        return respSuccessResult(BaseMessage.USER_CREATE_SUCCESSS.getMsg());
    }

    /**
     * @Description: 更新有头像用户
     * @param: [UserUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2020/12/22 00:28
     */
    @RightRequired
    @ValidateFileType(fileTypes = FileTypeConstants.IMAGE)
    @Description("更新有头像的用户")
    @ApiOperation("更新有头像的用户")
    @PostMapping("/avatar/update")
    public Response updateAvatarUser(@RequestParam("file") MultipartFile file, @RequestParam("userUpdateDTO") String userUpdateDTOStr) {
        // 处理DTO转换
        UserUpdateDTO userUpdateDTO = RsaUtil.decrypt(userUpdateDTOStr, UserUpdateDTO.class);
//        userUpdateDTO = JSONUtil.toBean(userUpdateDTOStr, UserUpdateDTO.class);
        BeanValidators.validateWithException(validator, userUpdateDTO);
        userService.updateAvatarUser(file, userUpdateDTO);
        return respSuccessResult(BaseMessage.UPDATE_USER_SUCCESS.getMsg());

    }

    /**
     * @Description: 更新无头像用户
     * @param: [UserUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @author: lirunyi
     * @date: 2020/12/23 23:04
     */
    @RightRequired
    @PostMapping("/noavatar/update")
    @Description("更新用户")
    @ApiOperation("更新用户")
    public Response updateNoAvatarUser(@RequestParam("userUpdateDTO") String userUpdateDTOStr) {

        UserUpdateDTO userUpdateDTO = RsaUtil.decrypt(userUpdateDTOStr, UserUpdateDTO.class);
        //        userUpdateDTO = JSONUtil.toBean(userUpdateDTOStr, UserUpdateDTO.class);
        BeanValidators.validateWithException(validator, userUpdateDTO);

        userService.updateNoAvatarUser(userUpdateDTO);

        return respSuccessResult(BaseMessage.UPDATE_USER_SUCCESS.getMsg());

    }

    /**
     * @Description: 更新无头像用户(密码)
     * @param: [UserUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @author: lirunyi
     * @date: 2020/12/23 23:04
     */
    @RightRequired
    @PostMapping("/noavatar/updatePwd")
    @Description("更新用户")
    @ApiOperation("更新用户")
    public Response updateNoAvatarUserPwd(@RequestParam("userUpdatePwdDTO") String userUpdatePwdDTOStr) {

        UserUpdatePwdDTO userUpdatePwdDTO = RsaUtil.decrypt(userUpdatePwdDTOStr, UserUpdatePwdDTO.class);
        //        userUpdateDTO = JSONUtil.toBean(userUpdateDTOStr, UserUpdateDTO.class);
        BeanValidators.validateWithException(validator, userUpdatePwdDTO);

        userService.updateNoAvatarPwdUser(userUpdatePwdDTO);

        return respSuccessResult(BaseMessage.UPDATE_USER_SUCCESS.getMsg());

    }

    /**
     * @Description: 更新无头像用户(信息)
     * @param: [UserUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @author: lirunyi
     * @date: 2020/12/23 23:04
     */
    @RightRequired
    @PostMapping("/noavatar/updateSetting")
    @Description("管理Setting更新用户")
    @ApiOperation("管理Setting更新用户")
    public Response updateNoAvatarUserSetting(@RequestBody UserUpdateSettingDTO userUpdateSettingDTO) {

        BeanValidators.validateWithException(validator, userUpdateSettingDTO);

        userService.updateNoAvatarUserSetting(userUpdateSettingDTO);

        return respSuccessResult(BaseMessage.UPDATE_USER_SUCCESS.getMsg());

    }

    /**
     * @Description: 冻结用户
     * @param: [userId, updateUser]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2020/12/27 0:28
     */
    @RightRequired
    @PostMapping("/freeze")
    @Description("冻结用户")
    @ApiOperation("冻结用户")
    public Response freezeUser(@RequestBody UserFreezeDTO userFreezeDTO) {
        userService.freezeUser(userFreezeDTO);
        return respSuccessResult(BaseMessage.FREEZE_USER_SUCCESS.getMsg());
    }

    /**
     * @Description: 解冻用户
     * @param: [userId, updateUser]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2020/12/27 0:28
     */
    @RightRequired
    @PostMapping("/unfreeze")
    @Description("解冻用户")
    @ApiOperation("解冻用户")
    public Response unfreezeUser(@RequestBody UserUnfreezeDTO userUnfreezeDTO) {
        userService.unfreezeUser(userUnfreezeDTO);
        return respSuccessResult(BaseMessage.UNFREEZE_USER_SUCCESS.getMsg());
    }


    /**
     * @Description: 用户登录接口
     * @param: [loginDTOStr]
     * @return: javax.ws.rs.core.Response
     * @auther: yangqh
     * @date: 2020/12/25/025 16:11
     */
    @PassToken
    @PostMapping("/login")
    @Description("登录")
    @ApiOperation("登录")
    public Response login(@RequestParam("loginDTOStr") String loginDTOStr) {
        // 处理DTO转换
        LoginDTO loginDTO = RsaUtil.decrypt(loginDTOStr, LoginDTO.class);
        BeanValidators.validateWithException(validator, loginDTO);
        LoginResultDTO loginResultDTO = userService.login(loginDTO);

        return respSuccessResult(BaseMessage.LOGIN_SUCCESS.getMsg(), loginResultDTO);
    }

    /**
     * 桌面端登录
     * @param loginDTOStr
     * @return
     */
    @PassToken
    @PostMapping("/login-desktop")
    @Description("登录")
    @ApiOperation("登录")
    public Response loginDesktop(@RequestParam("loginDTOStr") String loginDTOStr) {
      LoginDTO loginDTO = RsaUtil.decrypt(loginDTOStr, LoginDTO.class);
      BeanValidators.validateWithException(validator, loginDTO);
      LoginResultDTO loginResultDTO = userService.loginDesktop(loginDTO);

      return respSuccessResult(BaseMessage.LOGIN_SUCCESS.getMsg(), loginResultDTO);
    }



    /**
     * @Description: 删除用户
     * @param: [userId, updateUser]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021/1/10 11:24
     */
    @RightRequired
    @PostMapping("/delete")
    @Description("删除用户")
    @ApiOperation("删除用户")
    public Response deleteUser(@RequestBody UserDeleteDTO userDeleteDTO) {
        BeanValidators.validateWithException(validator, userDeleteDTO);
        userService.deleteUser(userDeleteDTO);
        return respSuccessResult(BaseMessage.DELETE_USER_SUCCESS.getMsg());
    }

    /**
     * @Description: 修改密码
     * @param: [userNum, oldUserPwd, newUserPwd]
     * @return: javax.ws.rs.core.Response
     * @auther: hxy
     * @date: 2021-01-11 17:12
     */
    @RightRequired
    @PostMapping("/pwd/change")
    @Description("修改密码")
    @ApiOperation("修改密码")
    public Response changePwd(@RequestParam("pwdChangeDTO") String pwdChangeDTOStr) {
        PwdChangeDTO pwdChangeDTO = RsaUtil.decrypt(pwdChangeDTOStr, PwdChangeDTO.class);
        BeanValidators.validateWithException(validator, pwdChangeDTO);
        userService.changePwd(pwdChangeDTO);
        return respSuccessResult(BaseMessage.PWD_CHANGE_SUCCESS.getMsg());
    }

    @RightRequired
    @GetMapping("/getUsers")
    @ApiOperation("查询用户")
    public Response getUsers(GetUsersDTO getUsersDTO) {
        Object result = userService.getUsers(getUsersDTO);
        return respSuccessResult(BaseMessage.GET_DATA_SUCCESS.getMsg(), result);
    }

}
