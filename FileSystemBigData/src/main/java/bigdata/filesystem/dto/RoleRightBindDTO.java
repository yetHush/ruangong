package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

//@NotEmpty 用在集合类上面
//@NotBlank 用在String上面
//@NotNull 用在基本类型上

@Data
public class RoleRightBindDTO implements Serializable {

    @NotNull
    private Long roleId;

    @NotNull
    private Long rightId;

    @NotBlank
    private String createUser;

    public RoleRightBindDTO() {
    }
}
