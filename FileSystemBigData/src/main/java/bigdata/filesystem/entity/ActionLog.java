package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class ActionLog implements Serializable {

    @Id
    private Long logId; //自增
    private String logName;
    private String logType;
    private String ipaddr;
    private String url;
    private String browser;
    private String os;
    private String clazz;
    private String method;
    private String message;
    private Integer status;
    private Integer lineNum;
    private Date createTime;
    private String createUser;
}
