package com.zhaohai.rocketmq.network.protocol.http.listeners;

import com.zhaohai.rocketmq.network.protocol.http.entity.MessageData;
import com.zhaohai.rocketmq.network.protocol.http.service.impl.HBaseServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;

import static io.netty.util.CharsetUtil.UTF_8;


@Getter
@Setter
@Slf4j
public class DefaultMessageListener implements MessageListenerConcurrently {

    private String groupId;

    public DefaultMessageListener(String groupId) {
        this.groupId = groupId;
    }

    private static ApplicationContext applicationContext;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        // 通过这里存储到Hbase中
        try {
            final HBaseServiceImpl hBaseService = applicationContext.getBean(HBaseServiceImpl.class);
            list.forEach(messageExt -> {
                final Date date = new Date();
                MessageData messageData = new MessageData();
                messageData.setMessage(new String(messageExt.getBody(), UTF_8));
                messageData.setGroupId(groupId);
                messageData.setTopic(messageExt.getTopic());
                messageData.setMessagId(messageExt.getMsgId());
                messageData.setTag(messageExt.getTags());
                messageData.setKey(messageExt.getKeys());
                messageData.setCreateDate(date);
                messageData.setUpdateDate(date);
                messageData.setConsumeSiteDate(date);
                hBaseService.saveMessage(messageData);
            });
        } catch (Exception e) {
            log.error("DefaultMessageListener | consumeMessage error => ", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
