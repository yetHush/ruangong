package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CatalogCreateDTO implements Serializable {

    @NotBlank
    private String catalogName;

    private Long parentCatalogId;

//    private String remark;

    @NotBlank
    private String createUser;
}
