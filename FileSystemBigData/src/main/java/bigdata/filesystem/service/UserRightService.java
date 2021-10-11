package bigdata.filesystem.service;

import bigdata.filesystem.dto.UserRightBindDTO;
import bigdata.filesystem.dto.UserRightUnbindDTO;
import bigdata.filesystem.dto.query.GetUserRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetUserRightsDTO;

public interface UserRightService {
    void userRightBind(UserRightBindDTO userRightBindDTO);

    void userRightUnbind(UserRightUnbindDTO userRightUnbindDTO);

    Object getUserRights(GetUserRightsDTO getUserRightsDTO);

    Object getUserRights4CheckBox(GetUserRights4CheckBoxDTO getUserRights4CheckBoxDTO);
}
