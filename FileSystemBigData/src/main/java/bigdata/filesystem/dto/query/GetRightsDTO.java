package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class GetRightsDTO extends BaseQueryDTO implements Serializable {
    private String rightName;
    private String rightType;
}
