package com.whg.ijvm.ch11.instruction.stack;

import com.whg.ijvm.ch11.instruction.base.NoOperandsInstruction;
import com.whg.ijvm.ch11.runtime.OperandStack;
import com.whg.ijvm.ch11.runtime.RFrame;
import com.whg.ijvm.ch11.runtime.Slot;

public class Swap {

    public static class SWAP extends NoOperandsInstruction{
        @Override
        public void execute(RFrame frame) {
            OperandStack stack = frame.getOperandStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            stack.pushSlot(slot1);
            stack.pushSlot(slot2);
        }
    }

}
