package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class CatalogRight implements Serializable {

    @Id
    private Long userId;
    @Id
    private Long catalogId;
    @Id
    private Long rightId;

    private Date createTime;

    private String createUser;

}
