package org.evanframework.rabbitmq.producer.entity;

import org.evanframework.rabbitmq.api.RabbitMessage;

import java.io.Serializable;
import java.util.Date;


/**
 * 	$BrokerMessage 消息记录表实体映射
 * @author ruanchaobo
 *
 */
public class BrokerMessage implements Serializable {
	
	private static final long serialVersionUID = 7447792462810110841L;

    /**
     * mq消息id
     */
	private String messageId;

    /**
     * mq消息持久化内容
     */
    private RabbitMessage message;

    /**
     * 重试次数
     */
    private Integer tryCount = 0;//

    /**
     * 消息状态
     * @see org.evanframework.rabbitmq.producer.constant.BrokerMessageStatus
     */
    private String status;

    /**
     * 下次重试时间
     */
    private Date nextRetry;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public RabbitMessage getMessage() {
		return message;
	}

	public void setMessage(RabbitMessage message) {
		this.message = message;
	}

	public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}