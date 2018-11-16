package com.medvedev.nikita.notes.objects;

import android.annotation.TargetApi;
import android.os.Build;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Body {
    public Body(){}

    @TargetApi(Build.VERSION_CODES.N)
    public Map<String, String> getAsMap()
    {
        Method[] methods = getClass().getDeclaredMethods();
        Map<String, Method> map = Arrays.stream(methods).filter(m->m.getName().contains("get"))
                .collect(Collectors.toMap(s -> {
                    String str = s.getName().replace("get","");
                    return str.substring(0,1).toLowerCase() + str.substring(1);
                }, method -> method));
        Map<String, String> o = new TreeMap<>();
        map.forEach((k,v)->{
            try {
                o.put(k, (String) v.invoke(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return o;
    }
}
