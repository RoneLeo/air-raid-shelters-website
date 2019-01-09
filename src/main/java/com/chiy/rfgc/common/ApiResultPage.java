package com.chiy.rfgc.common;

public class ApiResultPage<T> extends ApiResult {

    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;



    public static <W> ApiResultPage<W> SUCCESS(W data, int pageNumber, int pageSize, int totalElements, int totalPages) {
        ApiResultPage result = new ApiResultPage();
        result.setPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setTotalElements(totalElements);
        result.setTotalPages(totalPages);
        result.setData(data);
        return result;
    }

    public static ApiResultPage<Object> FAILURE(String msg) {
        ApiResultPage result = new ApiResultPage();
        result.setResCode(-1);
        result.setResMsg(msg);
        return result;
    }

    public static ApiResultPage<Object> UNKNOWN() {
        ApiResultPage result = new ApiResultPage();
        result.setResCode(100);
        result.setResMsg("未登录或登录失效，请先登录");
        return result;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
