package com.faith.InjectEvalCode;

public class InjectMain {
    public static void main(String[] args) throws InterruptedException {
        while(true){
            new People().say();
            Thread.sleep(5000);
        }
    }

}

