package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AutoDeleteDTO implements Serializable {

    @NotNull
    private Long autoId;
}
