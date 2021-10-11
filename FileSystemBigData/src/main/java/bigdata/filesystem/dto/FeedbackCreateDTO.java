package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class FeedbackCreateDTO implements Serializable {

    @NotBlank
    private String content;

    @NotBlank
    private Date createTime;

    @NotBlank
    private String createUser;


}
