package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetCatalogRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetCatalogRights4CheckBoxResultDTO;
import bigdata.filesystem.dto.query.GetCatalogRightsDTO;
import bigdata.filesystem.dto.query.GetCatalogRightsResultDTO;
import bigdata.filesystem.entity.CatalogRight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CatalogRightMapper extends BaseMapper<CatalogRight> {

    long checkUserId(Long userId);

    long checkRightId(Long userId);

    long checkCatalogId(Long userId);

    void userCatalogRightBind(CatalogRight catalogRight);

    void userCatalogRightUnbind(long userId, long rightId, long catalogId);

    List<GetCatalogRightsResultDTO> getCatalogRights(GetCatalogRightsDTO getCatalogRightsDTO);

    List<GetCatalogRights4CheckBoxResultDTO> getCatalogRights4CheckBox(GetCatalogRights4CheckBoxDTO getCatalogRights4CheckBoxDTO);
}
