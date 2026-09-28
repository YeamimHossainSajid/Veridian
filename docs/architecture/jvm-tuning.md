# JVM Optimization and Garbage Collection Tuning Guide

## Production JVM Flags for Veridian HFT Engine

Financial trading systems cannot tolerate Garbage Collection (GC) pauses exceeding 5ms. Veridian standardizes on **OpenJDK 17 / 21 with ZGC (Generational ZGC)** to achieve consistent sub-millisecond pauses.

```bash
java \
  -server \
  -XX:+UseZGC \
  -XX:+AlwaysPreTouch \
  -XX:MaxGCPauseMillis=5 \
  -XX:MaxDirectMemorySize=4g \
  -XX:+UseTransparentHugePages \
  -XX:+UnlockDiagnosticVMOptions \
  -XX:GuaranteedSafepointInterval=0 \
  -XX:+UseNUMA \
  -Xms8g -Xmx8g \
  -jar matching-engine.jar
```

---

## Flag Explanations

| Flag | Rationale |
|---|---|
| `-XX:+UseZGC` | Concurrent low-latency garbage collector with P99 pauses < 1ms regardless of heap size. |
| `-XX:+AlwaysPreTouch` | Pre-allocates and zeroes out physical RAM pages upon startup, preventing page faults on the hot execution path. |
| `-Xms8g -Xmx8g` | Eliminates heap resizing and OS memory remapping overhead during runtime spikes. |
| `-XX:MaxGCPauseMillis=5` | Signals target pause constraint to the ZGC heuristics thread. |
| `-XX:+UseTransparentHugePages` | Uses 2MB pages to reduce TLB (Translation Lookaside Buffer) misses. |
| `-XX:GuaranteedSafepointInterval=0` | Prevents the JVM from forcing periodic safepoints when idle. |
| `-XX:+UseNUMA` | Memory allocations occur local to the executing CPU socket to minimize bus travel latency. |

---

## Off-Heap Memory & Direct ByteBuffers

Critical order book indexes and market tick histories leverage off-heap direct memory buffers via `java.nio.ByteBuffer.allocateDirect()` to bypass JVM garbage collector tracing entirely.
