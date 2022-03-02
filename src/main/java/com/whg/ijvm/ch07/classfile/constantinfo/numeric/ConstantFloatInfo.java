package com.whg.ijvm.ch07.classfile.constantinfo.numeric;

import com.whg.ijvm.ch07.classfile.ClassReader;
import com.whg.ijvm.ch07.classfile.constantinfo.ConstantInfo;

public class ConstantFloatInfo implements ConstantInfo {

    public float val;

    @Override
    public void readInfo(ClassReader reader) {
        val = Float.intBitsToFloat((int)reader.readUint32().value());
    }

}
