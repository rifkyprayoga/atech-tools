package com.atech.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2024  Andy (Aleksander) Rozman (Atech-Software)
 *
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 *  For additional information about this project please visit our project site on
 *  https://github.com/andyrozman/atech-tools or contact us via this email:
 *  andy@atech-software.com
 *
 *  @author Andy
 *
 */
public class GsonUtils
{

    private static Gson gsonInstance = new Gson();


    public void test()
    {
    }


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
