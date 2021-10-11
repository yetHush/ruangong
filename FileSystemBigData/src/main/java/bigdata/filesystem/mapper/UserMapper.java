package bigdata.filesystem.mapper;

import bigdata.filesystem.comn.base.BaseMapper;
import bigdata.filesystem.dto.query.GetUsersDTO;
import bigdata.filesystem.dto.query.GetUsersResultDTO;
import bigdata.filesystem.entity.DmUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<DmUser> {
    long getNumByUserNum(String userNum);

    long getNumByUserId(long userId);

    Long getUserIdByUserNum(String userNum);

    DmUser getUserByUserNum(String userNum);

    long checkUserId(Long userId);

    DmUser getUserByUserId(Long userId);

    List<GetUsersResultDTO> getUsers(GetUsersDTO getUsersDTO);

    Long getNumByStatus(@Param("status") Integer status);
}
