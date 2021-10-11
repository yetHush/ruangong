package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetRolesDTO;
import bigdata.filesystem.dto.query.GetRolesResultDTO;
import bigdata.filesystem.entity.Role;
import bigdata.filesystem.mapper.RoleMapper;
import bigdata.filesystem.service.RoleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static bigdata.filesystem.comn.base.BaseConstant.*;

@Slf4j
@Service
@Transactional
public class RoleServiceImpl extends BaseService<Role, Long> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public long checkRoleByName(String name) {
        return roleMapper.getRoleByName(name).size();
    }

    @Override
    public long getNameCountByRoleName(String roleName) {
        return roleMapper.getNameCountByRoleName(roleName);
    }

    /**
     * @Description: 创建角色
     * @param: [roleCreateDTO]
     * @return: java.lang.Long
     * @auther: Lirunyi
     * @date: 2021/1/11 21:15
     */
    @Override
    public void createRole(RoleCreateDTO roleCreateDTO) {
        //报错范例
        if (null == roleCreateDTO) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleCreateDTO, role);
        if (checkRoleByName(role.getRoleName()) != 0) {
            this.throwException(BaseMessage.ROLE_NAME_DUPLICATE.getMsg());
        } else {
            role.setStatus(STATUS_FREEZE);
            Date date = new Date();
            role.setCreateTime(date);
            //插入数据
            this.save(role);
        }

    }

    /**
     * @Desctiption updateRole
     * @param: [RoleUpdateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/10 11:33
     */
    @Override
    public void updateRole(RoleUpdateDTO roleUpdateDTO) {
        //报错范例
        if (null == roleUpdateDTO) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        }
        Role role = this.getById(roleUpdateDTO.getRoleId());
        //判断角色是否存在，借助id
        if (null == role) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        }
//      BeanUtils.copyProperties(roleUpdateDTO, role);
        if (!StringUtils.equals(role.getRoleName(), roleUpdateDTO.getRoleName()))//数据库!=前端
        {
            if (getNameCountByRoleName(roleUpdateDTO.getRoleName()) != 0) {
                this.throwException(BaseMessage.ROLE_NAME_DUPLICATE.getMsg());
            } else {
                BeanUtils.copyProperties(roleUpdateDTO, role);
                Date date = new Date();
                role.setUpdateTime(date);
                role.setStatus(STATUS_FREEZE);
                this.update(role);
            }
        } else {
            BeanUtils.copyProperties(roleUpdateDTO, role);
            Date date = new Date();
            role.setUpdateTime(date);
            role.setStatus(STATUS_FREEZE);
            this.update(role);
        }


    }

    /**
     * @Description: 冻结角色
     * @param: [roleId, updateRole]
     * @return: void
     * @auther: lirunyi
     * @date: 2021/1/10 17:45
     */
    @Override
    public void freezeRole(RoleFreezeDTO roleFreezeDTO) {
        //检查输入的用户名是否正确
        if (null == roleFreezeDTO) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        }
        Role role = this.getById(roleFreezeDTO.getRoleId());
        //判断角色是否存在，借助id
        if (null == role) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        } else {
//      BeanUtils.copyProperties(this.getById(roleId), role); //role是需要被冻结的用户
            role.setStatus(STATUS_FREEZE);
            Date date = new Date();
            role.setUpdateTime(date);
            role.setUpdateUser(roleFreezeDTO.getUpdateUser());

            this.update(role);
        }
    }

    /**
     * @Description: 解冻角色
     * @param: [roleId, updateRole]
     * @return: void
     * @auther: lirunyi
     * @date: 2021/1/10 18:03
     */
    @Override
    public void unfreezeRole(RoleUnfreezeDTO roleUnfreezeDTO) {
        //检查输入的用户名是否正确
        if (null == roleUnfreezeDTO) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        }
        Role role = this.getById(roleUnfreezeDTO.getRoleId());
        //判断角色是否存在，借助id
        if (null == role) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        } else {
//      BeanUtils.copyProperties(this.getById(roleId), role); //user是需要被解冻的用户
            if (role.getStatus() == STATUS_DELETE) {
                this.throwException(BaseMessage.ROLE_IS_DELETED.getMsg());
            }
            role.setStatus(STATUS_NORMAL);

            Date date = new Date();
            role.setUpdateTime(date);
            role.setUpdateUser(roleUnfreezeDTO.getUpdateUser());

            this.update(role);
        }
    }

    /**
     * @Description: 删除角色
     * @param: [roleId, updateUser]
     * @return: void
     * @auther: hxy
     * @date: 2021/1/10 22:02
     */
    @Override
    public void deleteRole(RoleDeleteDTO roleDeleteDTO) {
        //检查输入的用户名是否正确
        if (null == roleDeleteDTO) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        }
        Role role = this.getById(roleDeleteDTO.getRoleId());
        //判断角色是否存在，借助id
        if (null == role) {
            this.throwException(BaseMessage.ROLE_IS_NULL.getMsg());
        } else {
//      BeanUtils.copyProperties(this.getById(roleId), role); //user是需要被解冻的用户
            role.setStatus(STATUS_DELETE);

            Date date = new Date();
            role.setUpdateTime(date);
            role.setUpdateUser(roleDeleteDTO.getUpdateUser());

            this.update(role);
        }
    }

    @Override
    public Object getRoles(GetRolesDTO getRolesDTO) {

        int pageSize = getRolesDTO.getPageSize();
        startPage(getRolesDTO.getPageNum(), pageSize);

        List<GetRolesResultDTO> list = roleMapper.getRoles(getRolesDTO);

        PageInfo<GetRolesResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }
}
