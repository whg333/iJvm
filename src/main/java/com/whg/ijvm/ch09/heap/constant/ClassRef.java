package com.whg.ijvm.ch09.heap.constant;

import com.whg.ijvm.ch09.classfile.constantinfo.member.ClassInfo;
import com.whg.ijvm.ch09.heap.RConstantPool;

public class ClassRef extends SymRef{

    public ClassRef(RConstantPool cp, ClassInfo classInfo){
        this.cp = cp;
        className = classInfo.getName();
    }

}
