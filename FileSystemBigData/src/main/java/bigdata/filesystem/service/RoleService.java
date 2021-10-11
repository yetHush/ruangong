package bigdata.filesystem.service;

import bigdata.filesystem.dto.*;
import bigdata.filesystem.dto.query.GetRolesDTO;

public interface RoleService {

    void createRole(RoleCreateDTO roleCreateDTO);

    long checkRoleByName(String name);

    void updateRole(RoleUpdateDTO roleUpdateDTO);

    long getNameCountByRoleName(String roleName);

    void freezeRole(RoleFreezeDTO roleFreezeDTO);

    void unfreezeRole(RoleUnfreezeDTO roleUnfreezeDTO);

    void deleteRole(RoleDeleteDTO roleDeleteDTO);

    Object getRoles(GetRolesDTO getRolesDTO);
}
