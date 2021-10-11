package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//@NotEmpty 用在集合类上面
//@NotBlank 用在String上面
//@NotNull 用在基本类型上

@Data
public class UserCreateDTO implements Serializable {

    @NotBlank
    private String userNum;

    @NotBlank
    private String username;

    @NotBlank
    private String userPwd;

    private Integer sex;

    private String email;

    private String phoneNum;

    private String remark;

    @NotBlank
    private String createUser;

    private Integer status;
}
