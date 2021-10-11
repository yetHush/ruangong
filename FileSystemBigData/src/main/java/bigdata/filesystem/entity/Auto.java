package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Auto implements Serializable {

    @Id
    private Long autoId; //自增
    private String tableName;
    private String content;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
