package com.zhaohai.rocketmq.network.protocol.http.dao;

import com.zhaohai.rocketmq.network.protocol.http.entity.MessageData;

import java.util.List;

public interface MessageDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_data
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_data
     *
     * @mbggenerated
     */
    int insert(MessageData record);

    /**
     *
     * @param messageList
     * @return
     */
    int insertBatch(List<MessageData> messageList);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_data
     *
     * @mbggenerated
     */
    int insertSelective(MessageData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_data
     *
     * @mbggenerated
     */
    MessageData selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_data
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MessageData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_data
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MessageData record);
}