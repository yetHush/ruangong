package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class FarmData implements Serializable {
    @Id
    private Long farmDataId;

    private Float soilTemperature;
    private Float soilHumidity;
    private Float illuminationIntensity;
    private Float airTemperature;
    private Float airHumidity;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

}
