package model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Mongodb分页Model
 * @author 梁昊
 * @Date:2019-07-05
 * @version 1.0
 */
public class MyPageable implements Pageable {
    private PageModel page;
    @Override
    public int getPageNumber() {
        return page.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return page.getPageSize();
    }

    @Override
    public int getOffset() {
        return (page.getPageNumber() - 1) * page.getPageSize();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
