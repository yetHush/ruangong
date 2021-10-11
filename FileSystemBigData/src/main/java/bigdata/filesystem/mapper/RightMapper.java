package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetRightsDTO;
import bigdata.filesystem.dto.query.GetRightsResultDTO;
import bigdata.filesystem.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightMapper extends BaseMapper<Permission> {

    long checkRightByName(String rightName);//校验name的唯一性

    long checkRightId(Long rightId);

    List<GetRightsResultDTO> getRights(GetRightsDTO getRightsDTO);
}
