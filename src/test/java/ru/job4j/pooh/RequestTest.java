package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RequestTest {
    @Test
    void whenQueueModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls
                + "temperature=18" + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType(), is("POST"));
        assertThat(request.getPoohMode(), is("queue"));
        assertThat(request.getSourceName(), is("weather"));
        assertThat(request.getParam(), is("temperature=18"));
    }

    @Test
    public void whenQueueModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType(), is("GET"));
        assertThat(request.getPoohMode(), is("queue"));
        assertThat(request.getSourceName(), is("weather"));
        assertThat(request.getParam(), is(""));
    }

    @Test
    void whenTopicModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls
                + "temperature=18" + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType(), is("POST"));
        assertThat(request.getPoohMode(), is("topic"));
        assertThat(request.getSourceName(), is("weather"));
        assertThat(request.getParam(), is("temperature=18"));
    }

    @Test
    void whenTopicModePostMethodWithoutParameters() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType(), is("POST"));
        assertThat(request.getPoohMode(), is("topic"));
        assertThat(request.getSourceName(), is("weather"));
        assertThat(request.getParam(), is(""));
    }

    @Test
    void whenQueueModePostMethodWithoutParameters() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType(), is("POST"));
        assertThat(request.getPoohMode(), is("queue"));
        assertThat(request.getSourceName(), is("weather"));
        assertThat(request.getParam(), is(""));
    }

    @Test
    void whenTopicModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType(), is("GET"));
        assertThat(request.getPoohMode(), is("topic"));
        assertThat(request.getSourceName(), is("weather"));
        assertThat(request.getParam(), is("client407"));
    }
}
