package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserRights4CheckBoxResultDTO implements Serializable {
    private Long userId;
    private Long rightId;
    private String rightName;
    private Long id;
    private String name;
    private Boolean on;
}
