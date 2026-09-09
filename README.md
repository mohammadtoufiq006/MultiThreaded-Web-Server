# Java Concurrent Web Server

A Java TCP socket-server project that compares three approaches to handling multiple client connections:

1. A single-threaded server
2. A thread-per-client server
3. A fixed-size thread-pool server

The project is designed to demonstrate how server concurrency affects responsiveness, resource usage, and scalability.

## Concepts demonstrated

- TCP networking with `ServerSocket` and `Socket`
- Client-server communication using `BufferedReader` and `PrintWriter`
- Single-threaded request handling
- One-thread-per-client concurrency
- Controlled concurrency using `ExecutorService` and a fixed thread pool
- Automatic resource cleanup with try-with-resources

## Project structure

```text
SingleThreaded/
  Server.java        # Handles one client at a time
  Client.java        # Connects one client to the server

MultiThreaded/
  Server.java        # Creates a new thread for each client
  Client.java        # Starts 100 concurrent clients

ThreadPool/
  Server.java        # Handles clients with a fixed pool of 10 worker threads
  Client.java        # Starts 100 concurrent clients for load testing
```

## How it works

Each server listens on TCP port `8010`. A client connects to `localhost:8010`, sends a greeting, reads the response from the server, and closes its connection.

The server accepts each connection and returns a greeting such as:

```text
Hello from server /127.0.0.1
```

## Running the thread-pool example

Open two terminals.

### Terminal 1: start the server

```powershell
cd "F:\Web Server\ThreadPool"
javac Server.java Client.java
java Server
```

### Terminal 2: start the clients

```powershell
cd "F:\Web Server\ThreadPool"
java Client
```

`Client.java` creates 100 client threads. The server uses `Executors.newFixedThreadPool(10)`, so it handles at most 10 client tasks concurrently and queues additional work until a worker becomes available.

## Comparing the approaches

| Approach | How it handles clients | Strength | Limitation |
| --- | --- | --- | --- |
| Single-threaded | The main thread serves one client before accepting the next. | Simple and easy to understand. | A slow client blocks later clients. |
| Thread per client | A new thread is created for every connection. | Handles many clients concurrently. | Unbounded thread creation can exhaust system resources. |
| Fixed thread pool | Connections are submitted to a reusable set of 10 worker threads. | Controls resource use while supporting concurrent work. | Work can wait in a queue when all workers are busy. |

## Note about server timeout

The multi-threaded and thread-pool servers currently set a 70-second `ServerSocket` timeout. When there are no new connections during that interval, Java raises `SocketTimeoutException` and the server exits. This is expected behavior for the current demonstration setup.

## Requirements

- Java Development Kit (JDK) 8 or later
- PowerShell, Command Prompt, or another terminal

## Future improvements

- Add graceful shutdown and friendlier timeout handling
- Make the port, pool size, and client count configurable
- Add logging and automated tests
- Define a richer request/response protocol instead of a fixed greeting
