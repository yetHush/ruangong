package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Crop implements Serializable {

    @Id
    private Long cropId; //自增

    private String cropName; //作物名，唯一

    private String status; // crop status, use a string to describe how the crop grows now

    private Date updateTime;

    private String updateUser;

}

