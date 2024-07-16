package com.faith.InjectEvalCode;

import com.sun.tools.attach.VirtualMachine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InjectToAgent {
    public static void main(String[] args) throws Exception {
        String pid = getjarpid().trim();
//        System.out.println(pid);
        VirtualMachine attachvm = VirtualMachine.attach(pid);
        attachvm.loadAgent("/Users/faith27/toos/codes/java/EvalJava/target/InjectAgent-1.0-SNAPSHOT-jar-with-dependencies.jar");
        attachvm.detach();



    }
    private static String getjarpid() throws Exception {
        Process ps = Runtime.getRuntime().exec("jps");
        InputStream is = ps.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bis = new BufferedReader(isr);
        String line;
        StringBuilder sb = new StringBuilder();
        String result = null;

        // 读取 jps 命令的输出
        while ((line = bis.readLine()) != null) {
            sb.append(line).append(";");
        }

        // 将输出分割成多行
        String[] processes = sb.toString().split(";");
        for (String process : processes) {
            // 找到包含 "jar" 的进程行
            if (process.contains("jar")) {
//                System.out.println(process);
                result = process.substring(0, process.indexOf(" "));
                System.out.println(result);
                break;
            }
        }
        return result;
    }

}
