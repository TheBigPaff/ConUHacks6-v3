package com.example.tempconuhacks6;

public class Tracks {
    private String href;
    private String total;


    public Tracks(String href, String total){
        this.href = href;
        this.total = total;
    }

    public String getHref() {
        return href;
    }

    public String getTotal() {
        return total;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
