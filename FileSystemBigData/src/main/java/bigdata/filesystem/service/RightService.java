package bigdata.filesystem.service;

import bigdata.filesystem.dto.RightCreateDTO;
import bigdata.filesystem.dto.RightDeleteDTO;
import bigdata.filesystem.dto.RightUpdateDTO;
import bigdata.filesystem.dto.query.GetRightsDTO;

public interface RightService {

    void createRight(RightCreateDTO rightCreateDTO);

    void updateRight(RightUpdateDTO rightUpdateDTO);

    long checkRightId(Long rightId);

    void deleteRight(RightDeleteDTO rightDeleteDTO);

    Object getRights(GetRightsDTO getRightsDTO);

    void checkRight(Long userId, String url, Long catalogId, Long fileId);
}
