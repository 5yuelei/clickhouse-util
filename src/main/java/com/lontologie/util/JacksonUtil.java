package com.lontologie.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JacksonUtil implements Serializable {

    private static final String SERIALIZER_FAILURE = "序列化失败";
    private static final String DESERIALIZER_FAILURE = "反序列化失败";

    /**
     * Spring容器中的ObjectMapper对象
     */
    private static ObjectMapper objectMapper;

    /**
     * 是否为合法Json字符串
     *
     * @param content 待判断的字符串
     * @return 是或否
     */
    public static boolean isJson(String content) {
        try {
            getObjectMapper().readTree(content);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    /**
     * 转换为Json字符串
     *
     * @param content 待序列化的对象
     * @return 序列化后的Json字符串
     * @since 1.0
     */
    public static String toJson(Object content) {
        try {
            return getObjectMapper().writeValueAsString(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(SERIALIZER_FAILURE, e);
        }
    }

    /**
     * 转换为对象
     *
     * @param jsonContent   待转换的Json字符串
     * @param typeReference 类型引用
     * @param <T>           对象泛型
     * @return 转换后的对象
     */
    public static <T> T toObject(String jsonContent, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(jsonContent, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException(DESERIALIZER_FAILURE, e);
        }
    }

    /**
     * 转换为对象
     *
     * @param jsonContent 待转换的Json字符串
     * @param objectType  对象类型
     * @param <T>         对象泛型
     * @return 转换后的对象
     */
    public static <T> T toObject(String jsonContent, Class<T> objectType) {
        try {
            return getObjectMapper().readValue(jsonContent, objectType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException(DESERIALIZER_FAILURE, e);
        }
    }

    /**
     * 转换为List
     *
     * @param jsonContent  待转换的Json字符串
     * @param elementClass List集合里元素的Class
     * @param <T>          List集合里的对象泛型
     * @return 转换后的集合
     */
    public static <T> List<T> toList(String jsonContent, Class<T> elementClass) {
        try {
            CollectionType collectionType = getObjectMapper().getTypeFactory().constructCollectionType(List.class, elementClass);
            return getObjectMapper().readValue(jsonContent, collectionType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException(DESERIALIZER_FAILURE, e);
        }
    }

    /**
     * 获取ObjectMapper
     * 如果未设置，则创建默认ObjectMapper对象
     *
     * @return ObjectMapper对象
     */
    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    /**
     * 设置ObjectMapper
     *
     * @param objectMapper ObjectMapper对象
     */
    public static void setObjectMapper(ObjectMapper objectMapper) {
        JacksonUtil.objectMapper = objectMapper;
    }

}

