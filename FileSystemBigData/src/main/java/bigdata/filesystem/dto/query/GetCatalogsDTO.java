package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetCatalogsDTO extends BaseQueryDTO implements Serializable {
    private String catalogName;
    private Integer status;
}
