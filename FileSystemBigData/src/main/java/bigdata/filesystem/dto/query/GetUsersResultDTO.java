package bigdata.filesystem.dto.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetUsersResultDTO implements Serializable {
    private Long userId; //自增

    private String userNum; //工号，唯一

    private String username;

    private Integer sex;

    private String email;

    private String phoneNum;

    private String remark;

    private String avatarPath;

    private Integer status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
