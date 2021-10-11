package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetUserRolesResultDTO implements Serializable {
    private Long userId; //自增

    private String userNum; //工号，唯一

    private String username;

    private Long roleId;

    private String roleName;

    private Date createTime;

    private String createUser;
}
