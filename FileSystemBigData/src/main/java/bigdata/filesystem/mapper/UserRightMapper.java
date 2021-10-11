package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetUserRights4CheckBoxDTO;
import bigdata.filesystem.dto.query.GetUserRights4CheckBoxResultDTO;
import bigdata.filesystem.dto.query.GetUserRightsDTO;
import bigdata.filesystem.dto.query.GetUserRightsResultDTO;
import bigdata.filesystem.entity.UserRight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserRightMapper extends BaseMapper<UserRight> {

    long checkUserRight(long userId, long rightId);

    void userRightBind(long userId, long rightId, Date date, String createTime, String createUser);

    void userRightUnbind(long userId, long rightId);

    List<GetUserRightsResultDTO> getUserRights(GetUserRightsDTO getUserRightsDTO);

    List<GetUserRights4CheckBoxResultDTO> getUserRights4CheckBox(GetUserRights4CheckBoxDTO getUserRights4CheckBoxDTO);

    List<Long> getDownloadRights(@Param("userId") Long userId);
}
