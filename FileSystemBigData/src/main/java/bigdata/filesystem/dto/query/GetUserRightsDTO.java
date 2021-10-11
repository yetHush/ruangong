package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserRightsDTO extends BaseQueryDTO implements Serializable {
    private String rightName;
    private String username;
}
