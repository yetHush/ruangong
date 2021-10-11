package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.UserCatalogRightBindDTO;
import bigdata.filesystem.dto.UserCatalogRightUnbindDTO;
import bigdata.filesystem.dto.query.*;
import bigdata.filesystem.entity.CatalogRight;
import bigdata.filesystem.entity.CatalogRightPK;
import bigdata.filesystem.mapper.CatalogMapper;
import bigdata.filesystem.mapper.CatalogRightMapper;
import bigdata.filesystem.mapper.RightMapper;
import bigdata.filesystem.mapper.UserMapper;
import bigdata.filesystem.service.CatalogRightService;
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
public class CatalogRightServiceImpl extends BaseService<CatalogRight, CatalogRightPK> implements CatalogRightService {

    @Autowired
    private CatalogRightMapper catalogRightMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private CatalogMapper catalogMapper;

    /**
     * @return long
     * @Author lirunyi
     * @Description //检查user是否存在
     * @Date 19:33 2021/1/19
     * @Param [userId]
     **/
    public long checkUserId(Long userId) {
        return catalogRightMapper.checkUserId(userId);
    }

    /**
     * @return long
     * @Author lirunyi
     * @Description //检查right是否存在
     * @Date 19:34 2021/1/19
     * @Param [rightId]
     **/
    public long checkRightId(Long rightId) {
        return catalogRightMapper.checkRightId(rightId);
    }

    /**
     * @return long
     * @Author lirunyi
     * @Description //检查catalog是否存在
     * @Date 19:34 2021/1/19
     * @Param [catalogId]
     **/
    public long checkCatalogId(Long catalogId) {
        return catalogRightMapper.checkCatalogId(catalogId);
    }

    /**
     * @Description: 用户绑定目录权限
     * @param: [userCatalogRightBindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021-01-19 19:08
     */
    @Override
    public void userCatalogRightBind(UserCatalogRightBindDTO userCatalogRightBindDTO) {
        if (null == userCatalogRightBindDTO) { //检查传入的DTO是否为空
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else if (0 == checkUserId(userCatalogRightBindDTO.getUserId())) { //检查传入的用户是否存在
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else if (0 == checkRightId(userCatalogRightBindDTO.getRightId())) { //检查传入的权限是否存在
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        } else if (0 == checkCatalogId(userCatalogRightBindDTO.getCatalogId())) { //检查传入的目录是否存在
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        } else {
            Long userId = userCatalogRightBindDTO.getUserId();
            Long catalogId = userCatalogRightBindDTO.getCatalogId();
            Long rightId = userCatalogRightBindDTO.getRightId();
            CatalogRightPK catalogRightPK = new CatalogRightPK();
            catalogRightPK.setCatalogId(catalogId);
            catalogRightPK.setUserId(userId);
            catalogRightPK.setRightId(rightId);
            CatalogRight tmpCatalogRight = this.getById(catalogRightPK);
            if (null != tmpCatalogRight) {
                this.throwException(BaseMessage.USER_CATALOG_RIGHT_EXISTED.getMsg());
            }
            String createUser = userCatalogRightBindDTO.getCreateUser();
            CatalogRight catalogRight = new CatalogRight();
            Date date = new Date();
            catalogRight.setCreateTime(date);
            catalogRight.setUserId(userId);
            catalogRight.setRightId(rightId);
            catalogRight.setCatalogId(catalogId);
//      catalogRight.setRightName(rightMapper.getRightNameById(rightId));
//      catalogRight.setCatalogName(catalogMapper.getCatalogNameById(catalogId));
//      catalogRight.setUserName(userMapper.getUserNameById(userId));
            catalogRight.setCreateUser(createUser);
            catalogRightMapper.userCatalogRightBind(catalogRight);
        }
    }

    /**
     * @Description: 用户解绑目录权限
     * @param: [userCatalogRightUnbindDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021-01-19 19:42
     */
    @Override
    public void userCatalogRightUnbind(UserCatalogRightUnbindDTO userCatalogRightUnbindDTO) {
        if (null == userCatalogRightUnbindDTO) { //检查传入的DTO是否为空
            this.throwException(BaseMessage.BIND_NOT_EXIST.getMsg());
        } else if (0 == checkUserId(userCatalogRightUnbindDTO.getUserId())) { //检查传入的用户是否存在
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        } else if (0 == checkRightId(userCatalogRightUnbindDTO.getRightId())) { //检查传入的权限是否存在
            this.throwException(BaseMessage.RIGHT_IS_NULL.getMsg());
        } else if (0 == checkCatalogId(userCatalogRightUnbindDTO.getCatalogId())) { //检查传入的目录是否存在
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        } else {
            long userId = userCatalogRightUnbindDTO.getUserId();
            long rightId = userCatalogRightUnbindDTO.getRightId();
            long catalogId = userCatalogRightUnbindDTO.getCatalogId();
            CatalogRightPK catalogRightPK = new CatalogRightPK();
            catalogRightPK.setCatalogId(catalogId);
            catalogRightPK.setUserId(userId);
            catalogRightPK.setRightId(rightId);
            CatalogRight tmpCatalogRight = this.getById(catalogRightPK);
            if (null == tmpCatalogRight) {
                this.throwException(BaseMessage.USER_CATALOG_RIGHT_NOT_EXISTED.getMsg());
            }
            this.deleteById(catalogRightPK);
        }
    }

    @Override
    public Object getCatalogRights(GetCatalogRightsDTO getCatalogRightsDTO) {

        int pageSize = getCatalogRightsDTO.getPageSize();
        startPage(getCatalogRightsDTO.getPageNum(), pageSize);

        List<GetCatalogRightsResultDTO> list = catalogRightMapper.getCatalogRights(getCatalogRightsDTO);

        PageInfo<GetCatalogRightsResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public Object getCatalogRights4CheckBox(GetCatalogRights4CheckBoxDTO getCatalogRights4CheckBoxDTO) {

        List<GetCatalogRights4CheckBoxResultDTO> list = catalogRightMapper.getCatalogRights4CheckBox(getCatalogRights4CheckBoxDTO);
        return list;
    }
}
