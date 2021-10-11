package bigdata.filesystem.comn.base;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface BaseInsertMapper4DM<T> {
    @InsertProvider(type = BaseInsertProvider4DM.class, method = "dynamicSQL")
    int insert(T record);

    @InsertProvider(type = BaseInsertProvider4DM.class, method = "dynamicSQL")
    int insertSelective(T record);
}
