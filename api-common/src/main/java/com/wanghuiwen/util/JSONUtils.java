package com.wanghuiwen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtils() {

    }

    public static ObjectMapper getInstance() {

        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz)
            throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json string convert to map
     */
    public static <T> Map json2map(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);

        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, javaType);
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        List<Map<String, Object>> list;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            list = objectMapper.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();

        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * 转换基本类型的list
     */
    public static <T> List<T> json22list(String jsonArrayStr, Class<T> clazz) {
        List<T> list;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            list = objectMapper.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return list;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Object map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }
}
