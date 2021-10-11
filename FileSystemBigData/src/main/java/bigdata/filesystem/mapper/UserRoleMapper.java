package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetUserRolesDTO;
import bigdata.filesystem.dto.query.GetUserRolesResultDTO;
import bigdata.filesystem.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    Long checkUserRole(Long userId, Long roleId);

    Set<Long> getRoleIdByUser(Long userId);

    void deleteUserRole(long userId, long roleId);

    List<GetUserRolesResultDTO> getUserRoles(GetUserRolesDTO getUserRolesDTO);

    List<UserRole> getUserRolesByUserId(@Param("userId") Long userId, @Param("status") Integer status);
}
