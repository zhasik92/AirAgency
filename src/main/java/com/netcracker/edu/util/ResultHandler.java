package com.netcracker.edu.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netcracker.edu.bobjects.BusinessObject;
import com.netcracker.edu.bobjects.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Жасулан on 24.01.2016.
 */
public class ResultHandler<T extends BusinessObject> {
    private Collection<T> object=new ArrayList<>();
    private  Gson gson = new Gson();
    JsonObject jsonObject=new JsonObject();
    JsonParser parser=new JsonParser();
    private int status=-1;

    /*public ResultHandler(Collection<T> object) {
        this.object = object;
    }*/
    public void setStatus(int status){
        this.status=status;
    }

    public void setUsersRole(User user){
        jsonObject.add("authStatus",parser.parse(user.role().toString()));
    }
    public void addObject(T object){
        this.object.add(object);
    }

    public void clear(){
        object.clear();
        setStatus(-1);
    }

    @Override
    public String toString() {
        jsonObject.add("value",gson.toJsonTree(object));
        jsonObject.addProperty("commandStatus",status);
       //return gson.toJsonTree(object)+"#?"+status;
        return jsonObject.toString();
    }
}
