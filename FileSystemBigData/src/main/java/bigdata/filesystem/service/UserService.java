package bigdata.filesystem.service;

import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetUsersDTO;
import bigdata.filesystem.entity.DmUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    long checkUserNum(String name);

    void updateAvatarUser(MultipartFile file, UserUpdateDTO userUpdateDTO);

    void updateNoAvatarUser(UserUpdateDTO userUpdateDTO);

    void createNoAvatarUser(UserCreateDTO userCreateDTO);

    Long getUserIdByUserNum(String userNum);

    void createAvatarUser(UserCreateDTO userCreateDTO, MultipartFile avatar);

    long checkUserId(Long userId);

    void freezeUser(UserFreezeDTO userFreezeDTO);

    void unfreezeUser(UserUnfreezeDTO userUnfreezeDTO);

    void deleteUser(UserDeleteDTO userDeleteDTO);

    void changePwd(PwdChangeDTO pwdChangeDTO);

    LoginResultDTO login(LoginDTO loginDTO);

    LoginResultDTO loginDesktop(LoginDTO loginDTO);

    DmUser getUserByUserNum(String userNum);

    void updateNoAvatarUserSetting(UserUpdateSettingDTO userUpdateSettingDTO);

    void updateNoAvatarPwdUser(UserUpdatePwdDTO userUpdatePwdDTO);

    Object getUsers(GetUsersDTO getUsersDTO);
}
