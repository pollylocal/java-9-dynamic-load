package com.greetings;

import com.api.GreetingSupplier;

public class GreetingSupplierImpl implements GreetingSupplier {
    static {
        final Class<GreetingSupplierImpl> clazz = GreetingSupplierImpl.class;
        System.out.printf("Loading %s from classloader %s parent %s.\n",
                clazz, clazz.getClassLoader(), clazz.getClassLoader().getParent());
    }


    @Override
    public String getGreeting() {
        return "Greetings from module TWO!";
    }
}
