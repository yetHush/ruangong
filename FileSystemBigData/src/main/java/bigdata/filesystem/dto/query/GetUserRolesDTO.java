package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserRolesDTO extends BaseQueryDTO implements Serializable {
    private String username;

    private String roleName;
}
