package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class MakeFormatItem {

    private String formatName;


    /**
     *
     * @return title을 반환
     */
    public String getFormatName() {
        return formatName;
    }

    public MakeFormatItem(String formatName){
        this.formatName = formatName;
    }

}
