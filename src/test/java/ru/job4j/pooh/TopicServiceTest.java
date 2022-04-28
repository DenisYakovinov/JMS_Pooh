package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TopicServiceTest {

    @Test
    void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );

        topicService.process(
                new Request("POST", "topic", "weather", paramForPublisher)
        );

        Response result1 = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );

        Response result2 = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.getText(), is("temperature=18"));
        assertThat(result2.getText(), is(""));
    }

    @Test
    void whenRequestTypeNotPostOrGetThenReturnMethodNotAllowedCode() {
        TopicService topicService = new TopicService();
        Response result = topicService.process(
                new Request("DELETE", "topic", "weather", "message")
        );
        assertThat(result.getText(), is(""));
        assertThat(result.getStatus(), is("405"));
    }

    @Test
    void whenGetButPersonalSubscriberQueueIsEmpty() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Request("POST", "topic", "weather", paramForPublisher)
        );
        topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        Response result = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        assertThat(result.getText(), is(""));
        assertThat(result.getStatus(), is("204"));
    }

    @Test
    void whenPostButTopicDoesNotExistThenReturnNoContentStatus() {
        TopicService topicService = new TopicService();
        Response result = topicService.process(
                new Request("POST", "topic", "weather", "message")
        );
        assertThat(result.getText(), is(""));
        assertThat(result.getStatus(), is("204"));
    }
}
