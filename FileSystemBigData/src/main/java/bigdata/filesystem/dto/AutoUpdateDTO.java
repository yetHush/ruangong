package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AutoUpdateDTO implements Serializable {

    @NotNull
    private Long autoId;

    @NotBlank
    private String tableName;

    @NotBlank
    private String content;

    @NotBlank
    private String updateUser;
}
