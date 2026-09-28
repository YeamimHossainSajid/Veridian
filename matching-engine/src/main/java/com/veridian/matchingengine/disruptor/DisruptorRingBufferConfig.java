package com.veridian.matchingengine.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorRingBufferConfig {

    @Value("${veridian.engine.ring-buffer-size:1048576}")
    private int ringBufferSize;

    @Bean(destroyMethod = "shutdown")
    public Disruptor<OrderCommandEvent> orderCommandDisruptor() {
        return new Disruptor<>(
                OrderCommandEvent.FACTORY,
                ringBufferSize,
                DaemonThreadFactory.INSTANCE,
                ProducerType.MULTI,
                new BusySpinWaitStrategy()
        );
    }

    @Bean
    public RingBuffer<OrderCommandEvent> orderRingBuffer(Disruptor<OrderCommandEvent> disruptor) {
        return disruptor.getRingBuffer();
    }
}
