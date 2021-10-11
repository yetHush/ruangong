package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserFileRightUnbindDTO implements Serializable {

    @NotNull
    private Long fileId;

    @NotNull
    private Long userId;

    @NotNull
    private Long rightId;

}
