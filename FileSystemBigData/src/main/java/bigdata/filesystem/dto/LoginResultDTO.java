package bigdata.filesystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResultDTO implements Serializable {

    private String userNum; //工号，唯一

    private String username;

    private Integer sex;

    private String email;

    private String phoneNum;

    private String remark;

    private Integer status;

    private String token;
}
