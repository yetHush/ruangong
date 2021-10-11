package bigdata.filesystem.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.persistence.Id;


@Data
public class Permission implements Serializable {
    @Id
    private Long rightId;

    private String rightName;
    private String rightType;
    private String rightUrl;

    private Date createTime;

    private String createUser;

}
