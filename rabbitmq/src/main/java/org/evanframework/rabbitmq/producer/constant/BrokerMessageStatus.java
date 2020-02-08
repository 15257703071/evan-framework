package org.evanframework.rabbitmq.producer.constant;

/**
 * 	$BrokerMessageStatus
 * 	消息的发送状态
 * @author ruanchaobo
 *
 */
public enum BrokerMessageStatus {

	/**
	 * 发送中
	 */
	SENDING("0"),

	/**
	 * 成功
	 */
	SEND_OK("1"),

	/**
	 * 失败
	 */
	SEND_FAIL("2"),

	/**
	 * 失败（待定）
	 */
	SEND_FAIL_A_MOMENT("3");
	
	private String code;
	
	private BrokerMessageStatus(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}
