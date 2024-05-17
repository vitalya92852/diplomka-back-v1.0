package com.example.demo1222.Exception;

public class FailedToSaveFileException extends RuntimeException{
    public FailedToSaveFileException(String message){
        super("Ошибка загрузки файла");
    }
}
