package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AutoCreateDTO implements Serializable {

    @NotBlank
    private String tableName;

    @NotBlank
    private String content;

    @NotBlank
    private String createUser;
}
