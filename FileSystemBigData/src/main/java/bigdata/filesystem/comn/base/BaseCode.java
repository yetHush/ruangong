package bigdata.filesystem.comn.base;

public enum BaseCode {
    SUCCESS("000"),
    //异常
    EXCEPTION("111"),
    //用户Token无效
    TOKEN_INVALID("001"),
    ;
    private final String code;

    BaseCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "[" + getCode() + "]";
    }
}
