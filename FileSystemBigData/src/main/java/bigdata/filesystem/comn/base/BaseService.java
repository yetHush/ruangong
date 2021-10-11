package bigdata.filesystem.comn.base;

import bigdata.filesystem.comn.exception.ErrorMsgException;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;
import java.io.Serializable;

public abstract class BaseService<T, PK extends Serializable> implements IBaseService<T, PK> {

    @Autowired
    protected BaseMapper<T> baseMapper;
    @Autowired
    protected Validator validator;

    @Override
    public int save(T entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public int update(T entity) {
        return baseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateSelective(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public T getById(PK id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(PK id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    public void startPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, true, null, true);
    }

    protected void throwException(String msg) {
        ErrorMsgException exception = new ErrorMsgException(msg);
        throw exception;
    }

    protected void throwException(Exception exp, String msg) {
        ErrorMsgException exception = new ErrorMsgException(exp, msg);
        throw exception;
    }
}
