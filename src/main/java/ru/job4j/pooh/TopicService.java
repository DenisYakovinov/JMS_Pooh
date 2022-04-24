package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> sourceNamesToQueuesMaps
            = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        if ("POST".equals(request.getHttpRequestType())) {
            sourceNamesToQueuesMaps.computeIfPresent(request.getSourceName(), (k, queue) -> {
                queue.values().forEach(v -> v.offer(request.getParam()));
                return queue;
            });
            return new Response("", "201");
        }
        if ("GET".equals(request.getHttpRequestType())) {
            sourceNamesToQueuesMaps.putIfAbsent(request.getSourceName(), new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> consumersNamesToQueues
                    = sourceNamesToQueuesMaps.get(request.getSourceName());
            consumersNamesToQueues.putIfAbsent(request.getParam(), new ConcurrentLinkedQueue<>());
            var message = consumersNamesToQueues.get(request.getParam()).poll();
            return message == null ? new Response("", "204") : new Response(message, "200");
        }
        return new Response("", "405");
    }
}
