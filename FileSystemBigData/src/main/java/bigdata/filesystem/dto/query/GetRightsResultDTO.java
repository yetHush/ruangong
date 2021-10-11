package bigdata.filesystem.dto.query;

import bigdata.filesystem.entity.Permission;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetRightsResultDTO extends Permission implements Serializable {
}
