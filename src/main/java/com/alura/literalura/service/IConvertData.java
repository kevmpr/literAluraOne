package com.alura.literalura.service;

public interface IConvertData {
    <T> T obtainData(String json, Class<T> classType);
}
