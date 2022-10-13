package com.devmhk.restaurant.util.page;

import com.devmhk.restaurant.util.page.PageUtil;

public class BaseController {
    public String getPagerHtml(long totalCount, long pageSize, long pageIndex, String queryString) {
        return new PageUtil(totalCount, pageSize, pageIndex, queryString).pager();
    }
}
