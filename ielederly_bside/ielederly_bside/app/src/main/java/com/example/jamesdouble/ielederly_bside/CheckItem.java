package com.example.jamesdouble.ielederly_bside;

/**
 * Created by JamesDouble on 16/4/8.
 */
public class CheckItem {
    String str;
    long id;
    Integer weight;

    public CheckItem(String strs,Integer w)
    {
        str = strs;
        weight = w;
    }
    public CheckItem() {
        str = "";
        weight = 0;
    }
}

