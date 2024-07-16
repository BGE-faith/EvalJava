package com.faith.InjectEvalCode;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

class PeopleTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 参数解释：
        // loader: 加载正在转换的类的类加载器
        // className: 正在转换的类的名称（以斜杠分隔的格式）
        // classBeingRedefined: 正在被重新定义或重新转换的类对象（如果类是首次定义则为 null）
        // protectionDomain: 与正在转换的类相关联的保护域
        // classfileBuffer: 类文件的字节码表示形式(也就是没有被修改过的字节码）
        if (!className.startsWith("com/faith/InjectEvalCode/PeopleTransformer")) {
            return classfileBuffer;
        }
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classClassPath = new ClassClassPath(classBeingRedefined); //获取当前类的类路径
        pool.insertClassPath(classClassPath); //将类路径添加进classpool
        try {
            CtClass ctClass = pool.get("com.faith.InjectEvalCode.People");
//            System.out.println(ctClass.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod("say");
            System.out.println("changing class method and add some code");
            ctMethod.addLocalVariable("elapsedTime",CtClass.longType);
            ctMethod.insertBefore("System.out.println(\"injected by agent\");");
            byte[] classBytecode = ctClass.toBytecode();
            ctClass.detach();
            return classBytecode;
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        } catch (CannotCompileException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
