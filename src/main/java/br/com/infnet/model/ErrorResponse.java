package br.com.infnet.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponse {
    private final String timestamp;
    private final String path;
    private final String code;
    private final String message;

    public ErrorResponse(String timestamp, String path, String code, String message) {
        this.timestamp = timestamp;
        this.path = path;
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(String path, String code, String message) {
        String ts = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new ErrorResponse(ts, path, code, message);
    }

    public String getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}