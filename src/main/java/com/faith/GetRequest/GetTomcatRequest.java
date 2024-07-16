package com.faith.GetRequest;

import java.util.Map;

public class GetTomcatRequest {
    public static void main(String[] args) {
//        Map<String, String> getenv = System.getenv();
//        for (String key : getenv.keySet()) {
//            System.out.println(key + " = " + getenv.get(key));
//        }
        String cmd= "";
        boolean isLinux= true;
        String osType = System.getProperty("os.name").toLowerCase();
        if (osType.contains("win")) {
            isLinux = false;
        }
        String[] cmds= isLinux ? new String[] {"sh","-c",cmd}: new String[]{"cmd.exe","/c",cmd};

    }
}
