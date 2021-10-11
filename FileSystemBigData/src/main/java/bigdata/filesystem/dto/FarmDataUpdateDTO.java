package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FarmDataUpdateDTO implements Serializable {

    @NotNull
    private Long farmDataId;

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

    @NotBlank
    private String updateUser;
}
