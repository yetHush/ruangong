package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Catalog implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long catalogId; //自增

    private String catalogName;
    private Long parentCatalogId;
    private Integer status;

    private Date createTime;
    private String createUser;

    private Date updateTime;
    private String updateUser;

    private String completePath;
}
