package com.whg.ijvm.ch07.classfile.attribute;

import com.whg.ijvm.ch07.classfile.ClassReader;

public interface AttributeInfo {
    void readInfo(ClassReader reader);
    String getName();
}
