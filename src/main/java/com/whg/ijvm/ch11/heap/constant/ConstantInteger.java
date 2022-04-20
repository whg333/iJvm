package com.whg.ijvm.ch11.heap.constant;

import com.whg.ijvm.ch11.classfile.constantinfo.numeric.ConstantIntegerInfo;

public class ConstantInteger implements Constant {

    public final int val;

    public ConstantInteger(ConstantIntegerInfo info) {
        val = info.val;
    }
}
