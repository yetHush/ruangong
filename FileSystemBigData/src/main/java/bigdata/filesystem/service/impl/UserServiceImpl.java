package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseConstant;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.comn.exception.ErrorMsgException;
//import bigdata.filesystem.comn.utils.FileUtil;
import bigdata.filesystem.comn.utils.HttpServletUtil;
import bigdata.filesystem.comn.utils.PasswordUtil;
import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetUsersDTO;
import bigdata.filesystem.dto.query.GetUsersResultDTO;
import bigdata.filesystem.entity.DmUser;
import bigdata.filesystem.mapper.UserMapper;
import bigdata.filesystem.service.TokenService;
import bigdata.filesystem.service.UserService;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.power.common.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static bigdata.filesystem.comn.base.BaseConstant.*;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends BaseService<DmUser, Long> implements UserService {

    @Value("${file.avatar-path}")
    private String AVATAR_PATH;

    @Value("${file.chunk-size}")
    private Long CHUNK_SIZE;

//
//    public void setChunkSize(Long chunkSize) {
//        FileUtil.CHUNK_SIZE = chunkSize;
//    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public long checkUserNum(String userNum) {
        return userMapper.getNumByUserNum(userNum);
    }

    /**
     * @Description: 检查用户ID
     * @param: [userNum]
     * @return: long
     * @auther: hxy
     * @date: 2020/12/25 13:42
     */
    @Override
    public long checkUserId(Long userId) {
        return userMapper.getNumByUserId(userId);
    }

    /**
     * @Description: 通过工号查询用户数量
     * @param: [userNum]
     * @return: java.lang.Long
     * @auther: hxy
     * @date: 2020/12/26 21:43
     */
    @Override
    public Long getUserIdByUserNum(String userNum) {
        return userMapper.getUserIdByUserNum(userNum);
    }

    /**
     * @Description: 创建无头像用户
     * @param: [userCreateDTO]
     * @return: void
     * @auther: hxy
     * @date: 2020/12/26 21:43
     */
    @Override
    public void createNoAvatarUser(UserCreateDTO userCreateDTO) {
        //检查是否传入
        if (null == userCreateDTO) {
            this.throwException(BaseMessage.USER_IS_NULL.getMsg());
        }

        DmUser dmUser = new DmUser();
        BeanUtils.copyProperties(userCreateDTO, dmUser);
        //检查用户名是否重复
        if (checkUserNum(dmUser.getUserNum()) != 0) {
            this.throwException(BaseMessage.USERNAME_OCCUPIED.getMsg());
        } else {
            //对密码加密分为两个步骤：
            //1.获取盐
            String salt = PasswordUtil.getSalt();
            //2.加密
            String userPwd = PasswordUtil.encryptPassword(dmUser.getUserPwd(), salt);
            //3.保存密码数据和盐
            dmUser.setUserPwd(userPwd);
            dmUser.setPwdSalt(salt);
            dmUser.setStatus(2);
            Date date = new Date();
            dmUser.setCreateTime(date);
            //信息
            String email = userCreateDTO.getEmail();
            Integer sex = userCreateDTO.getSex();
            String phoneNum = userCreateDTO.getPhoneNum();
            String remark = userCreateDTO.getRemark();
            dmUser.setEmail(email);
            dmUser.setSex(sex);
            dmUser.setPhoneNum(phoneNum);
            dmUser.setRemark(remark);

            //插入数据
            this.save(dmUser);
        }
    }

    /**
     * @Description: 创建带头像用户
     * @param: [userCreateDTO, avatar]
     * @return: void
     * @auther: hxy
     * @date: 2020/12/26 21:43
     */
    @Override
    public void createAvatarUser(UserCreateDTO userCreateDTO, MultipartFile avatar) {
        //检查是否传入
        if (null == userCreateDTO) {
            this.throwException(BaseMessage.USER_IS_NULL.getMsg());
        }

        String filename = "";

        DmUser dmUser = new DmUser();
        BeanUtils.copyProperties(userCreateDTO, dmUser);
        //检查用户名是否重复
        if (checkUserNum(dmUser.getUserNum()) != 0) {
            this.throwException(BaseMessage.USERNAME_OCCUPIED.getMsg());
        } else {
            if (null == avatar || avatar.isEmpty()) {
                throw new ErrorMsgException(BaseMessage.FILE_EMPTY.getMsg());
            } else if (avatar.getSize() > CHUNK_SIZE) {
                throw new ErrorMsgException(BaseMessage.FILE_TOO_LARGE.getMsg());
            } else {
                String avatarPath = AVATAR_PATH + "/" + filename;
                avatarPath = UrlUtil.simplifyUrl(avatarPath);
                dmUser.setAvatarPath(avatarPath);

                //1.获取盐
                String salt = PasswordUtil.getSalt();
                //2.加密
                String userPwd = PasswordUtil.encryptPassword(dmUser.getUserPwd(), salt);
                //3.保存密码数据和盐
                dmUser.setUserPwd(userPwd);
                dmUser.setPwdSalt(salt);
                dmUser.setStatus(STATUS_FREEZE);
                Date date = new Date();
                dmUser.setCreateTime(date);
                //插入数据
                this.save(dmUser);
            }
        }
    }

    /**
     * 更新有头像用户
     *
     * @Desctiption updateAvatarUser
     * @param: [UserUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2020/12/22 00:28
     */
    @Override
    public void updateAvatarUser(MultipartFile file, UserUpdateDTO userUpdateDTO) {

//        String fileName = file.getOriginalFilename();
        //保存文件
        String newFileName = "";

        //报错范例
        if (null == userUpdateDTO) {
            this.throwException(BaseMessage.USER_IS_NULL.getMsg());
        }
        DmUser dmUser = new DmUser();
        BeanUtils.copyProperties(userUpdateDTO, dmUser);
        String avatarPath = AVATAR_PATH + "/" + newFileName;
        avatarPath = UrlUtil.simplifyUrl(avatarPath);

        if (getUserIdByUserNum(dmUser.getUserNum()) == null || getUserIdByUserNum(dmUser.getUserNum()).equals(dmUser.getUserId()))//base==dto
        {
            if (null == file || file.isEmpty()) {
                throw new ErrorMsgException(BaseMessage.FILE_EMPTY.getMsg());
            } else if (StringUtils.isEmpty(newFileName)) {
                throw new ErrorMsgException(BaseMessage.FILE_NAME_EMPTY.getMsg());
            } else if (file.getSize() > CHUNK_SIZE) {
                throw new ErrorMsgException(BaseMessage.FILE_TOO_LARGE.getMsg());
            } else {
                //如果本地上传的用户userNum对应的userId（1）和数据库的userNum对应的userId（2）相等，代表可以修改userNum，无需校验唯一性。
                String salt = PasswordUtil.getSalt();
                String userPwd = PasswordUtil.encryptPassword(userUpdateDTO.getUserPwd(), salt);
                dmUser.setUserPwd(userPwd);
                dmUser.setPwdSalt(salt);
                dmUser.setAvatarPath(avatarPath);
                Date date = new Date();
                dmUser.setCreateTime(date);
                this.update(dmUser);
            }

        } else//校验号码唯一性
        {
            if (checkUserNum(dmUser.getUserNum()) != 0) {//重复
                this.throwException(BaseMessage.USERNAME_OCCUPIED.getMsg());
            } else {
                //插入数据
                String salt = PasswordUtil.getSalt();
                String userPwd = PasswordUtil.encryptPassword(userUpdateDTO.getUserPwd(), salt);
                dmUser.setUserPwd(userPwd);
                dmUser.setPwdSalt(salt);
                dmUser.setAvatarPath(avatarPath);
                Date date = new Date();
                dmUser.setCreateTime(date);
                this.update(dmUser);
            }
        }

    }

    /**
     * 更新无头像用户
     *
     * @Desctiption updateNoAvatarUser
     * @param: [UserUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2020/12/23 23:04
     */
    @Override
    public void updateNoAvatarUser(UserUpdateDTO userUpdateDTO) {
        //报错范例
        if (null == userUpdateDTO) {
            this.throwException(BaseMessage.USER_IS_NULL.getMsg());
        }
        DmUser dmUser = null;
//        BeanUtils.copyProperties(userUpdateDTO, user);
        dmUser = this.getById(userUpdateDTO.getUserId());
        dmUser.setEmail(userUpdateDTO.getEmail());
        dmUser.setPhoneNum(userUpdateDTO.getPhoneNum());
        dmUser.setSex(userUpdateDTO.getSex());
        dmUser.setStatus(userUpdateDTO.getStatus());
        dmUser.setRemark(userUpdateDTO.getRemark());
        dmUser.setUpdateUser(userUpdateDTO.getUpdateUser());
        if (getUserIdByUserNum(dmUser.getUserNum()) == null || getUserIdByUserNum(dmUser.getUserNum()).equals(dmUser.getUserId())) {
            //如果本地上传的用户userId对应的userNum（1）和数据库的userId对应的userNum（2）相等，代表可以修改userNum，无需校验唯一性。
            if (StringUtils.isNotBlank(userUpdateDTO.getUserPwd())) {
                String salt = PasswordUtil.getSalt();
                String userPwd = PasswordUtil.encryptPassword(userUpdateDTO.getUserPwd(), salt);
                dmUser.setUserPwd(userPwd);
                dmUser.setPwdSalt(salt);
            }
            if (StringUtils.isNotBlank(userUpdateDTO.getUsername())) {
                dmUser.setUsername(userUpdateDTO.getUsername());
            }
            Date date = new Date();
            dmUser.setUpdateTime(date);
            this.update(dmUser);
        } else//校验号码唯一性
        {
            if (checkUserNum(dmUser.getUserNum()) != 0) {
                this.throwException(BaseMessage.USERNAME_OCCUPIED.getMsg());
            } else {
                //插入数据
                if (StringUtils.isNotBlank(userUpdateDTO.getUserPwd())) {
                    String salt = PasswordUtil.getSalt();
                    String userPwd = PasswordUtil.encryptPassword(userUpdateDTO.getUserPwd(), salt);
                    dmUser.setUserPwd(userPwd);
                    dmUser.setPwdSalt(salt);
                }
                if (StringUtils.isNotBlank(userUpdateDTO.getUsername())) {
                    dmUser.setUsername(userUpdateDTO.getUsername());
                }
                Date date = new Date();
                dmUser.setUpdateTime(date);
                this.update(dmUser);
            }
        }
    }

    /**
     * @Description: 冻结用户
     * @param: [userId, updateUser]
     * @return: void
     * @auther: hxy
     * @date: 2020/12/25 13:08
     */
    @Override
    public void freezeUser(UserFreezeDTO userFreezeDTO) {
        //检查输入的用户名是否正确
        if (checkUserId(userFreezeDTO.getUserId()) == 0) {
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else {
//            User user = new User();
//            BeanUtils.copyProperties(this.getById(userFreezeDTO.getUserId()), user); //user是需要被冻结的用户
            DmUser dmUser = this.getById(userFreezeDTO.getUserId());
//            if (user.getStatus() == STATUS_DELETE) {
//                this.throwException(BaseMessage.USER_IS_DELETED.getMsg());
//            }

            dmUser.setStatus(STATUS_FREEZE);

            Date date = new Date();
            dmUser.setUpdateTime(date);

            dmUser.setUpdateUser(userFreezeDTO.getUpdateUser());

            this.update(dmUser);
        }
    }

    /**
     * @Description: 解冻用户
     * @param: [userUnfreezeDTO]
     * @return: void
     * @auther: hxy
     * @date: 2020/12/25 13:08
     */
    @Override
    public void unfreezeUser(UserUnfreezeDTO userUnfreezeDTO) {
        //检查输入的用户名是否正确
        if (checkUserId(userUnfreezeDTO.getUserId()) == 0) {
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else {
//            User user = new User();
//            BeanUtils.copyProperties(this.getById(userUnfreezeDTO.getUserId()), user); //user是需要被解冻的用户
            DmUser dmUser = this.getById(userUnfreezeDTO.getUserId());
            if (dmUser.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.USER_IS_DELETED.getMsg());
            }

            dmUser.setStatus(STATUS_NORMAL);

            Date date = new Date();
            dmUser.setUpdateTime(date);
            dmUser.setUpdateUser(userUnfreezeDTO.getUpdateUser());

            this.update(dmUser);
        }
    }

    /**
     * @Description: 删除用户
     * @param: [userId, updateUser]
     * @return: void
     * @auther: hxy
     * @date: 2021/1/10 11:25
     */
    @Override
    public void deleteUser(UserDeleteDTO userDeleteDTO) {
        //检查输入的用户名是否正确
        if (checkUserId(userDeleteDTO.getUserId()) == 0) {
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else {
            DmUser dmUser = new DmUser();
            BeanUtils.copyProperties(this.getById(userDeleteDTO.getUserId()), dmUser); //user是需要被删除的用户

            if (dmUser.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.USER_IS_DELETED.getMsg());
            }

            dmUser.setStatus(STATUS_DELETE);

            Date date = new Date();
            dmUser.setUpdateTime(date);
            dmUser.setUpdateUser(userDeleteDTO.getUpdateUser());

            this.update(dmUser);
        }
    }

    /**
     * @Description: 修改密码
     * @param: [userNum, oldUserPwd, newUserPwd]
     * @return: void
     * @auther: hxy
     * @date: 2021/1/10 11:33
     */
    @Override
    public void changePwd(PwdChangeDTO pwdChangeDTO) {

        if (null == pwdChangeDTO) {
            this.throwException(BaseMessage.PWD_DTO_IS_NULL.getMsg());
        } else {
//            User user = new User();
//            Long id=getUserIdByUserNum(pwdChangeDTO.getUserNum());
            DmUser dmUser = userMapper.getUserByUserNum(pwdChangeDTO.getUserNum());
            Long id = dmUser.getUserId();
            System.out.println(dmUser.getUserPwd());
            System.out.println(pwdChangeDTO.getOldUserPwd());//userInput
            Boolean result = PasswordUtil.checkPassword(pwdChangeDTO.getOldUserPwd(), dmUser.getUserPwd(), dmUser.getPwdSalt());
            System.out.println(result);
            if (null == dmUser || dmUser.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.USER_NOEXIST.getMsg());
            } else if (!result) {
                this.throwException(BaseMessage.PWD_ERROR.getMsg()); //旧密码错误
            } else if (dmUser.getUserPwd() == pwdChangeDTO.getNewUserPwd()) {
                this.throwException(BaseMessage.PWD_NOT_CHANGE.getMsg()); //新密码与旧密码相同
            } else {
                String salt = dmUser.getPwdSalt();
                String newPwd = PasswordUtil.encryptPassword(pwdChangeDTO.getNewUserPwd(), salt);
                dmUser.setUserPwd(newPwd); //修改密码
                Date date = new Date();
                dmUser.setUpdateTime(date);
                dmUser.setUpdateUser(dmUser.getUsername()); //updateUser是用户自己
                this.update(dmUser);
            }
        }
    }

    /**
     * @Description: 用户登录接口
     * @param: [loginDTO]
     * @return: java.lang.String
     * @auther: yangqh
     * @date: 2020/12/25/025 17:12
     */
    @Override
    public LoginResultDTO login(LoginDTO loginDTO) {
        //先校验验证码
        String captcha = loginDTO.getCaptcha();
        HttpSession httpSession = HttpServletUtil.getRequest().getSession();
        String sessionCaptcha = Convert.toStr(httpSession.getAttribute(BaseConstant.CAPTCHA_SESSION_KEY));
        if (ObjectUtil.isEmpty(captcha) || !captcha.equalsIgnoreCase(sessionCaptcha)) {
            throw new ErrorMsgException(BaseMessage.VALID_CODE_ERROR.getMsg());
        }
        return loginCommon(loginDTO);
    }

    @Override
    public LoginResultDTO loginDesktop(LoginDTO loginDTO) {
        return loginCommon(loginDTO);
    }

    private LoginResultDTO loginCommon(LoginDTO loginDTO) {
        //查询出用户
        DmUser dmUser = userMapper.getUserByUserNum(loginDTO.getUserNum());
        if (null == dmUser) {
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        }
        //判断用户状态
        if (dmUser.getStatus() == BaseConstant.STATUS_FREEZE) {
            this.throwException(BaseMessage.USER_FREEZED.getMsg());
        } else if (dmUser.getStatus() == BaseConstant.STATUS_DELETE) {
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        }
        //校验密码是否正确
        Boolean result = PasswordUtil.checkPassword(loginDTO.getUserPwd(), dmUser.getUserPwd(), dmUser.getPwdSalt());
        if (!result) {
            this.throwException(BaseMessage.USER_OR_PWD_ERROR.getMsg());
        }
        //如果用户密码正确则表示登录成功，此时需要生成Token
        String token = tokenService.getToken(dmUser);
        //保存token到数据库
        Date date = new Date();
        dmUser.setUpdateTime(date);
        dmUser.setUpdateUser(dmUser.getUserNum());
        this.update(dmUser);
        LoginResultDTO loginResultDTO = new LoginResultDTO();
//        loginResultDTO.setUserNum(user.getUserNum());
        BeanUtils.copyProperties(dmUser, loginResultDTO);
        loginResultDTO.setToken(token);
        return loginResultDTO;
    }

    /**
     * @Description: 根据工号查询用户
     * @param: [userNum]
     * @return: bigdata.filesystem.entity.User
     * @auther: yangqh
     * @date: 2020/12/25/025 16:50
     */
    @Override
    public DmUser getUserByUserNum(String userNum) {
        return userMapper.getUserByUserNum(userNum);
    }

    @Override
    public void updateNoAvatarUserSetting(UserUpdateSettingDTO userUpdateSettingDTO) {
        //报错范例
        if (null == userUpdateSettingDTO) {
            this.throwException(BaseMessage.USER_IS_NULL.getMsg());
        }
        DmUser dmUser = new DmUser();
        Long id = getUserIdByUserNum(userUpdateSettingDTO.getUserNum());
        BeanUtils.copyProperties(this.getById(id), dmUser);
        System.out.println(id);
        if (getUserIdByUserNum(dmUser.getUserNum()) == null || getUserIdByUserNum(dmUser.getUserNum()).equals(dmUser.getUserId())) {
            //如果本地上传的用户userId对应的userNum（1）和数据库的userId对应的userNum（2）相等，代表可以修改userNum，无需校验唯一性。
//            String pwd=getUserPwdByUserId(id);
//            String salt = PasswordUtil.getSalt();
//            user.setUserPwd(pwd);
//            user.setPwdSalt(salt);
            Date date = new Date();
            dmUser.setUpdateTime(date);
            Integer sex = userUpdateSettingDTO.getSex();
            dmUser.setSex(sex);
            String email = userUpdateSettingDTO.getEmail();
            dmUser.setEmail(email);
            String phoneNum = userUpdateSettingDTO.getPhoneNum();
            dmUser.setPhoneNum(phoneNum);
            String remark = userUpdateSettingDTO.getRemark();
            dmUser.setRemark(remark);
            dmUser.setStatus(1);
            this.update(dmUser);
        } else//校验号码唯一性
        {
            if (checkUserNum(dmUser.getUserNum()) > 1) {
                this.throwException(BaseMessage.USERNAME_OCCUPIED.getMsg());
            } else {
                //插入数据
//                String pwd=getUserPwdByUserId(id);
//                String salt = PasswordUtil.getSalt();
//                user.setUserPwd(pwd);
//                user.setPwdSalt(salt);
                Date date = new Date();
                dmUser.setCreateTime(date);
                Integer sex = userUpdateSettingDTO.getSex();
                dmUser.setSex(sex);
                dmUser.setStatus(1);
                this.update(dmUser);
            }
        }
    }

    @Override
    public void updateNoAvatarPwdUser(UserUpdatePwdDTO userUpdatePwdDTO) {
        //报错范例
        if (null == userUpdatePwdDTO) {
            this.throwException(BaseMessage.USER_IS_NULL.getMsg());
        }
        DmUser dmUser = new DmUser();
        Long id = getUserIdByUserNum(userUpdatePwdDTO.getUserNum());
        BeanUtils.copyProperties(this.getById(id), dmUser);
//        System.out.println(user.getUserPwd());//加密后的原密码
//        System.out.println(userUpdatePwdDTO.getOldPwd());//用户输入的未加密密码

        Boolean result = PasswordUtil.checkPassword(userUpdatePwdDTO.getUserPwd(), dmUser.getUserPwd(), dmUser.getPwdSalt());
        if (!result) {
            this.throwException(BaseMessage.PWD_ERROR.getMsg());
        }
        String salt = PasswordUtil.getSalt();
        String userPwd = PasswordUtil.encryptPassword(userUpdatePwdDTO.getUserPwd(), salt);
        dmUser.setUserPwd(userPwd);
        dmUser.setPwdSalt(salt);
        Date date = new Date();
        dmUser.setUpdateTime(date);
        this.update(dmUser);


    }

    @Override
    public Object getUsers(GetUsersDTO getUsersDTO) {

        int pageSize = getUsersDTO.getPageSize();
        startPage(getUsersDTO.getPageNum(), pageSize);

        List<GetUsersResultDTO> list = userMapper.getUsers(getUsersDTO);

        PageInfo<GetUsersResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }
}
