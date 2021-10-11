package bigdata.filesystem.comn.base;

import java.io.Serializable;

public interface IBaseService<T, PK extends Serializable> {

    /**
     * <p>添加一条记录</p>
     *
     * @param entity 要添加的实体对象
     * @return
     */
    int save(T entity);

    /**
     * <p>更新一条记录</p>
     *
     * @param entity 要更新的实体对象
     * @return
     */
    int update(T entity);

    /**
     * <p>选择性的更新一条记录，实体中没有的值不更新</p>
     *
     * @param entity 要更新的实体对象
     * @return
     */
    int updateSelective(T entity);

    /**
     * <p>根据主键ID获取一条记录</p>
     *
     * @param id 主键ID
     * @return 表记录对应的实体对象
     */
    T getById(PK id);

    /**
     * <p>根据主键ID删除一条记录</p>
     *
     * @param id 主键ID
     * @return
     */
    int deleteById(PK id);
}
