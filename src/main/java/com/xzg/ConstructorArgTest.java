package com.xzg;

import com.caselchen.designpattern.factory.ConstructorArg;

public class ConstructorArgTest {
    public static void main(String[] args) {
        ConstructorArg constructorArg1 = new ConstructorArg.Builder()
                .setArg("rateCounter")
                .setRef(true)
                .build();

        ConstructorArg constructorArg2 = new ConstructorArg.Builder()
                .setArg("127.0.0.1")
                .setType(String.class)
                .setRef(false)
                .build();
    }
}
