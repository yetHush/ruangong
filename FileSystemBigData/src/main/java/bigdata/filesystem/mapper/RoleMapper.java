package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetRolesDTO;
import bigdata.filesystem.dto.query.GetRolesResultDTO;
import bigdata.filesystem.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoleByName(String name);

    long getNameCountByRoleName(String roleName);

    Role getRoleById(Long roleId);

    List<GetRolesResultDTO> getRoles(GetRolesDTO getRolesDTO);
}
