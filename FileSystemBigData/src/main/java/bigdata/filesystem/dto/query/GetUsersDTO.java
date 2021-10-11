package bigdata.filesystem.dto.query;

import bigdata.filesystem.comn.base.BaseQueryDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetUsersDTO extends BaseQueryDTO implements Serializable {
    private String userNum;

    private String username;

    private Integer sex;

    private String email;

    private String phoneNum;

    private Integer status;
}
