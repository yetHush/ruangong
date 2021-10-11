package bigdata.filesystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRightPK implements Serializable {
  private Long userId;

  private Long rightId;
}
