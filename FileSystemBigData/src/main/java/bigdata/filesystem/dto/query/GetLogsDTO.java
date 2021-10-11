package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetLogsDTO extends BaseQueryDTO implements Serializable {
    private String logName;
    private String logType;
    private String ipaddr;
    private String url;
    private String clazz;
    private String method;
    private String message;
    private Integer status;
    private Integer lineNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toTime;
}
