package bigdata.filesystem.comn.exception;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.result.RespResult;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ErrorMsgException extends RuntimeException {

    private static final long serialVersionUID = -772460477538768284L;

    private RespResult respResult;

    public ErrorMsgException(String message) {
        if (null == respResult) {
            respResult = new RespResult();
        }
        this.respResult.setCode(BaseMessage.EXCEPTION.getMsg());
        this.respResult.setMsg(message);
    }

    public ErrorMsgException(String code, String message) {
        if (null == respResult) {
            respResult = new RespResult();
        }
        this.respResult.setCode(code);
        this.respResult.setMsg(message);
    }

    public ErrorMsgException(RespResult respResult) {
        this.respResult = respResult;
    }

    public ErrorMsgException(Exception exception, String message) {
        super(exception.getMessage(), exception.getCause());
        if (null == respResult) {
            respResult = new RespResult();
        }
        this.respResult.setCode(BaseMessage.EXCEPTION.getMsg());
        this.respResult.setMsg(message);
    }

    public ErrorMsgException(Exception exception, String code, String message) {
        super(exception.getMessage(), exception.getCause());
        if (null == respResult) {
            respResult = new RespResult();
        }
        this.respResult.setCode(code);
        this.respResult.setMsg(message);
    }

    public RespResult getRespResult() {
        return respResult;
    }

    public void setRespResult(RespResult respResult) {
        this.respResult = respResult;
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            this.printStackTrace(new PrintWriter(s));
        }
    }

    @Override
    public void printStackTrace(PrintWriter w) {
        synchronized (w) {
            w.println(this);
            StackTraceElement[] trace = getStackTrace();
            for (int i = 0; i < trace.length; i++)
                w.println("\tat " + trace[i]);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(w);
            }
            w.flush();
        }
    }
}
