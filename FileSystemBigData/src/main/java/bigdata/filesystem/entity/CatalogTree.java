package bigdata.filesystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CatalogTree
 * @Description: 用于给前端返回树型目录结构
 * @Author: hxy
 * @Date: 2021-05-03 13:22
 * @Version: v1.0
 */
@Data
public class CatalogTree implements Serializable {
  @Id
  private Long catalogId; //自增

  private String catalogName;
  private String parentCatalogId;
  private Integer status;

  private Date createTime;
  private String createUser;

  private Date updateTime;
  private String updateUser;

  @JsonIgnore
  private CatalogTree parent;
  @JsonIgnore
  private List<CatalogTree> children = new ArrayList<>();

}
