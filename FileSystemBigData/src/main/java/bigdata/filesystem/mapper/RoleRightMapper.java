package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetRoleRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetRoleRights4CheckBoxResultDTO;
import bigdata.filesystem.dto.query.GetRoleRightsDTO;
import bigdata.filesystem.dto.query.GetRoleRightsResultDTO;
import bigdata.filesystem.entity.RoleRight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleRightMapper extends BaseMapper<RoleRight> {

    long checkRoleId(Long roleId);

    long checkRightId(Long rightId);

    void roleRightUnbind(long roleId, long rightId);

    Set<Long> getRightsByRoleId(Long roleId);

    List<GetRoleRightsResultDTO> getRoleRights(GetRoleRightsDTO getRoleRightsDTO);

    List<GetRoleRights4CheckBoxResultDTO> getRoleRights4CheckBox(GetRoleRights4CheckBoxDTO getRoleRights4CheckBoxDTO);

    List<Long> getDownloadRights(@Param("roleId") Long roleId);
}
