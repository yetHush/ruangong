package bigdata.filesystem.entity;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class CatalogRightPK implements Serializable{
    private Long userId;

    private Long catalogId;

    private Long rightId;

}
