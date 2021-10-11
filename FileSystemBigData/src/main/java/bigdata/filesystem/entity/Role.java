package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Role implements Serializable {
    @Id
    private Long roleId; //自增
    private String roleName;
    private String remark;
    private Integer status;

    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;

}
