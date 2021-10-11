package bigdata.filesystem.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserRolePK implements Serializable {

    private Long userId;
    private Long roleId;

}
