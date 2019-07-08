package model;

import org.springframework.data.domain.Sort;

public final class PageModel {
    public PageModel() {
        this.pageNumber = 1;
        this.pageSize = 10;
    }
    private Integer pageNumber;
    // 当前页面条数
    private Integer pageSize;
    // 排序条件
    private Sort sort;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
