package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-05-19.
 */

public class MyPageItem {

    String name, detail;

    /**
     *
     * @return title을 반환
     */
    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public MyPageItem(String name, String detail){
        this.name = name;
        this.detail = detail;
    }

}
