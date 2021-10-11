package bigdata.filesystem.comn.result;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class RespResult implements Serializable {

    private static final long serialVersionUID = -2051487665881277026L;

    /**
     * 默认值为"000"，成功状态
     * "111", 为出错
     */
    private String code = "000";

    private String msg;

    /**
     * 输出数据
     */
    private Object entity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
