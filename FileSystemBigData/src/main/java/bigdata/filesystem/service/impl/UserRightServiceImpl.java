package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.UserRightBindDTO;
import bigdata.filesystem.dto.UserRightUnbindDTO;
import bigdata.filesystem.dto.query.GetUserRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetUserRights4CheckBoxResultDTO;
import bigdata.filesystem.dto.query.GetUserRightsDTO;
import bigdata.filesystem.dto.query.GetUserRightsResultDTO;
import bigdata.filesystem.entity.UserRight;
import bigdata.filesystem.entity.UserRightPK;
import bigdata.filesystem.mapper.RightMapper;
import bigdata.filesystem.mapper.UserMapper;
import bigdata.filesystem.mapper.UserRightMapper;
import bigdata.filesystem.service.UserRightService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class UserRightServiceImpl extends BaseService<UserRight, UserRightPK> implements UserRightService {

    @Autowired
    private UserRightMapper userRightMapper;

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 通过ID查询权限
     * @param: [rightId]
     * @return: long
     * @auther: hxy
     * @date: 2021-01-13 0:30
     */
    private long checkRightId(Long rightId) {
        return rightMapper.checkRightId(rightId);
    }

    /**
     * @Description: 用户绑定权限
     * @param: [userRoleBindDTO]
     * @return: void
     * @auther: hxy
     * @date: 2021-01-12 22:46
     */
    @Override
    public void userRightBind(UserRightBindDTO userRightBindDTO) {
        if (null == userRightBindDTO) { //检查传入的DTO是否为空
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else if (0 == userMapper.checkUserId(userRightBindDTO.getUserId())) { //检查传入的用户是否存在
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else if (0 == rightMapper.checkRightId(userRightBindDTO.getRightId())) { //检查传入的权限是否存在
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        } else {
            long userId = userRightBindDTO.getUserId();
            long rightId = userRightBindDTO.getRightId();

            UserRightPK userRightPK = new UserRightPK();
            userRightPK.setRightId(rightId);
            userRightPK.setUserId(userId);

            if (null != getById(userRightPK)) { //检查该权限是否已经绑定
                this.throwException(BaseMessage.USER_RIGHT_DUPLICATE.getMsg());
            } else {
                UserRight userRight = new UserRight();
                userRight.setUserId(userId);
                userRight.setRightId(rightId);
                userRight.setCreateUser(userRightBindDTO.getCreateUser());
                Date date = new Date();
                userRight.setCreateTime(date);

                this.save(userRight);
            }
        }
    }

    /**
     * @Description: 用户解绑权限
     * @param: [userRoleUnbindDTO]
     * @return: void
     * @auther: hxy
     * @date: 2021-01-12 22:48
     */
    @Override
    public void userRightUnbind(UserRightUnbindDTO userRightUnbindDTO) {
        if (null == userRightUnbindDTO) { //检查传入的DTO是否为空
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else {
            long userId = userRightUnbindDTO.getUserId();
            long rightId = userRightUnbindDTO.getRightId();

            if (0 == userMapper.checkUserId(userId)) { //检查传入的用户是否存在
                this.throwException(BaseMessage.USER_NOEXIST.getMsg());
            } else if (0 == rightMapper.checkRightId(rightId)) { //检查传入的权限是否存在
                this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
            } else {
                UserRightPK userRightPK = new UserRightPK();
                userRightPK.setRightId(rightId);
                userRightPK.setUserId(userId);
                if (null == getById(userRightPK)) { //查询该绑定是否存在
                    this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
                }
                this.deleteById(userRightPK);
            }
        }
    }

    @Override
    public Object getUserRights(GetUserRightsDTO getUserRightsDTO) {

        int pageSize = getUserRightsDTO.getPageSize();
        startPage(getUserRightsDTO.getPageNum(), pageSize);

        List<GetUserRightsResultDTO> list = userRightMapper.getUserRights(getUserRightsDTO);

        PageInfo<GetUserRightsResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public Object getUserRights4CheckBox(GetUserRights4CheckBoxDTO getUserRights4CheckBoxDTO) {

        List<GetUserRights4CheckBoxResultDTO> list = userRightMapper.getUserRights4CheckBox(getUserRights4CheckBoxDTO);
        return list;
    }
}
