package com.hotabmax.filters.resultForFilterAutorization;

import javax.servlet.http.Cookie;

public class ResultForFilterAutorization {
    private String resultPage;
    private Cookie cookie;
    public ResultForFilterAutorization(String resultPage, Cookie cookie){
        this.resultPage = resultPage;
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getResultPage() {
        return resultPage;
    }
}
