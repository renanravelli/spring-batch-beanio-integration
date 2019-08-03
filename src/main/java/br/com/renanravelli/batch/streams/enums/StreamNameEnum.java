package br.com.renanravelli.batch.streams.enums;

public enum StreamNameEnum {
    USER_FIXED_LENGTH("user-with-fixed-length", ".txt"),
    USER_DELIMITED("user-with-delimited", ".txt"),
    USER_CSV("user-with-csv", ".csv");

    private String stream;
    private String extesion;

    StreamNameEnum(String stream, String extesion) {
        this.stream = stream;
        this.extesion = extesion;
    }

    public String getStream() {
        return stream;
    }

    public String getExtesion() {
        return extesion;
    }
}
