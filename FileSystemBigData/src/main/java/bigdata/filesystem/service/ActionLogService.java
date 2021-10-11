package bigdata.filesystem.service;

import bigdata.filesystem.dto.ActionLogDeleteDTO;
import bigdata.filesystem.dto.ActionLogUnfreezeDTO;
import bigdata.filesystem.dto.query.GetLogsDTO;
import bigdata.filesystem.entity.ActionLog;

import java.util.List;

public interface ActionLogService {
    void unfreezeActionLog(ActionLogUnfreezeDTO actionLogUnfreezeDTO);

    void deleteActionLog(ActionLogDeleteDTO actionLogDeleteDTO);

    Object getLogs(GetLogsDTO getLogsDTO);

    void hardDeleteActionLog(ActionLogDeleteDTO actionLogDeleteDTO);

    Object getHomeData();
}
