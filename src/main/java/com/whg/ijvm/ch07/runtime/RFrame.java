package com.whg.ijvm.ch07.runtime;

import com.whg.ijvm.ch07.heap.RMethod;

public class RFrame {

    RFrame lower;
    private LocalVars localVars;
    private OperandStack operandStack;

    RThread thread;
    RMethod method;
    int nextPc;

    RFrame(RThread thread, RMethod method){
        this(method.getMaxLocals(), method.getMaxStack());
        this.thread = thread;
        this.method = method;
    }

    //for test
    public RFrame(int maxLocals, int maxStack){
        localVars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }

    // 重新指向当前指令（即重新执行）
    public void revertNextPc() {
        nextPc = thread.pc;
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public int getPc(){
        return thread.getPc();
    }

    public RThread getThread() {
        return thread;
    }
    public RMethod getMethod() {
        return method;
    }

    public void setNextPc(int nextPc) {
        this.nextPc = nextPc;
    }
    public int getNextPc() {
        return nextPc;
    }

}
