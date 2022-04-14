package com.whg.ijvm.ch09.heap.constant;

import com.whg.ijvm.ch09.classfile.constantinfo.numeric.ConstantLongInfo;

public class ConstantLong implements Constant {

    public final long val;

    public ConstantLong(ConstantLongInfo info) {
        val = info.val;
    }
}
