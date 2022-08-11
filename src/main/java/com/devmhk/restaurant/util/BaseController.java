package com.devmhk.restaurant.util;

public class BaseController {
    public String getPagerHtml(long totalCount, long pageSize, long pageIndex, String queryString) {
        return new PageUtil(totalCount, pageSize, pageIndex, queryString).pager();
    }
}
