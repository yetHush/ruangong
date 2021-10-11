package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.RoleRightBindDTO;
import bigdata.filesystem.dto.RoleRightUnbindDTO;
import bigdata.filesystem.dto.query.GetRoleRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetRoleRights4CheckBoxResultDTO;
import bigdata.filesystem.dto.query.GetRoleRightsDTO;
import bigdata.filesystem.dto.query.GetRoleRightsResultDTO;
import bigdata.filesystem.entity.RoleRight;
import bigdata.filesystem.entity.RoleRightPK;
import bigdata.filesystem.mapper.RightMapper;
import bigdata.filesystem.mapper.RoleMapper;
import bigdata.filesystem.mapper.RoleRightMapper;
import bigdata.filesystem.service.RoleRightService;
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
public class RoleRightServiceImpl extends BaseService<RoleRight, RoleRightPK> implements RoleRightService {

    @Autowired
    private RoleRightMapper roleRightMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RightMapper rightMapper;

    /**
     * @return long
     * @Author lirunyi
     * @Description 查询角色是否存在
     * @Date 22:29 2021/1/13
     * @Param userId
     **/
    public long checkRoleId(Long roleId) {
        return roleRightMapper.checkRoleId(roleId);
    }

    /**
     * @return long
     * @Author lirunyi
     * @Description 查询权限是否存在
     * @Date 22:32 2021/1/13
     * @Param rightId
     **/
    public long checkRightId(Long rightId) {
        return roleRightMapper.checkRightId(rightId);
    }


    /**
     * @Description: 角色绑定权限
     * @param: [roleRightBindDTO]
     * @return: void
     * @auther: lirunyi
     * @date: 2021-01-13 22:28
     */
    @Override
    public void roleRightBind(RoleRightBindDTO roleRightBindDTO) {
        if (null == roleRightBindDTO) { //检查传入的DTO是否为空
            this.throwException(BaseMessage.DTO_NOT_EXIST.getMsg());
        } else if (0 == checkRoleId(roleRightBindDTO.getRoleId())) { //检查传入的role是否存在
            this.throwException(BaseMessage.ROLE_NOT_EXIST.getMsg());
        } else if (0 == checkRightId(roleRightBindDTO.getRightId())) { //检查传入的权限是否存在
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        } else {
            // 判断角色是否已经绑定的该权限
            RoleRightPK roleRightPK = new RoleRightPK();
            roleRightPK.setRightId(roleRightBindDTO.getRightId());
            roleRightPK.setRoleId(roleRightBindDTO.getRoleId());
            if (null != this.getById(roleRightPK)) {
                this.throwException(BaseMessage.ROLE_ALREADY_BIND_RIGHT.getMsg());
            } else {
                Date date = new Date();
                Long roleId = roleRightBindDTO.getRoleId();
                Long rightId = roleRightBindDTO.getRightId();
                String createUser = roleRightBindDTO.getCreateUser();
                RoleRight roleRight = new RoleRight();
                roleRight.setRoleId(roleId);
                roleRight.setRightId(rightId);
                roleRight.setCreateUser(createUser);
                roleRight.setCreateTime(date);
                this.save(roleRight);
            }
        }
    }

    /**
     * @Description: 角色解绑权限
     * @param: [roleRightUnbindDTO]
     * @return: void
     * @auther: lirunyi
     * @date: 2021-01-13 23:16
     */
    @Override
    public void roleRightUnbind(RoleRightUnbindDTO roleRightUnbindDTO) {
        if (null == roleRightUnbindDTO) { //检查传入的DTO是否为空
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else if (0 == checkRoleId(roleRightUnbindDTO.getRoleId())) { //检查传入的role是否存在
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else if (0 == checkRightId(roleRightUnbindDTO.getRightId())) { //检查传入的权限是否存在
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        } else {
            long roleId = roleRightUnbindDTO.getRoleId();
            long rightId = roleRightUnbindDTO.getRightId();
//			this.deleteById();
            roleRightMapper.roleRightUnbind(roleId, rightId);
        }
    }


    @Override
    public Object getRoleRights(GetRoleRightsDTO getRoleRightsDTO) {

        int pageSize = getRoleRightsDTO.getPageSize();
        startPage(getRoleRightsDTO.getPageNum(), pageSize);

        List<GetRoleRightsResultDTO> list = roleRightMapper.getRoleRights(getRoleRightsDTO);

        PageInfo<GetRoleRightsResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public Object getRoleRights4CheckBox(GetRoleRights4CheckBoxDTO getRoleRights4CheckBoxDTO) {

        List<GetRoleRights4CheckBoxResultDTO> list = roleRightMapper.getRoleRights4CheckBox(getRoleRights4CheckBoxDTO);
        return list;
    }
}
