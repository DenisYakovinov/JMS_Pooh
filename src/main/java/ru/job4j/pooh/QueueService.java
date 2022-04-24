package ru.job4j.pooh;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

public class QueueService implements Service {
    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> sourceNamesToQueues = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        if ("POST".equals(request.getHttpRequestType())) {
            sourceNamesToQueues.putIfAbsent(request.getSourceName(), new ConcurrentLinkedQueue<>());
            sourceNamesToQueues.get(request.getSourceName()).add(request.getParam());
            return new Response("", "201");
        }
        if ("GET".equals(request.getHttpRequestType())) {
            ConcurrentLinkedQueue<String> targetQueue = sourceNamesToQueues.get(request.getSourceName());
            if (targetQueue == null) {
                return new Response("", "204");
            }
            var message = targetQueue.poll();
            return message == null ? new Response("", "204") : new Response(message, "200");
        }
        return new Response("", "405");
    }
}
