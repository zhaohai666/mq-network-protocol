package com.zhaohai.rocketmq.network.protocol.http.listeners;

import com.zhaohai.rocketmq.network.protocol.http.entity.MessageData;
import com.zhaohai.rocketmq.network.protocol.http.utils.HikariCPUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.ArrayList;
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

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        // ToDO通过这里存储到DB中
        List<MessageData>  messageDataList = new ArrayList<>(list.size());
        log.info("list : {}", list);
        list.forEach(messageExt -> {
            MessageData messageData = new MessageData();
            messageData.setMessage(new String(messageExt.getBody(), UTF_8));
            messageData.setGroupId(groupId);
            messageData.setTopic(messageExt.getTopic());
            messageData.setMessagId(messageExt.getMsgId());
            messageData.setTag(messageExt.getTags());
            messageData.setKey(messageExt.getKeys());
            messageDataList.add(messageData);
        });
        try {
            HikariCPUtils.excuteUpdate("", "");
        } catch (Exception e) {
            log.error("DefaultMessageListener | consumeMessage error => ", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
