package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseConstant;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;

import bigdata.filesystem.comn.utils.TokenUtil;
import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetCatalogsDTO;
import bigdata.filesystem.dto.query.GetCatalogsFilesResultDTO;
import bigdata.filesystem.dto.query.GetCatalogsResultDTO;
import bigdata.filesystem.entity.*;
import bigdata.filesystem.mapper.*;
import bigdata.filesystem.service.CatalogService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static bigdata.filesystem.comn.base.BaseConstant.*;

@Slf4j
@Service
@Transactional
public class CatalogServiceImpl extends BaseService<Catalog, Long> implements CatalogService {

    @Autowired
    private CatalogMapper catalogMapper;

    @Autowired
    private CatalogRightMapper catalogRightMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RightMapper rightMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserRightMapper userRightMapper;
    @Autowired
    private RoleRightMapper roleRightMapper;

    /**
     * @return long
     * @Author lirunyi
     * @Description 根据目录ID检查是否存在
     * @Date 11:44 2021/1/15
     * @Param catalogId
     **/
    @Override
    public long checkCatalogId(Long catalogId) {
        return catalogMapper.checkCatalogId(catalogId);
    }


    /**
     * @Description: 创建目录
     * @param: [catalogCreateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:22
     */
    @Override
    public void createCatalog(CatalogCreateDTO catalogCreateDTO) {
        System.out.println("just neglect it");
//        //检查是否传入
//        if (null == catalogCreateDTO) {
//            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
//        }
//        if (!legalCatalog(catalogCreateDTO.getCatalogName())) {
//            this.throwException(BaseMessage.CATALOG_NAME_ILLEGAL.getMsg());
//        }
//        Catalog catalog = new Catalog();
//        BeanUtils.copyProperties(catalogCreateDTO, catalog);
//        Date now = new Date();
//        catalog.setCreateTime(now);
//        catalog.setStatus(STATUS_FREEZE);
//        if (null == catalogCreateDTO.getParentCatalogId()) {
//            catalog.setCompletePath("/" + catalogCreateDTO.getCatalogName());
//            File newCatalog = FileUtil.getSaveFile(catalogCreateDTO.getCatalogName());
//            if (!newCatalog.exists()) {
//                newCatalog.mkdirs();
//            } else {
//                this.throwException(BaseMessage.CATALOG_NAME_DUPLICATE.getMsg());
//            }
//            this.save(catalog);
//        } else {
//            Catalog parentCatalog = this.getById(catalogCreateDTO.getParentCatalogId());
//            if (null == parentCatalog) {
//                this.throwException(BaseMessage.PARENT_CATALOG_NOT_EXISTS.getMsg());
//            }
//            if (STATUS_NORMAL != parentCatalog.getStatus()) {
//                this.throwException(BaseMessage.CATALOG_NOT_NORMAL.getMsg());
//            }
//            catalog.setParentCatalogId(catalogCreateDTO.getParentCatalogId());
//            if (StringUtils.isBlank(parentCatalog.getCompletePath())) {
//                this.throwException(BaseMessage.PARENT_COMPLETE_PATH_NOT_EXIST.getMsg());
//            }
//            String completePath = parentCatalog.getCompletePath() + "/" + catalogCreateDTO.getCatalogName(); //拼接完整的目录路径
//            catalog.setCompletePath(completePath);
//            File newCatalog = FileUtil.getSaveFile(completePath);
//            if (!newCatalog.exists()) {
//                newCatalog.mkdirs();
//            } else {
//                this.throwException(BaseMessage.CATALOG_NAME_ILLEGAL.getMsg());
//            }
//            this.save(catalog);
//            // 获取父目录权限
//            DmUser dmUser = userMapper.getUserByUserNum(catalogCreateDTO.getCreateUser());
//            if (null == dmUser) {
//                this.throwException(BaseMessage.USER_NOEXIST.getMsg());
//            }
//            Example example = new Example(CatalogRight.class);
//            Example.Criteria criteria = example.createCriteria();
//            criteria.andEqualTo("catalogId", parentCatalog.getCatalogId());
//            criteria.andEqualTo("userId", dmUser.getUserId());
//            List<CatalogRight> catalogRights = catalogRightMapper.selectByExample(example);
//            if (null != catalogRights && catalogRights.size() > 0) {
//                for (CatalogRight catalogRight : catalogRights) {
//                    catalogRight.setCatalogId(catalog.getCatalogId());
//                    catalogRight.setCreateTime(now);
//                    catalogRight.setCreateUser(catalogCreateDTO.getCreateUser());
//                    catalogRightMapper.insert(catalogRight);
//                }
//            }
//        }
    }

    @Override
    public void updateCatalog(CatalogUpdateDTO catalogUpdateDTO) {
        //检查是否传入
        if (null == catalogUpdateDTO) {
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        }
        Catalog catalog = this.getById(catalogUpdateDTO.getCatalogId());
        if (StringUtils.equalsIgnoreCase(catalog.getCatalogName(), catalogUpdateDTO.getCatalogName())) {
            this.throwException(BaseMessage.CATALOG_NAME_NOT_CHANGED.getMsg());
        }
        if (!legalCatalog(catalogUpdateDTO.getCatalogName())) {
            this.throwException(BaseMessage.CATALOG_NAME_ILLEGAL.getMsg());
        }
        String newCompletePath = "";
        if (null == catalog.getParentCatalogId()) {
            newCompletePath = "/" + catalogUpdateDTO.getCatalogName();
        } else {
            Catalog parentCatalog = this.getById(catalog.getParentCatalogId());
            if (null == parentCatalog) {
                this.throwException(BaseMessage.PARENT_CATALOG_NOT_EXISTS.getMsg());
            }
            if (StringUtils.isBlank(parentCatalog.getCompletePath())) {
                this.throwException(BaseMessage.PARENT_COMPLETE_PATH_NOT_EXIST.getMsg());
            }
            newCompletePath = parentCatalog.getCompletePath() + "/" + catalogUpdateDTO.getCatalogName(); //拼接完整的目录路径
        }
        // 获取完整路径，修改路径
        String completePath = catalog.getCompletePath();
        if (StringUtils.isBlank(completePath)) {
            this.throwException(BaseMessage.COMPLETE_PATH_NOT_EXIST.getMsg());
        }
//        File newCatalog = FileUtil.getSaveFile(completePath);
//        if (!newCatalog.exists()) {
//            newCatalog.mkdirs();
//        } else {
//            File newCatalogName = FileUtil.getSaveFile(newCompletePath);
//            newCatalog.renameTo(newCatalogName);
//        }
        BeanUtils.copyProperties(catalogUpdateDTO, catalog);
        Date now = new Date();
        catalog.setUpdateTime(now);
        catalog.setCompletePath(newCompletePath);
        this.update(catalog);

        // 更新子目录和文件
        this.updateChildrenCatalogPath(catalog.getCatalogId(), completePath, newCompletePath, catalogUpdateDTO.getUpdateUser(), now);
    }

    private void updateChildrenCatalogPath(Long parentCatalogId, String oldParentPath, String newParentPath, String userNum, Date now) {
        // 获取当前目录文件
        System.out.println("Waiting to be deleted");
//        Example example = new Example(Document.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("parentCatalogId", parentCatalogId);
//        List<Document> files = fileMapper.selectByExample(example);
//        if (null != files && files.size() > 0) {
//            for (Document file : files) {
//                String filePath = file.getFilePath();
//                String newfilePath = filePath.replaceFirst(oldParentPath, newParentPath);
//                file.setFilePath(filePath);
//                file.setUpdateTime(now);
//                file.setUpdateUser(userNum);
//                fileMapper.updateByPrimaryKey(file);
//            }
//        }
//        // 获取子目录
//        List<Catalog> catalogList = this.getChildById(parentCatalogId);
//        if (null != catalogList && catalogList.size() > 0) {
//            for (Catalog catalog : catalogList) {
//                String completePath = catalog.getCompletePath();
//                if (StringUtils.isBlank(completePath)) {
//                    this.throwException(BaseMessage.PARENT_COMPLETE_PATH_NOT_EXIST.getMsg());
//                }
//                String newCompletePath = completePath.replaceFirst(oldParentPath, newParentPath);
//                catalog.setCompletePath(newCompletePath);
//                catalog.setUpdateUser(userNum);
//                catalog.setUpdateTime(now);
//                this.update(catalog);
//                this.updateChildrenCatalogPath(catalog.getCatalogId(), oldParentPath, newParentPath, userNum, now);
//            }
//        }
    }

    /**
     * @return boolean
     * @Author lirunyi
     * @Description 判断目录名是否合法
     * @Date 21:50 2021/1/21
     * @Param [s]
     **/
    public boolean legalCatalog(String s) {
        char[] ss = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (ss[i] == '/' || ss[i] == '\\' || ss[i] == '*' || ss[i] == '?' || ss[i] == ':' || ss[i] == '"' || ss[i] == '<'
                    || ss[i] == '>' || ss[i] == '|') return false;
        }
        return true;
    }


    /**
     * @Description: 冻结目录
     * @param: [catalogFreezeDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:25
     */
    @Override
    public void freezeCatalog(CatalogFreezeDTO catalogFreezeDTO) {
        //检查输入的用户名是否正确
        if (checkCatalogId(catalogFreezeDTO.getCatalogId()) == 0) {
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        } else {
            Catalog catalog = this.getById(catalogFreezeDTO.getCatalogId());
            //判断角色是否存在，借助id
            if (null == catalog) {
                this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
            } else {
//      BeanUtils.copyProperties(this.getById(roleId), role); //role是需要被冻结的用户
                Date date = new Date();
                catalog.setUpdateUser(catalogFreezeDTO.getUpdateUser());
                catalog.setUpdateTime(date);
                catalog.setStatus(STATUS_FREEZE);
                this.update(catalog);
            }
        }
    }

    /**
     * @Description: 解冻目录
     * @param: [catalogUnfreezeDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:27
     */
    @Override
    public void unfreezeCatalog(CatalogUnfreezeDTO catalogUnfreezeDTO) {
        //检查输入的目录名是否正确
        if (null == catalogUnfreezeDTO) {
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        }
        Catalog catalog = this.getById(catalogUnfreezeDTO.getCatalogId());
        //判断目录是否存在，借助id
        if (null == catalog) {
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        } else {
            if (STATUS_DELETE == catalog.getStatus()) {
                this.throwException(BaseMessage.CATALOG_IS_DELETED.getMsg());
            }
//      BeanUtils.copyProperties(this.getById(roleId), role); //user是需要被解冻的用户
            catalog.setStatus(STATUS_NORMAL);
            Date date = new Date();
            catalog.setUpdateUser(catalogUnfreezeDTO.getUpdateUser());
            catalog.setUpdateTime(date);
            this.update(catalog);
        }
    }

    @Override
    public void softDeleteCatalog(CatalogDeleteDTO catalogDeleteDTO) {
        Date date = new Date();
        this.softDeleteCatalogIteration(catalogDeleteDTO.getCatalogId(), catalogDeleteDTO.getUpdateUser(), date);
    }

    private void softDeleteCatalogIteration(Long catalogId, String userNum, Date date) {
        //报错范例
        Catalog catalog = getById(catalogId);
        if (catalog == null) { //检查目录是否存在
            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
        }
        catalog.setStatus(STATUS_DELETE);//1.目录其本身软删除
        catalog.setUpdateUser(userNum);
        catalog.setUpdateTime(date);
        this.update(catalog);
        catalogMapper.deleteCatalogRight(catalogId);//2.删除涉及到该目录的目录权限绑定
//        Example example = new Example(Document.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("parentCatalogId", catalogId);
////        Document selectDocument = new Document();
////        selectDocument.setParentCatalogId(catalogId);
//        List<Document> files = fileMapper.selectByExample(example);
//        if (null != files && files.size() > 0) {
//            for (Document file : files) {
//                // 1 软删除文件
//                file.setStatus(STATUS_DELETE);
//                catalog.setUpdateUser(userNum);
//                catalog.setUpdateTime(date);
//                fileMapper.updateByPrimaryKey(file);
//                // 2 删除文件权限
//                fileRightMapper.deleteFile(file.getFileId());
//            }
        }

//        // 如果目录下面还有子目录 则继续删除
//        List<Long> childrenCatalogIds = catalogMapper.getChildCatalogIdByParentCatalogId(catalogId);
//        if (null != childrenCatalogIds && childrenCatalogIds.size() > 0) {
//            for (Long childrenCatalogId : childrenCatalogIds) {
//                this.softDeleteCatalogIteration(childrenCatalogId, userNum, date);
//            }
//        }


    @Override
    public void hardDeleteCatalog(CatalogDeleteDTO catalogDeleteDTO) {
        Date date = new Date();
        this.hardDeleteCatalogIteration(catalogDeleteDTO.getCatalogId(), catalogDeleteDTO.getUpdateUser(), date);
    }

    private void hardDeleteCatalogIteration(Long catalogId, String userNum, Date date) {
        System.out.println("just neglect it");
//        //报错范例
//        Catalog catalog = getById(catalogId);
//        if (catalog == null) { //检查目录是否存在
//            this.throwException(BaseMessage.CATALOG_IS_NULL.getMsg());
//        }
//        // 硬删除目录
//        this.deleteById(catalogId);
//        String completePath = catalog.getCompletePath();
//        if (StringUtils.isBlank(completePath)) {
//            this.throwException(BaseMessage.COMPLETE_PATH_NOT_EXIST.getMsg());
//        }
//        File catalogPath = FileUtil.getSaveFile(catalog.getCompletePath());
//        cn.hutool.core.io.FileUtil.del(catalogPath);
//        catalogMapper.deleteCatalogRight(catalogId);//2.删除涉及到该目录的目录权限绑定
//        Example example = new Example(Document.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("parentCatalogId", catalogId);
////        Document selectDocument = new Document();
////        selectDocument.setParentCatalogId(catalogId);
//        List<Document> files = fileMapper.selectByExample(example);
//        if (null != files && files.size() > 0) {
//            for (Document file : files) {
//                // 1 硬删除文件
////                file.setStatus(STATUS_DELETE);
////                catalog.setUpdateUser(userNum);
////                catalog.setUpdateTime(date);
////                fileMapper.updateByPrimaryKey(file);
//                fileMapper.delete(file);
//                File filePath = FileUtil.getSaveFile(file.getFilePath());
//                cn.hutool.core.io.FileUtil.del(filePath);
//                // 2 删除文件权限
//                fileRightMapper.deleteFile(file.getFileId());
//                // 3 删除已经解析的文件
//                parseDocMapper.deleteByFileId(file.getFileId());
//                // 4 删除ES 文件
//                elasticSearchService.deleteDoc(file.getFileId());
//            }
//        }
//
//        // 如果目录下面还有子目录 则继续删除
//        List<Long> childrenCatalogIds = catalogMapper.getChildCatalogIdByParentCatalogId(catalogId);
//        if (null != childrenCatalogIds && childrenCatalogIds.size() > 0) {
//            for (Long childrenCatalogId : childrenCatalogIds) {
//                this.hardDeleteCatalogIteration(childrenCatalogId, userNum, date);
//            }
//        }
    }

    /**
     * @Description: 递归得到所有子目录路径
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @auther: hxy
     * @date: 2021-05-08 22:45
     */
    public List<String> getAllChildrenPath(Long catalogId) {
        List<String> allChildrenPath = getAllChildrenPath(catalogId);
        while (null != catalogMapper.getChildById(catalogId)) { //获取下一级子目录的Id
            for (Catalog c : catalogMapper.getChildById(catalogId)) {
                allChildrenPath.add(c.getCompletePath());
                getAllChildrenPath(c.getCatalogId());
            }
        }
        return allChildrenPath;
    }

    public List<Catalog> getChildById(Long catalogId) {
        return catalogMapper.getChildById(catalogId);
    }

    public List<Catalog> findByParentIsNull() {
        return catalogMapper.findByParentIsNull();
    }

    @Override
    public Object getCatalogs(GetCatalogsDTO getCatalogsDTO) {

        int pageSize = getCatalogsDTO.getPageSize();
        startPage(getCatalogsDTO.getPageNum(), pageSize);

        List<GetCatalogsResultDTO> list = catalogMapper.getCatalogs(getCatalogsDTO);

        PageInfo<GetCatalogsResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public Object getCatalogsFiles() {
        List<GetCatalogsFilesResultDTO> catalogsFiles = new ArrayList<>();
        // 先根据用户权限，查询到能够访问到的目录及文件
        String userNum = TokenUtil.getUserNum();
        DmUser dmUser = userMapper.getUserByUserNum(userNum);
        if (null == dmUser) {
            this.throwException(BaseMessage.USER_NOEXIST.getMsg());
        }
        Long userId = dmUser.getUserId();
        String rightUrl = "/catalog/getCatalogsFiles";
        // 首先查询权限是否存在
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("rightUrl", rightUrl);
        List<Permission> rights = rightMapper.selectByExample(example);
        // 查看默认查询所有
        Boolean isAdmin = false;
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
                        isAdmin = true;
                        break;
                    }
                    if (null != userRoles && userRoles.size() > 0) {
                        for (UserRole userRole : userRoles) {
                            RoleRightPK roleRightPK = new RoleRightPK();
                            roleRightPK.setRightId(right.getRightId());
                            roleRightPK.setRoleId(userRole.getRoleId());
                            RoleRight roleRight = roleRightMapper.selectByPrimaryKey(roleRightPK);
                            if (null != roleRight) {
                                isAdmin = true;
                                break;
                            }
                        }
                        if (isAdmin) {
                            break;
                        }
                    }
                }
            }
        }
        // 超管查询所有目录
        if (isAdmin) {
            // 从根路径往下追溯
            catalogsFiles = this.getCatalogsFilesTree(null);

        } else {
            // 不是超管，则根据用户具有的目录权限来设置
            this.getCatalogsFilesTreebyRights(userId, null, catalogsFiles, rights);
        }
        return catalogsFiles;
    }

    private void getCatalogsFilesTreebyRights(Long userId, Long catalogId, List<GetCatalogsFilesResultDTO> catalogsFiles, List<Permission> rights) {
        // 从根路径往下追溯
        System.out.println("just neglect it");
//        List<Document> files = fileMapper.getChildFileByParentCatalogId(catalogId);
//        for (Document file : files) {
//            Boolean hasRight = false;
//            for (Permission right : rights) {
//                FileRightPK fileRightPK = new FileRightPK();
//                fileRightPK.setRightId(right.getRightId());
//                fileRightPK.setFileId(file.getFileId());
//                fileRightPK.setUserId(userId);
//                FileRight fileRight = fileRightMapper.selectByPrimaryKey(fileRightPK);
//                if (null != fileRight) {
//                    hasRight = true;
//                }
//            }
//            if (hasRight) {
//                GetCatalogsFilesResultDTO getCatalogsFilesResultDTO = new GetCatalogsFilesResultDTO();
//                getCatalogsFilesResultDTO.setName(file.getDocumentName());
//                getCatalogsFilesResultDTO.setId(file.getFileId());
//                getCatalogsFilesResultDTO.setParentId(catalogId);
//                getCatalogsFilesResultDTO.setFileName(file.getFileName());
//                getCatalogsFilesResultDTO.setFilePath(file.getFilePath());
//                getCatalogsFilesResultDTO.setType(BaseConstant.CATALOG_OR_FILE_FILE);
//                catalogsFiles.add(getCatalogsFilesResultDTO);
//            }
//        }
//        List<Catalog> catalogs = catalogMapper.getChildCatalogByParentCatalogId(catalogId);
//        for (Catalog catalog : catalogs) {
//            Boolean hasRight = false;
//            for (Permission right : rights) {
//                CatalogRightPK catalogRightPK = new CatalogRightPK();
//                catalogRightPK.setRightId(right.getRightId());
//                catalogRightPK.setCatalogId(catalog.getCatalogId());
//                catalogRightPK.setUserId(userId);
//                CatalogRight catalogRight = catalogRightMapper.selectByPrimaryKey(catalogRightPK);
//                if (null != catalogRight) {
//                    hasRight = true;
//                }
//            }
//            if (hasRight) {
//                GetCatalogsFilesResultDTO getCatalogsFilesResultDTO = new GetCatalogsFilesResultDTO();
//                getCatalogsFilesResultDTO.setName(catalog.getCatalogName());
//                getCatalogsFilesResultDTO.setId(catalog.getCatalogId());
//                getCatalogsFilesResultDTO.setParentId(catalog.getParentCatalogId());
//                getCatalogsFilesResultDTO.setType(BaseConstant.CATALOG_OR_FILE_CATALOG);
//                getCatalogsFilesResultDTO.setChildren(this.getCatalogsFilesTree(catalog.getCatalogId()));
//                catalogsFiles.add(getCatalogsFilesResultDTO);
//            } else {
//                this.getCatalogsFilesTreebyRights(userId, catalog.getCatalogId(), catalogsFiles, rights);
//            }
//        }
    }

    private List<GetCatalogsFilesResultDTO> getCatalogsFilesTree(Long catalogId) {
        List<GetCatalogsFilesResultDTO> catalogsFiles = new ArrayList<>();
        // 从根路径往下追溯
        List<Catalog> catalogs = catalogMapper.getChildCatalogByParentCatalogId(catalogId);
        for (Catalog catalog : catalogs) {
            GetCatalogsFilesResultDTO getCatalogsFilesResultDTO = new GetCatalogsFilesResultDTO();
            getCatalogsFilesResultDTO.setName(catalog.getCatalogName());
            getCatalogsFilesResultDTO.setId(catalog.getCatalogId());
            getCatalogsFilesResultDTO.setParentId(catalogId);
            getCatalogsFilesResultDTO.setType(BaseConstant.CATALOG_OR_FILE_CATALOG);
            getCatalogsFilesResultDTO.setChildren(this.getCatalogsFilesTree(catalog.getCatalogId()));
            catalogsFiles.add(getCatalogsFilesResultDTO);

        }
//        List<Document> files = fileMapper.getChildFileByParentCatalogId(catalogId);
//        for (Document file : files) {
//            GetCatalogsFilesResultDTO getCatalogsFilesResultDTO = new GetCatalogsFilesResultDTO();
//            getCatalogsFilesResultDTO.setName(file.getDocumentName());
//            getCatalogsFilesResultDTO.setId(file.getFileId());
//            getCatalogsFilesResultDTO.setParentId(catalogId);
//            getCatalogsFilesResultDTO.setFileName(file.getFileName());
//            getCatalogsFilesResultDTO.setFilePath(file.getFilePath());
//            getCatalogsFilesResultDTO.setType(BaseConstant.CATALOG_OR_FILE_FILE);
//            catalogsFiles.add(getCatalogsFilesResultDTO);
//        }
        return catalogsFiles;
    }
}
