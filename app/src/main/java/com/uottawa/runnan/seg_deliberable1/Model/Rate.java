package com.uottawa.runnan.seg_deliberable1.Model;

public class Rate {
    String spname;
    public String getSpname() {
        return spname;
    }
    public void setSpname(String spname) {
        this.spname = spname;
    }

    int mark;
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }

    public Rate(String spname, int mark) {
        this.spname = spname;
        this.mark = mark;
    }
}
