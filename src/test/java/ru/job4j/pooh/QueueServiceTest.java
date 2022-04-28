package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class QueueServiceTest {
    @Test
    void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Request("POST", "queue", "weather", paramForPostMethod)
        );

        Response result = queueService.process(
                new Request("GET", "queue", "weather", null)
        );
        assertThat(result.getText(), is("temperature=18"));
    }

    @Test
    void whenGetFirstTimeThenReturnNoContentStatus() {
        QueueService queueService = new QueueService();
        Response result = queueService.process(
                new Request("GET", "queue", "weather", null)
        );
        assertThat(result.getText(), is(""));
        assertThat(result.getStatus(), is("204"));
    }

    @Test
    void whenPostThenReturnCreatedCode() {
        QueueService queueService = new QueueService();
        Response result = queueService.process(
                new Request("POST", "queue", "weather", "message")
        );
        assertThat(result.getText(), is(""));
        assertThat(result.getStatus(), is("201"));
    }

    @Test
    void whenRequestTypeNotPostOrGetThenReturnMethodNotAllowedCode() {
        QueueService queueService = new QueueService();
        Response result = queueService.process(
                new Request("PUT", "queue", "weather", "message")
        );
        assertThat(result.getText(), is(""));
        assertThat(result.getStatus(), is("405"));
    }
}
