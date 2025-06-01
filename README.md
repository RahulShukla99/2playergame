# ThreeSixZeroT - Java Player Communication System

## 📘 Overview

This project simulates communication between two `Player` entities in two distinct modes:

- ✅ **Same JVM Process**: Both players communicate using threads.
- ✅ **Separate JVM Processes**: Server and Client communicate over sockets, launched via `ProcessBuilder`.

---

## 🧱 Project Structure

```
src/
├── main/
│   ├── java/com/messaging/
│   │   ├── dto/              # Message DTOs and Player interfaces
│   │   ├── service/          # PlayerService interface and implementation
│   │   ├── util/             # Logger, config reader, constants
│   │   ├── thread/           # MessageThread, execution logic
│   │   ├── separatePID/      # ServerPlayer and ClientPlayer
│   │   └── runner/           # SameProcessRunner and SeparateProcessRunner
│   └── resources/
│       └── config.properties
├── test/
│   └── java/                 # Unit and integration tests
```

---

## 🚀 How to Build and Run

### 🛠 Prerequisites

- Java 17+
- Maven 3.x+

### 🔧 Build
```bash
mvn clean package
```

### ▶️ Run in Same JVM
Update `Main.java` to use:
```java
Runner runner = new SameProcessRunner();
```

Run:
```bash
mvn exec:java -Dexec.mainClass="com.messaging.Main"
```

### ▶️ Run in Separate JVMs
Update `Main.java` to use:
```java
Runner runner = new SeparateProcessRunner();
```

Then:
```bash
mvn exec:java -Dexec.mainClass="com.messaging.Main"
```

This internally launches:
- `ServerPlayer` in one process
- `ClientPlayer` in another

✅ PIDs of both processes are printed to confirm separate execution.

---

## ⚙️ Configuration

You can change default player names, ports, or limits in:
```
src/main/resources/config.properties
```

---

## 🧪 Testing

Tests are available under:
```
src/test/java/
```

- `PlayerServiceTest`: Unit test for player service logic
- `SocketCommunicationTest`: Integration test for socket-based communication

Run with:
```bash
mvn test
```

---

## 📋 Logging

Basic console logging is available via `messaging.util.Logger`.

For production, replace it with SLF4J or Java Util Logging.

---

## 📄 License

MIT (Add if required)

---

## 🧠 Author

Rahul Shukla

