package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import bigdata.filesystem.entity.Catalog;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetCatalogsResultDTO extends Catalog implements Serializable {
    private String parentCatalogName;
}
