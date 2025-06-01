# ThreeSixZeroT_F - Player Messaging System

This project demonstrates inter-player messaging using Java. Two players exchange messages either:
1. **In the same process** (multi-threaded).
2. **In separate processes** (via TCP sockets).

## Features

- Basic message DTO and player implementation
- Configurable log level and port via `config.properties`
- Supports both same-process and inter-process communication
- Graceful termination after a predefined message count
- Centralized logger with support for TRACE, DEBUG, INFO, WARN, and ERROR levels
- Modular design with clear separation of DTOs, services, and utilities

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── com.messaging/
│   │   │   ├── dto/         # Interfaces and Message DTOs
│   │   │   ├── service/     # Service interfaces and implementations
│   │   │   ├── separatePID/ # Client and Server for separate JVM communication
│   │   │   ├── thread/      # Thread runner for same-process mode
│   │   │   ├── util/        # Utility classes: Logger, ConfigReader
│   │   │   └── exception/   # Centralized exception handling
│   └── resources/
│       └── config.properties
├── test/
    ├── java/
        └── integration/     # Integration tests for communication
        └── service/         # Unit tests for services
```

## How to Run

### Same Process (Multi-threaded)

#### Mac/Linux
```bash
./start_same_process.sh
```

#### Windows
```cmd
start_same_process.cmd
```

### Separate Processes

Start the server and client in two different terminals:

#### Server
```bash
mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ServerPlayer
```

#### Client
```bash
mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ClientPlayer
```

## Configuration

Edit the `src/main/resources/config.properties` file to adjust:

- `server.port` — TCP port for server (default: 8080)
- `log.level` — Logging level (e.g., INFO, DEBUG, ERROR)

## Requirements

- Java 17+
- Maven 3.6+

## Author
Rahul Shukla

---

Licensed under MIT.
