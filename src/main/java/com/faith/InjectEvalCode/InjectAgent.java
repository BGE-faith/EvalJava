package com.faith.InjectEvalCode;

import java.lang.instrument.Instrumentation;

public class InjectAgent {
    public static void agentmain(String args, Instrumentation inst) {

        inst.addTransformer(new PeopleTransformer(),true); //这个True表示转换器可以在类被重新转换和重新定义的时候调用，如果为false，表示转换器智能在类被首次加载时调用。

    }
}

