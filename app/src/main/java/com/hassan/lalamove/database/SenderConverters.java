package com.hassan.lalamove.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hassan.lalamove.models.Route;
import com.hassan.lalamove.models.Sender;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

public class SenderConverters {
    @TypeConverter
    public static Sender fromString(String value) {
        Type listType = new TypeToken<Sender>() {}.getType();
        Sender s=new Gson().fromJson(value, listType);
        return s;
        // return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String SenderToString(Sender list) {
        Gson gson = new Gson();

        return gson.toJson(list);
        // return date == null ? null : date.getTime();
    }
}
