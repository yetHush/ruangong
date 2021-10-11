package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class DmUser implements Serializable {

    @Id
    private Long userId; //自增

    private String userNum; //工号，唯一

    private String username;

    private String userPwd;

    private String pwdSalt;

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

