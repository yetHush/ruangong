package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetAutosDTO;
import bigdata.filesystem.dto.query.GetAutosResultDTO;
import bigdata.filesystem.entity.Auto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AutoMapper extends BaseMapper<Auto> {
    List<GetAutosResultDTO> getAutos(GetAutosDTO getAutosDTO);

    List<Auto> getAutosByTableName(@Param("tableName") String tableName);
}
