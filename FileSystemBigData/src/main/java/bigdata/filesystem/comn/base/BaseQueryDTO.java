package bigdata.filesystem.comn.base;

public class BaseQueryDTO {
    /**
     * 分页条数，为0时不分页
     */
    private int pageSize;

    /**
     * 分页页数，从1开始
     */
    private int pageNum;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setLimit(int limit) {
        this.pageSize = limit;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPage(int page) {
        this.pageNum = page;
    }
}
