package ru.job4j.pooh;

public class Request {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Request(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Request of(String content) {
        var ls = System.lineSeparator();
        var headLines = content.split(ls)[0].split(" ");
        var httpRequestType = headLines[0];
        var poohMode = headLines[1].split("/")[1];
        var sourceName = headLines[1].split("/")[2];
        String param;
        if (httpRequestType.equals("GET")) {
            var params = headLines[1].split("/");
            param = params.length > 3 ? params[params.length - 1] : "";
        } else {
            var reg = "(\\r)?" +ls + "(\\r)?" + ls;
            var endLines =  content.split(reg);
            param = endLines.length < 2 ? "": endLines[endLines.length - 1].trim();
        }
        return new Request(httpRequestType, poohMode, sourceName, param);
    }

    public String getHttpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
