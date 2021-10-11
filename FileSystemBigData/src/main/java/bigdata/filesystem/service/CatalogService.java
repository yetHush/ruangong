package bigdata.filesystem.service;

import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetCatalogsDTO;
import bigdata.filesystem.entity.Catalog;

import java.util.List;

public interface CatalogService {

    void createCatalog(CatalogCreateDTO catalogCreateDTO);

    void freezeCatalog(CatalogFreezeDTO catalogFreezeDTO);

    void unfreezeCatalog(CatalogUnfreezeDTO catalogUnfreezeDTO);

    void softDeleteCatalog(CatalogDeleteDTO catalogDeleteDTO);

    long checkCatalogId(Long catalogId);

    List<Catalog> findByParentIsNull();

    List<Catalog> getChildById(Long catalogId);

    Object getCatalogs(GetCatalogsDTO getCatalogsDTO);

    void hardDeleteCatalog(CatalogDeleteDTO catalogDeleteDTO);

    void updateCatalog(CatalogUpdateDTO catalogUpdateDTO);

    Object getCatalogsFiles();
}
