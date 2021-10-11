package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetRoleRights4CheckBoxResultDTO implements Serializable {
    private Long roleId;
    private Long rightId;
    private String rightName;
    private Long id;
    private String name;
    private Boolean on;
}
