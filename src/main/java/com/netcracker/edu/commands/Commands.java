package com.netcracker.edu.commands;

import java.util.HashMap;

/**
 * Created by Zhassulan on 23.10.2015.
 */
public final class Commands {
    private static HashMap<String,String> map;
    private Commands() {
    }
    public static HashMap<String,String> getAllCommands(){
        if(map==null){
            map=new HashMap<>();
            map.put("hello", "com.netcracker.edu.commands.HelloWorldCommand");
        }
        return map;
    }
}
