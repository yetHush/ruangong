package bigdata.filesystem.dto.query;

import bigdata.filesystem.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetRolesResultDTO extends Role implements Serializable {
}
