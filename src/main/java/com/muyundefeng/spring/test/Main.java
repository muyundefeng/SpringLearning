package com.muyundefeng.spring.test;

/**
 * Created by lisheng on 17-3-2.
 */
public class Main {
    private enum type {
        ALL_TYPE(1), LANGUAGE(2), MACHINE_LEANING(3), SERVER_DEV(4), NLP(5), BIG_DATA(6), MOBILE(7);
        public final int value;

        type(int a) {
            this.value = a;
        }
    }

    public static void main(String[] args) {
        System.out.println(type.ALL_TYPE.value);
    }
}
