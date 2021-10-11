package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetCatalogRightsResultDTO implements Serializable {
    private Long userId;
    private String userNum;
    private String username;
    private Long catalogId;
    private Long rightId;
    private String rightName;
    private String catalogName;
    private String completePath;
    private Date createTime;
    private String createUser;
}
