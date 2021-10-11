package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.UserRoleBindDTO;
import bigdata.filesystem.dto.UserRoleUnbindDTO;
import bigdata.filesystem.dto.query.GetUserRolesDTO;
import bigdata.filesystem.dto.query.GetUserRolesResultDTO;
import bigdata.filesystem.entity.DmUser;
import bigdata.filesystem.entity.Role;
import bigdata.filesystem.entity.UserRole;
import bigdata.filesystem.entity.UserRolePK;
import bigdata.filesystem.mapper.*;
import bigdata.filesystem.service.UserRoleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static bigdata.filesystem.comn.base.BaseConstant.STATUS_DELETE;

@Slf4j
@Service
@Transactional
public class UserRoleServiceImpl extends BaseService<DmUser, UserRolePK> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleRightMapper roleRightMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRightMapper userRightMapper;

    /**
     * @Description: 用户绑定角色
     * @param: [userRoleBindDTO]
     * @return: void
     * @auther: hxy
     * @date: 2021-01-12 10:28
     */
    @Override
    public void userRoleBind(UserRoleBindDTO userRoleBindDTO) {
        if (null == userRoleBindDTO) {
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else {
            long userId = userRoleBindDTO.getUserId();
            long roleId = userRoleBindDTO.getRoleId();
//      String userName= userMapper.getUserNameByUserId(userId);
//      String roleName=roleMapper.getRoleNameById(roleId);
            DmUser dmUser = userMapper.getUserByUserId(userId);
            Role role = roleMapper.getRoleById(roleId);
            if (null == dmUser || dmUser.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.USER_NOEXIST.getMsg());
            } else if (null == role || role.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.ROLE_NOT_EXIST.getMsg());
            } else {
                //绑定
                UserRolePK userRolePK = new UserRolePK();
                userRolePK.setUserId(userId);
                userRolePK.setRoleId(roleId);
                if (0 != userRoleMapper.checkUserRole(userId, roleId)) { //判断该绑定关系是否已经存在
                    this.throwException(BaseMessage.BIND_DUPLICATE.getMsg());
                } else {
                    Set<Long> rights = roleRightMapper.getRightsByRoleId(userRoleBindDTO.getRoleId());//查询角色的权限
                    if (null == rights) {
                        this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg()); //判断角色的权限是否存在
                    }
//          List<UserRight> items = new ArrayList<>();
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dataString = formatter.format(date);
                    for (long rightId : rights) { //将要绑定的用户权限存为列表
                        if (0 == userRightMapper.checkUserRight(userId, rightId)) {
                            userRightMapper.userRightBind(userId, rightId, date, dataString, userRoleBindDTO.getCreateUser());
                        }
                    }

                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);
//          userRole.setRoleName(roleName);
//          userRole.setUsername(userName);

                    userRole.setCreateTime(date);
                    userRole.setCreateUser(userRoleBindDTO.getCreateUser());
                    userRoleMapper.insert(userRole);

                }
            }
            //rights指批量操作
        }
    }

    /**
     * @Description: 用户解绑角色
     * @param: [userRoleUnbindDTO]
     * @return: void
     * @auther: hxy
     * @date: 2021-01-13 15:28
     */
    @Override
    public void userRoleUnbind(UserRoleUnbindDTO userRoleUnbindDTO) {
        if (null == userRoleUnbindDTO) {
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else {
            DmUser dmUser = userMapper.getUserByUserId(userRoleUnbindDTO.getUserId());
            Role role = roleMapper.getRoleById(userRoleUnbindDTO.getRoleId());
            if (null == dmUser || dmUser.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.USER_NOEXIST.getMsg());
            } else if (null == role || role.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.ROLE_NOT_EXIST.getMsg());
            } else {
                UserRolePK userRolePK = new UserRolePK();
                long userId = userRoleUnbindDTO.getUserId();
                long roleId = userRoleUnbindDTO.getRoleId();
                userRolePK.setUserId(userId);
                userRolePK.setRoleId(roleId);
                if (0 == userRoleMapper.checkUserRole(userId, roleId)) { //查询该用户角色绑定是否存在
                    this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
                }

                Set<Long> unBindPermissions = roleRightMapper.getRightsByRoleId(userRoleUnbindDTO.getRoleId());//把需要解绑的角色对应的权限放入集合中
                Set<Long> remainPermissions = new LinkedHashSet<>(); //用于存放需要被保留的权限
                Set<Long> remainRoles = userRoleMapper.getRoleIdByUser(userRoleUnbindDTO.getUserId()); //用户解绑后剩下的角色

                for (long roleId1 : remainRoles) { //获取剩余角色的所有权限
                    Set<Long> permissions = roleRightMapper.getRightsByRoleId(roleId1);
                    remainPermissions.addAll(permissions);
                }

                unBindPermissions.remove(remainPermissions); //解绑权限 = 解绑角色权限 - 剩余角色的权限

                //删除用户因解绑角色而失去的权限
                for (long rightId : remainPermissions) {
                    userRightMapper.userRightUnbind(userId, rightId);
                }

                //删除用户角色
                userRoleMapper.deleteUserRole(userId, roleId);
//        userRoleMapper.userRoleUnbind(userRolePK, unBindPermissionsList);
            }
        }
    }

    @Override
    public Object getUserRoles(GetUserRolesDTO getUserRolesDTO) {

        int pageSize = getUserRolesDTO.getPageSize();
        startPage(getUserRolesDTO.getPageNum(), pageSize);

        List<GetUserRolesResultDTO> list = userRoleMapper.getUserRoles(getUserRolesDTO);

        PageInfo<GetUserRolesResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

}


