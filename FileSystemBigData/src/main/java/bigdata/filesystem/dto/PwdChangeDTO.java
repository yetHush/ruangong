package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@NotEmpty 用在集合类上面
//@NotBlank 用在String上面
//@NotNull 用在基本类型上

@Data
public class PwdChangeDTO implements Serializable {

    @NotNull
    private String userNum;

    @NotBlank
    private String oldUserPwd;

    @NotBlank
    private String newUserPwd;

    public PwdChangeDTO() {
    }
}
