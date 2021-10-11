package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {

    @NotBlank
    private String userNum;
    @NotBlank
    private String userPwd;
    //验证码
    @NotBlank
    private String captcha;
}
