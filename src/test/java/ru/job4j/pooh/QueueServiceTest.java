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
}
