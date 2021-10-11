package bigdata.filesystem.service;

import bigdata.filesystem.dto.UserRoleBindDTO;
import bigdata.filesystem.dto.UserRoleUnbindDTO;
import bigdata.filesystem.dto.query.GetUserRolesDTO;

public interface UserRoleService {

    void userRoleBind(UserRoleBindDTO userRoleBindDTO);

    void userRoleUnbind(UserRoleUnbindDTO userRoleUnbindDTO);

    Object getUserRoles(GetUserRolesDTO getUserRolesDTO);
}
