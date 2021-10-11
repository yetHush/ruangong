package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetCatalogRights4CheckBoxDTO implements Serializable {
    private Long userId;
    private Long catalogId;
}
