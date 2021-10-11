package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CropCreateDTO implements Serializable {

    @NotBlank
    private String cropName;

    @NotBlank
    private String updateUser;
}
