package com.bancolombia.aplicacionbancaria.config;

public class Exceptions {

    public static class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String message) {
            super(message);
        }
    }

}
