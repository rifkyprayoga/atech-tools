package com.atech.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtils
{

    private static Gson gsonInstance = new Gson();


    // Type listType = new TypeToken<ArrayList<CallHuntingLineDto>>() {
    //
    // }.getType();
    //
    // return new Gson().fromJson(lines, listType);

    public static <T> List<T> getListOfType(String dataJson, Class<T> clazz)
    {

        // Consuming remote method
        // String strJson = getService().listEntity(clazz.getName());

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(dataJson).getAsJsonArray();

        List<T> lst = new ArrayList<T>();
        for (final JsonElement json : array)
        {
            T entity = gsonInstance.fromJson(json, clazz);
            lst.add(entity);
        }

        return lst;
    }
}
