package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RightCreateDTO implements Serializable {

    @NotBlank
    private String rightName;
    @NotBlank
    private String rightType;
    @NotBlank
    private String rightUrl;

    private String remark;

    @NotBlank
    private String createUser;
}
