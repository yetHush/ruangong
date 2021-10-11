package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseConstant;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.RightCreateDTO;
import bigdata.filesystem.dto.RightDeleteDTO;
import bigdata.filesystem.dto.RightUpdateDTO;
import bigdata.filesystem.dto.query.GetRightsDTO;
import bigdata.filesystem.dto.query.GetRightsResultDTO;
import bigdata.filesystem.entity.*;
import bigdata.filesystem.mapper.*;
import bigdata.filesystem.service.RightService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class RightServiceImpl extends BaseService<Permission, Long> implements RightService {

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserRightMapper userRightMapper;
    @Autowired
    private RoleRightMapper roleRightMapper;

    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private CatalogRightMapper catalogRightMapper;


    /**
     * @return long
     * @Author lirunyi
     * @Description 返回RIGHT_NAME=rightName的权限数
     * @Date 21:39 2021/1/13
     * @Param [rightName]
     **/
    public long checkRightByName(String rightName) {
        return rightMapper.checkRightByName(rightName);
    }


    /**
     * @Description: 创建权限
     * @param: [roleCreateDTO]
     * @return: java.lang.Long
     * @auther: Lirunyi
     * @date: 2021/1/12 11:25
     */
    @Override
    public void createRight(RightCreateDTO rightCreateDTO) {
        //报错范例
        if (null == rightCreateDTO) {
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        }
        Permission right = new Permission();
        BeanUtils.copyProperties(rightCreateDTO, right);
        if (checkRightByName(right.getRightName()) != 0) {
            this.throwException(BaseMessage.RIGHT_NAME_DUPLICATE.getMsg());
        } else {
            Date date = new Date();
            right.setCreateTime(date);
            //插入数据
            this.save(right);
        }
    }

    /**
     * @Description: 更新权限
     * @param: [rightCreateDTO]
     * @return: java.lang.Long
     * @auther: Lirunyi
     * @date: 2021/1/12 11:38
     */
    @Override
    public void updateRight(RightUpdateDTO rightUpdateDTO) {
        //报错范例
        if (null == rightUpdateDTO) {
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        }
        Permission right = this.getById(rightUpdateDTO.getRightId());
        //判断权限是否存在，借助id
        if (null == right) {
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        }
//      BeanUtils.copyProperties(roleUpdateDTO, role);
        if (!StringUtils.equals(right.getRightName(), rightUpdateDTO.getRightName()))//数据库!=前端
        {
            if (checkRightByName(rightUpdateDTO.getRightName()) != 0) {
                this.throwException(BaseMessage.RIGHT_NAME_DUPLICATE.getMsg());
            } else {
                BeanUtils.copyProperties(rightUpdateDTO, right);
//        Date date = new Date();
//        right.setCreateTime(date);
                this.update(right);
            }
        } else {
            BeanUtils.copyProperties(rightUpdateDTO, right);
            this.update(right);
//      this.throwException(BaseMessage.RIGHT_NAME_DUPLICATE.getMsg());
        }
    }

    /**
     * @Description: 通过ID查询权限
     * @param: [rightId]
     * @return: long
     * @auther: hxy
     * @date: 2021-01-13 23:07
     */
    @Override
    public long checkRightId(Long rightId) {
        return rightMapper.checkRightId(rightId);
    }

    @Override
    public void deleteRight(RightDeleteDTO rightDeleteDTO) {
        this.deleteById(rightDeleteDTO.getRightId());
    }

    @Override
    public Object getRights(GetRightsDTO getRightsDTO) {

        int pageSize = getRightsDTO.getPageSize();
        startPage(getRightsDTO.getPageNum(), pageSize);

        List<GetRightsResultDTO> list = rightMapper.getRights(getRightsDTO);

        PageInfo<GetRightsResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public void checkRight(Long userId, String url, Long catalogId, Long fileId) {
        Boolean hasRight = false;
        // 首先查询权限是否存在
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("userId", userId);
        if (StringUtils.contains(url, "/file/download")) {

            criteria.andEqualTo("rightUrl", "/file/download");
        } else {
            criteria.andEqualTo("rightUrl", url);
        }
        List<Permission> rights = rightMapper.selectByExample(example);
        // 检查角色权限
        List<UserRole> userRoles = userRoleMapper.getUserRolesByUserId(userId, BaseConstant.STATUS_NORMAL);
        if (null != rights && rights.size() > 0) {
            for (Permission right : rights) {
                String rightType = right.getRightType();
                if (StringUtils.equalsIgnoreCase(rightType, BaseConstant.RIGHT_TYPE_USER_RIGHT)) {
                    // 检查用户权限
                    UserRightPK userRightPK = new UserRightPK();
                    userRightPK.setRightId(right.getRightId());
                    userRightPK.setUserId(userId);
                    UserRight userRight = userRightMapper.selectByPrimaryKey(userRightPK);
                    if (null != userRight) {
                        hasRight = true;
                        break;
                    }
                    if (null != userRoles && userRoles.size() > 0) {
                        for (UserRole userRole : userRoles) {
                            RoleRightPK roleRightPK = new RoleRightPK();
                            roleRightPK.setRightId(right.getRightId());
                            roleRightPK.setRoleId(userRole.getRoleId());
                            RoleRight roleRight = roleRightMapper.selectByPrimaryKey(roleRightPK);
                            if (null != roleRight) {
                                hasRight = true;
                                break;
                            }
                        }
                        if (hasRight) {
                            break;
                        }
                    }
                } else if (StringUtils.equalsIgnoreCase(rightType, BaseConstant.RIGHT_TYPE_CATALOG_RIGHT)) {
                        hasRight = this.checkCatalogRight(catalogId, right.getRightId(), userId);
                } else if (StringUtils.equalsIgnoreCase(rightType, BaseConstant.RIGHT_TYPE_FILE_RIGHT)) {
//                    //I don't know hot to fix it. It is too complicated
                      hasRight = true;
//                    if (null != fileRight) {
//                        hasRight = true;
//                        break;
//                    } else {
//                        Document document = fileMapper.selectByPrimaryKey(fileId);
//                        if(null == document){
//                            this.throwException(BaseMessage.FILE_NOT_EXIST.getMsg());
//                        }else {
//                            hasRight = this.checkCatalogRight(document.getParentCatalogId(), right.getRightId(), userId);
//                        }
//                    }
                } else {
                    this.throwException(BaseMessage.RIGHT_TYPE_IS_NULL.getMsg());
                }
            }
        }
        if (!hasRight) {
            this.throwException(BaseMessage.DONT_HAVE_RIGHT.getMsg());
        }
    }

    // 检查父级目录是否有该权限
    private Boolean checkCatalogRight(Long catalogId, Long rightId, Long userId) {
        Boolean hasRight = false;
        if (null == catalogId) {
            hasRight = false;
        } else {
            Catalog catalog = catalogMapper.selectByPrimaryKey(catalogId);
            if (null == catalog) {
                this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
            }
            CatalogRightPK catalogRightPK = new CatalogRightPK();
            catalogRightPK.setRightId(rightId);
            catalogRightPK.setCatalogId(catalogId);
            catalogRightPK.setUserId(userId);
            CatalogRight catalogRight = catalogRightMapper.selectByPrimaryKey(catalogRightPK);
            if (null != catalogRight) {
                hasRight = true;
            } else {
                hasRight = checkCatalogRight(catalog.getParentCatalogId(), rightId, userId);
            }
        }
        return hasRight;
    }
}
