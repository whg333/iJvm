package com.whg.ijvm.ch06.runtime;

public class Slot {

    int num;
    RObject ref;

    @Override
    public String toString() {
        return "{" + num + " " + ref + "}";
    }
}
