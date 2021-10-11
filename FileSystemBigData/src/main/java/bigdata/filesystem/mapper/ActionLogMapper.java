package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetLogsDTO;
import bigdata.filesystem.dto.query.GetLogsResultDTO;
import bigdata.filesystem.entity.ActionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ActionLogMapper extends BaseMapper<ActionLog> {
    List<GetLogsResultDTO> getLogs(GetLogsDTO getLogsDTO);

    Long getLoginNum(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);
    Long getSubmitNum(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);
    Long getDownloadNum(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);
    Long getUploadNum(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);
}
