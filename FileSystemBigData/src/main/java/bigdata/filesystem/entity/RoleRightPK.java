package bigdata.filesystem.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleRightPK implements Serializable{
    private Long roleId;

    private Long rightId;
}
