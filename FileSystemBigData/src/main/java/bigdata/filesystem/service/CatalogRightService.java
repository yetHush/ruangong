package bigdata.filesystem.service;

import bigdata.filesystem.dto.UserCatalogRightBindDTO;
import bigdata.filesystem.dto.UserCatalogRightUnbindDTO;
import bigdata.filesystem.dto.query.GetCatalogRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetCatalogRightsDTO;

public interface CatalogRightService {

    void userCatalogRightUnbind(UserCatalogRightUnbindDTO userCatalogRightUnbindDTO);

    void userCatalogRightBind(UserCatalogRightBindDTO userCatalogRightBindDTO);

    Object getCatalogRights(GetCatalogRightsDTO getCatalogRightsDTO);

    Object getCatalogRights4CheckBox(GetCatalogRights4CheckBoxDTO getCatalogRights4CheckBoxDTO);
}
