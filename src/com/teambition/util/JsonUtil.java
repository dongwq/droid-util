package com.teambition.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dongwq
 * Date: 9/12/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtil {

    /**
     * 对于从服务器端来的数据要使用ISODateAdapter， 其他数据不用使用
     */
    public static <V> ArrayList<V> jsonToList(String strJSON, Class<V> clazz) {
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.DATE_FORMAT_JSON)
                .registerTypeAdapter(Date.class, new ISODateAdapter())
                .create();

        Type listType = new TypeToken<List<V>>() {
        }.getType();
        List<V> vList = gson.fromJson(strJSON, listType);

        ArrayList<V> list = new ArrayList<V>();
        for (V v : vList) {

            String result = gson.toJson(v);
            list.add(gson.fromJson(result, clazz));
        }

        return list;
    }

    public static <V> V jsonToObj(String strJSON, Class<V> clazz) {
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.DATE_FORMAT_JSON)
                .registerTypeAdapter(Date.class, new ISODateAdapter())
                .create();
        V v = gson.fromJson(strJSON, clazz);
        return v;
    }

    public static <V> String objToJson(V obj) {
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.DATE_FORMAT_JSON)
                .registerTypeAdapter(Date.class, new ISODateAdapter())
                .create();
        String json = gson.toJson(obj);
        return json;
    }

    public static <V> String listToJson(List<V> obj) {
        String strToJSON;
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.DATE_FORMAT_JSON).create();
        Type listType = new TypeToken<List<V>>() {
        }.getType();
        strToJSON = gson.toJson(obj, listType);
        return strToJSON;
    }

    public static boolean isJson(String s) {
        JsonParser jsonParser = new JsonParser();
        try {
            if (StringUtil.isNotBlank(s))
                jsonParser.parse(s);
        } catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }

}

