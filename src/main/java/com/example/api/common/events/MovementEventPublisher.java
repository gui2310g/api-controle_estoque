package com.example.api.common.events;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovementEventPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ChannelTopic topic = new ChannelTopic("movements");

    public void publishMovementCreated(Object event) {
        redisTemplate.convertAndSend(topic.getTopic(), event);
    }
}
