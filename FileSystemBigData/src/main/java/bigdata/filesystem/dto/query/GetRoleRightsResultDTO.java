package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetRoleRightsResultDTO implements Serializable {
    private Long roleId;
    private Long rightId;
    private String rightName;
    private String roleName;
    private Date createTime;
    private String createUser;
}
