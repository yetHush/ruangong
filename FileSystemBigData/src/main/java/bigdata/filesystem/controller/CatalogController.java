package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.utils.BeanValidators;
import bigdata.filesystem.config.RightRequired;
import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetCatalogsDTO;
import bigdata.filesystem.entity.Catalog;
import bigdata.filesystem.service.CatalogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/catalog")
@RestController
public class CatalogController extends BaseController {
    @Autowired
    private CatalogService catalogService;

    /**
     * @Description: 创建目录
     * @param: [catalogCreateDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:22
     */
    @RightRequired
    @Description("创建目录")
    @ApiOperation("创建目录")
    @PostMapping("/create")
    public Response createCatalog(@RequestBody CatalogCreateDTO catalogCreateDTO) {
        // 处理DTO转换
        BeanValidators.validateWithException(validator, catalogCreateDTO);

        catalogService.createCatalog(catalogCreateDTO);

        return respSuccessResult(BaseMessage.CATALOG_CREATE_SUCCESS.getMsg());
    }

    @RightRequired
    @Description("更新目录")
    @ApiOperation("更新目录")
    @PostMapping("/update")
    public Response updateCatalog(@RequestBody CatalogUpdateDTO catalogUpdateDTO) {

        BeanValidators.validateWithException(validator, catalogUpdateDTO);

        catalogService.updateCatalog(catalogUpdateDTO);

        return respSuccessResult(BaseMessage.CATALOG_UPDATE_SUCCESS.getMsg());

    }

    /**
     * @Description: 冻结目录
     * @param: [catalogFreezeDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:25
     */
    @RightRequired
    @Description("冻结目录")
    @ApiOperation("冻结目录")
    @PostMapping("/freeze")
    public Response freezeCatalog(@RequestBody CatalogFreezeDTO catalogFreezeDTO) {
        catalogService.freezeCatalog(catalogFreezeDTO);
        return respSuccessResult(BaseMessage.CATALOG_FREEZE_SUCCESS.getMsg());
    }

    /**
     * @Description: 解冻目录
     * @param: [catalogUnfreezeDTO]
     * @return: javax.ws.rs.core.Response
     * @auther: lirunyi
     * @date: 2021/1/15 11:27
     */
    @RightRequired
    @Description("解冻目录")
    @ApiOperation("解冻目录")
    @PostMapping("/unfreeze")
    public Response unfreezeCatalog(@RequestBody CatalogUnfreezeDTO catalogUnfreezeDTO) {
        catalogService.unfreezeCatalog(catalogUnfreezeDTO);
        return respSuccessResult(BaseMessage.UNFREEZE_CATALOG_SUCCESS.getMsg());
    }

    @RightRequired
    @Description("软删除目录")
    @ApiOperation("软删除目录")
    @PostMapping("/soft/delete")
    public Response softDeleteCatalog(@RequestBody CatalogDeleteDTO catalogDeleteDTO) {
        BeanValidators.validateWithException(validator, catalogDeleteDTO);
        catalogService.softDeleteCatalog(catalogDeleteDTO);
        return respSuccessResult(BaseMessage.CATALOG_DELETE_SUCCESS.getMsg());
    }

    @RightRequired
    @Description("硬删除目录")
    @ApiOperation("硬删除目录")
    @PostMapping("/hard/delete")
    public Response hardDeleteCatalog(@RequestBody CatalogDeleteDTO catalogDeleteDTO) {
        BeanValidators.validateWithException(validator, catalogDeleteDTO);
        catalogService.hardDeleteCatalog(catalogDeleteDTO);
        return respSuccessResult(BaseMessage.CATALOG_DELETE_SUCCESS.getMsg());
    }

    @RightRequired
    @Description("获取所有目录")
    @ApiOperation("获取所有目录")
    @GetMapping("/getCatalogs")
    public Response getCatalogs(GetCatalogsDTO getCatalogsDTO) {
        Object result = catalogService.getCatalogs(getCatalogsDTO);
        return respSuccessResult(BaseMessage.SUCCESS_GET_ALL.getMsg(), result);
    }

    @RightRequired
    @Description("获取目录树")
    @ApiOperation("获取目录树")
    @GetMapping("/getCatalogTree")
    public Response getCatalogTree() {
        List<Catalog> catalogList = catalogService.findByParentIsNull();    //查找所有菜单
        List<HashMap<String, Object>> result = new ArrayList<>();    //定义一个map处理json键名问题
        for (Catalog d : catalogList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", d.getCatalogId());
            map.put("name", d.getCatalogName());
            map.put("completePath", d.getCompletePath());
            map.put("open", false);
            map.put("checked", false);
            map.put("spread", true);      //设置是否展开
            List<HashMap<String, Object>> result1 = new ArrayList<>();
            List<Catalog> children = catalogService.getChildById(d.getCatalogId());    //下级菜单
            map.put("children", fun(children, result1));
            result.add(map);
        }
        return respSuccessResult(BaseMessage.SUCCESS_GET_ALL.getMsg(), result);
    }

    private Object fun(List<Catalog> catalogList, List<HashMap<String, Object>> result) {
        for (Catalog d : catalogList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", d.getCatalogId());
            map.put("name", d.getCatalogName());
            map.put("completePath", d.getCompletePath());
            map.put("open", false);
            map.put("checked", false);
            map.put("spread", true);      //设置是否展开
            List<HashMap<String, Object>> result1 = new ArrayList<>();
            List<Catalog> children = catalogService.getChildById(d.getCatalogId());    //下级菜单
            map.put("children", fun(children, result1));
            result.add(map);
        }
        return result;
    }

    @Description("查询用户目录及文件")
    @ApiOperation("查询用户目录及文件")
    @GetMapping("/getCatalogsFiles")
    public Response getCatalogsFiles() {
        Object result = catalogService.getCatalogsFiles();
        return respSuccessResult(BaseMessage.SUCCESS_GET_ALL.getMsg(), result);
    }

}
