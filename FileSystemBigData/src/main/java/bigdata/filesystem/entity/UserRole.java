package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserRole implements Serializable {
    @Id
    private Long userId; //自增
    @Id
    private Long roleId; //自增

    private Date createTime;
    private String createUser;
}
