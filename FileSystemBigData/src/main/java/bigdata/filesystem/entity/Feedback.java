package bigdata.filesystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class Feedback implements Serializable {
    @Id
    private Long feedbackId;

    private String content; //feedback content

    private Date createTime;

    private String createUser;

    private Date processTime;

    private String processUser;

    private String reply; //reply content

}
