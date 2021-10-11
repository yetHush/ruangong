package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class FarmDataCreateDTO implements Serializable {

    @NotBlank
    private Float soilTemperature;
    @NotBlank
    private Float soilHumidity;
    @NotBlank
    private Float illuminationIntensity;
    @NotBlank
    private Float airTemperature;
    @NotBlank
    private Float airHumidity;
    @NotBlank
    private Long parentCatalogId;

//    private String remark;

    @NotBlank
    private String createUser;
}
