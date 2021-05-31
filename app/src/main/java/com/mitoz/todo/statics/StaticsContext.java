package com.mitoz.todo.statics;

import android.content.Context;

public class StaticsContext {
public static Context cntx;
    public static Context context(Context context) {
        System.out.println("gitti");
        cntx = context;
        return context;
    }

}
