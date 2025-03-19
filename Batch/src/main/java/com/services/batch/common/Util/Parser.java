package com.services.batch.common.Util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Parser {
    static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    }
    public static <T> List<T> parseJson(String json, TypeReference<List<T>> typeReference) throws Exception {
        return objectMapper.readValue(json, typeReference);
    }
}
