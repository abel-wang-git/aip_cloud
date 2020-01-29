package com.wanghuiwen.core.conifg.resource;

import java.util.ArrayList;
import java.util.List;

public class WhiteList {

    private List<String> whiteList = new ArrayList<>();

    {
        whiteList.add("/favicon.ico");
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
