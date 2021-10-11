package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetAutosDTO extends BaseQueryDTO implements Serializable {
    private String tableName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toTime;
}
