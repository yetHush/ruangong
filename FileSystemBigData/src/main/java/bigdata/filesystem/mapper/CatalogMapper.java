package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetCatalogsDTO;
import bigdata.filesystem.dto.query.GetCatalogsResultDTO;
import bigdata.filesystem.entity.Catalog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CatalogMapper extends BaseMapper<Catalog> {

    long checkCatalogId(Long catalogId);

    String getParentCatalogId(String catalogName);

    void deleteCatalogRight(long catalogId);

    void deleteFile(String parentCatalogId);

    Long[] getChildCatalogId(Long catalogId);

    List<Catalog> getAllCatalog();

    String getCatalogNameById(Long catalogId);

    List<Catalog> findByParentIsNull();

    List<Catalog> getChildById(Long catalogId);

    List<Long> getChildCatalogIdByParentCatalogId(@Param("parentCatalogId") Long parentCatalogId);

    List<Catalog> getChildCatalogByParentCatalogId(@Param("parentCatalogId") Long parentCatalogId);

    List<GetCatalogsResultDTO> getCatalogs(GetCatalogsDTO getCatalogsDTO);
}
