package bigdata.filesystem.dto.query;

import bigdata.filesystem.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetUserRightsResultDTO implements Serializable {
    private Long userId;
    private Long rightId;
    private String rightName;
    private String username;
    private String userNum;
    private Date createTime;

    private String createUser;
}
