# job4j_pooh

[![Java CI with Maven](https://github.com/DenisYakovinov/job4j_pooh/actions/workflows/maven.yml/badge.svg)](https://github.com/DenisYakovinov/job4j_pooh/actions/workflows/maven.yml)

<ul>
    <li>We need to make an analogue of the asynchronous RabbitMQ queue.</li>
    <li>The application launches Socket and waits for clients.</li>
    <li>Clients can be of two types: senders (publisher), recipients (subscriber).</li>
    <li>HTTP will be used as the protocol. </li>
    <li>There are two modes: queue, topic.</li>
    <li>cURL is used as the client.</li>
    <li>There should be no synchronization in the code. Everything needs to be done on Executors and concurrent collections.</li>
</ul>

<h2>Technologies</h2>
<ul>
    <li>Java Concurrency (java.util.concurrent)</li>
    <li>Sockets</li>
    <li>Java IO</li>
    <li>Junit</li>
</ul>

<h2>Queue mode</h2>
<p>
    The sender sends a message indicating the queue.<br>
    The recipient reads the first message and deletes it from the queue.<br>
    If several recipients arrive, they read from the same queue.<br>
    A unique message can be read by only one recipient.
    
    Example of requests:
    The POST request should add items to the weather queue.
    curl -X POST -d "temperature=18" http://localhost:9000/queue/weather
    queue indicates the "queue" mode.
    weather indicates the name of the queue.
    The GET request should get items from the weather queue.
    curl -X GET http://localhost:9000/queue/weather
    Answer: temperature=18
</p>
<h2>Topic mode</h2>
<p>
  The sender sends a request to add data indicating the topic (weather) and the parameter value (temperature=18).<br>
  The message is placed at the end of each individual recipient queue.<br>
  If the topic is not in the service, then the data is ignored.<br>
  The recipient sends a request for data with an indication of the topic.<br>
  If there is no topic, then a new one is created.<br>
  And if the topic is present, then the message is taken from the beginning of the recipient's <br>
  individual queue and deleted.
    
   When the recipient receives data from the topic for the first time, an individual empty queue is created for him.<br>
   All subsequent messages from senders with data for this topic are placed in this queue too.<br>
    
    Example of requests:
    Sender.
    POST /topic/weather -d "temperature=18"
    topic indicates the "topic" mode.
    weather indicates the name of the topic.
    Recipient.
    GET /topic/weather/1
    1 - Recipient's ID.
    Answer: temperature=18
</p>
