package bigdata.filesystem.dto.query;

import bigdata.filesystem.entity.ActionLog;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetLogsResultDTO extends ActionLog implements Serializable {
}
