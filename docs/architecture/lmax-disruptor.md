# LMAX Disruptor Engineering in Veridian

## High-Throughput Inter-Thread Communication

Traditional Java concurrency utilizes blocking queues (such as `ArrayBlockingQueue` or `LinkedBlockingQueue`) protected by reentrant locks or synchronized blocks. In ultra-low-latency financial contexts (sub-millisecond execution), mutex locks induce:
- Costly operating system context switches (~1-5 µs each).
- CPU core pipeline stalls.
- Cache contention between processor L1/L2 caches due to cache invalidation bus traffic.

Veridian replaces thread synchronization with the **LMAX Disruptor pattern**: a lock-free, zero-allocation circular buffer operated by single-writer semantics.

---

## RingBuffer Mechanics

```
   Slot 0       Slot 1       Slot 2       Slot 3
 [Command] -> [Command] -> [Command] -> [Command] ... (Size = 2^20)
       ^                         ^
  Consumer Cursor          Producer Cursor
```

### Key Optimizations:
1. **Power of Two Sizing**: Ring buffer capacity is strictly $2^N$ (default $2^{20} = 1,048,576$). Index calculation translates into a single binary bitwise AND operation:
   $$\text{index} = \text{sequence} \ \& \ (\text{capacity} - 1)$$
2. **False Sharing Prevention**: Cache-line padding (64 bytes / 7 long words) is applied around atomic sequence cursors to ensure producer and consumer counters never occupy the same CPU cache line.
3. **Pre-allocated Event Objects**: All `OrderCommandEvent` instances are instantiated once at JVM startup. During trading execution, worker threads mutate existing slot objects without triggering heap allocation.
4. **BusySpinWaitStrategy**: In production, worker threads spin on processor hyperthreads without yielding to the OS scheduler, avoiding latency jitter.
