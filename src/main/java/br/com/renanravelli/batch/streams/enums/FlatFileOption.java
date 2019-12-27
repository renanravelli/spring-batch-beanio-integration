package br.com.renanravelli.batch.streams.enums;

public enum FlatFileOption {
    WRITER(1),

    READER(2);

    private Integer cod;

    FlatFileOption(Integer cod) {
        this.cod = cod;
    }

    public Integer getCod() {
        return cod;
    }

    public static Boolean isWriter(FlatFileOption optionsEnum) {
        return optionsEnum.equals(WRITER);
    }

    public static Boolean isReader(FlatFileOption optionsEnum) {
        return optionsEnum.equals(READER);
    }
}
