package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> classData) {
        try {
            return objectMapper.readValue(json, classData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
