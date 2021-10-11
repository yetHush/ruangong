package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CatalogDeleteDTO implements Serializable {

    @NotNull
    private Long catalogId;

    @NotBlank
    private String updateUser;
}
