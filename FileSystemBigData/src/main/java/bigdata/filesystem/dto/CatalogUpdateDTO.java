package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CatalogUpdateDTO implements Serializable {

    @NotBlank
    private String catalogName;

    @NotNull
    private Long catalogId;

//    private String remark;

    @NotBlank
    private String updateUser;
}
