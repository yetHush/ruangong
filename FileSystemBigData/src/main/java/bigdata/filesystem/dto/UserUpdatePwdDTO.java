package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@NotEmpty 用在集合类上面
//@NotBlank 用在String上面
//@NotNull 用在基本类型上

@Data
public class UserUpdatePwdDTO implements Serializable {

//    @NotNull
//    private Long userId;//PK

    @NotBlank
    private String userNum;

//    @NotBlank
//    private String username;

    @NotBlank
    private String userPwd;

//    @NotBlank
//    private String userPwdAgain;

    @NotBlank
    private String oldPwd;

//    private Integer sex;

//    private String email;

//    private String phoneNum;

//    private String remark;

//    @NotBlank
//    private String updateUser;

//    private Integer status;

}
