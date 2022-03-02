package com.whg.ijvm.ch07.classfile.attribute.table;

import com.whg.ijvm.ch07.classfile.uint.Uint16;

public class LineNumberTableEntry {

    Uint16 startPc;
    Uint16 lineNumber;

    public LineNumberTableEntry(Uint16 startPc, Uint16 lineNumber) {
        this.startPc = startPc;
        this.lineNumber = lineNumber;
    }

}
