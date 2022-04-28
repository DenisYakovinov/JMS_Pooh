package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

public class TopicService implements Service {
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> sourceNamesToQueuesMaps
            = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        if ("POST".equals(request.getHttpRequestType())) {
            AtomicReference<Response> response = new AtomicReference<>(new Response("", "204"));
            sourceNamesToQueuesMaps.computeIfPresent(request.getSourceName(), (k, queue) -> {
                queue.values().forEach(v -> v.offer(request.getParam()));
                response.set(new Response("", "201"));
                return queue;
            });
            return response.get();
        }
        if ("GET".equals(request.getHttpRequestType())) {
            sourceNamesToQueuesMaps.putIfAbsent(request.getSourceName(), new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscribersNamesToQueues
                    = sourceNamesToQueuesMaps.get(request.getSourceName());
            subscribersNamesToQueues.putIfAbsent(request.getParam(), new ConcurrentLinkedQueue<>());
            var message = subscribersNamesToQueues.get(request.getParam()).poll();
            return message == null ? new Response("", "204") : new Response(message, "200");
        }
        return new Response("", "405");
    }
}
