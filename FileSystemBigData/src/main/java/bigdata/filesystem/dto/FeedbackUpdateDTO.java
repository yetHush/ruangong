package bigdata.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class FeedbackUpdateDTO implements Serializable {

    @NotBlank
    private String reply;

    @NotNull
    private Long feedbackId;

    @NotBlank
    private String updateUser;
}
