package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

//@NotEmpty 用在集合类上面
//@NotBlank 用在String上面
//@NotNull 用在基本类型上

@Data
public class UserCatalogRightQueryDTO implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String catalogName;

    @NotNull
    private String rightName;

    private String createUser;

    private Date createTime;


}
