package com.hassan.lalamove.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hassan.lalamove.models.Route;
import com.hassan.lalamove.models.Sender;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.room.TypeConverter;

public class RouteConverters {
    @TypeConverter
    public static Route fromString(String value) {
        Type listType = new TypeToken<Route>() {}.getType();
        Route r=new Gson().fromJson(value, listType);
        return r;
        // return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String RouteToString(Route list) {
        Gson gson = new Gson();
        return gson.toJson(list);
        // return date == null ? null : date.getTime();
    }
}
