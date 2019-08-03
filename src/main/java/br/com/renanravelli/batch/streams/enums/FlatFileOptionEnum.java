package br.com.renanravelli.batch.streams.enums;

public enum FlatFileOptionEnum {
    WRITER(1),

    READER(2);

    private Integer cod;

    FlatFileOptionEnum(Integer cod) {
        this.cod = cod;
    }

    public Integer getCod() {
        return cod;
    }

    public static Boolean isWriter(FlatFileOptionEnum optionsEnum) {
        if (optionsEnum.equals(WRITER)) {
            return true;
        }
        return false;
    }

    public static Boolean isReader(FlatFileOptionEnum optionsEnum) {
        if (optionsEnum.equals(READER)) {
            return true;
        }
        return false;
    }
}
