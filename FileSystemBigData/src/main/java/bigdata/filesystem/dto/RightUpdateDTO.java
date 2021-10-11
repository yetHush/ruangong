package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RightUpdateDTO implements Serializable {

    @NotNull
    private Long rightId;

    @NotBlank
    private String rightName;
    @NotBlank
    private String rightType;
    @NotBlank
    private String rightUrl;

    private String remark;

}
