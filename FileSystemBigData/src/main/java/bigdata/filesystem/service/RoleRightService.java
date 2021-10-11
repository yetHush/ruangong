package bigdata.filesystem.service;

import bigdata.filesystem.dto.RoleRightBindDTO;
import bigdata.filesystem.dto.RoleRightUnbindDTO;
import bigdata.filesystem.dto.query.GetRoleRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetRoleRightsDTO;

public interface RoleRightService {

    void roleRightBind(RoleRightBindDTO roleRightBindDTO);

    void roleRightUnbind(RoleRightUnbindDTO roleRightUnbindDTO);

    Object getRoleRights(GetRoleRightsDTO getRoleRightsDTO);

    Object getRoleRights4CheckBox(GetRoleRights4CheckBoxDTO getRoleRights4CheckBoxDTO);
}
