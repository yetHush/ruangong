package bigdata.filesystem.entity;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RoleRight implements Serializable{
    @Id
    private Long roleId;
    @Id
    private Long rightId;

    private String createUser;
    private Date createTime;
}
