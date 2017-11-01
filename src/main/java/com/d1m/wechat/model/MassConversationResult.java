package com.d1m.wechat.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "mass_conversation_result")
public class MassConversationResult {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 公众号群发助手的微信号
	 */
	@Column(name = "mp_helper")
	private String mpHelper;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 创建人
	 */
	@Column(name = "creator_id")
	private Integer creatorId;

	/**
	 * 审核时间
	 */
	@Column(name = "audit_at")
	private Date auditAt;

	/**
	 * 审核人
	 */
	@Column(name = "audit_by")
	private Integer auditBy;

	/**
	 * 审核备注
	 */
	@Column(name = "audit_reason")
	private String auditReason;

	/**
	 * 发送时间
	 */
	@Column(name = "send_at")
	private Date sendAt;

	/**
	 * 执行时间
	 */
	@Column(name = "run_at")
	private Date runAt;

	/**
	 * 任务ID
	 */
	@Column(name = "task_id")
	private Integer taskId;

	/**
	 * 微信发送回调时间
	 */
	@Column(name = "wx_send_at")
	private Date wxSendAt;

	/**
	 * 素材ID
	 */
	@Column(name = "material_id")
	private Integer materialId;

	/**
	 * 消息类型
	 */
	@Column(name = "msg_type")
	private Byte msgType;

	/**
	 * 事件类型
	 */
	private Byte event;

    /**
     * 群发总批次
     */
    @Column(name = "total_batch")
    private Integer totalBatch;

	/**
	 * 群发消息ID
	 */
	@Column(name = "msg_id")
	private String msgId;

	/**
	 * 群发消息数据ID
	 */
	@Column(name = "msg_data_id")
	private String msgDataId;

	/**
	 * 群发消息errcode
	 */
	@Column(name = "errcode")
	private String errcode;

	/**
	 * 群发消息errmsg
	 */
	@Column(name = "errmsg")
	private String errmsg;

	/**
	 * 结果
	 */
	private Byte status;

	/**
	 * tag_id下粉丝数；或者openid_list中的粉丝数
	 */
	@Column(name = "total_count")
	private Integer totalCount;

	/**
	 * 过滤后准备发送的粉丝数
	 */
	@Column(name = "filter_count")
	private Integer filterCount;

	/**
	 * 发送成功的粉丝数
	 */
	@Column(name = "send_count")
	private Integer sendCount;

	/**
	 * 发送失败的粉丝数
	 */
	@Column(name = "error_count")
	private Integer errorCount;

	/**
	 * 群发会话ID
	 */
	@Column(name = "conversation_id")
	private Integer conversationId;

	/**
	 * 公众号ID
	 */
	@Column(name = "wechat_id")
	private Integer wechatId;

	/**
	 * 条件
	 */
	private String conditions;

	/**
	 * 获取主键ID
	 *
	 * @return id - 主键ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置主键ID
	 *
	 * @param id
	 *            主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取公众号群发助手的微信号
	 *
	 * @return mp_helper - 公众号群发助手的微信号
	 */
	public String getMpHelper() {
		return mpHelper;
	}

	/**
	 * 设置公众号群发助手的微信号
	 *
	 * @param mpHelper
	 *            公众号群发助手的微信号
	 */
	public void setMpHelper(String mpHelper) {
		this.mpHelper = mpHelper == null ? null : mpHelper.trim();
	}

	/**
	 * 获取创建时间
	 *
	 * @return created_at - 创建时间
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createdAt
	 *            创建时间
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 获取创建人
	 *
	 * @return creator_id - 创建人
	 */
	public Integer getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置创建人
	 *
	 * @param creatorId
	 *            创建人
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 获取审核时间
	 *
	 * @return audit_at - 审核时间
	 */
	public Date getAuditAt() {
		return auditAt;
	}

	/**
	 * 设置审核时间
	 *
	 * @param auditAt
	 *            审核时间
	 */
	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}

	/**
	 * 获取审核人
	 *
	 * @return audit_by - 审核人
	 */
	public Integer getAuditBy() {
		return auditBy;
	}

	/**
	 * 设置审核人
	 *
	 * @param auditBy
	 *            审核人
	 */
	public void setAuditBy(Integer auditBy) {
		this.auditBy = auditBy;
	}

	/**
	 * 获取审核备注
	 *
	 * @param auditReason
	 *            审核备注
	 */
	public String getAuditReason() {
		return auditReason;
	}

	/**
	 * 设置审核备注
	 *
	 * @param auditReason
	 *            审核备注
	 */
	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	/**
	 * 获取发送时间
	 *
	 * @return send_at - 发送时间
	 */
	public Date getSendAt() {
		return sendAt;
	}

	/**
	 * 设置发送时间
	 *
	 * @param sendAt
	 *            发送时间
	 */
	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}

	/**
	 * 获取执行时间
	 *
	 * @return run_at - 执行时间
	 */
	public Date getRunAt() {
		return runAt;
	}

	/**
	 * 设置执行时间
	 *
	 * @param run_at
	 *            执行时间
	 */
	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}

	/**
	 * 获取任务ID
	 *
	 * @param taskId
	 *            任务ID
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * 设置任务ID
	 *
	 * @param taskId
	 *            任务ID
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * 获取微信发送回调时间
	 *
	 * @return wx_send_at - 微信发送回调时间
	 */
	public Date getWxSendAt() {
		return wxSendAt;
	}

	/**
	 * 设置微信发送回调时间
	 *
	 * @param wxSendAt
	 *            微信发送回调时间
	 */
	public void setWxSendAt(Date wxSendAt) {
		this.wxSendAt = wxSendAt;
	}

	/**
	 * 获取素材ID
	 *
	 * @return material_id - 素材ID
	 */
	public Integer getMaterialId() {
		return materialId;
	}

	/**
	 * 设置素材ID
	 *
	 * @param materialId
	 *            素材ID
	 */
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	/**
	 * 获取消息类型
	 *
	 * @return msg_type - 消息类型
	 */
	public Byte getMsgType() {
		return msgType;
	}

	/**
	 * 设置消息类型
	 *
	 * @param msgType
	 *            消息类型
	 */
	public void setMsgType(Byte msgType) {
		this.msgType = msgType;
	}

	/**
	 * 获取事件类型
	 *
	 * @return event - 事件类型
	 */
	public Byte getEvent() {
		return event;
	}

	/**
	 * 设置事件类型
	 *
	 * @param event
	 *            事件类型
	 */
	public void setEvent(Byte event) {
		this.event = event;
	}

    /**
     * 获取群发总批次
     *
     * @return total_batch - 群发总批次
     */
    public Integer getTotalBatch() {
        return totalBatch;
    }

    /**
     * 设置群发总批次
     *
     * @param totalBatch 群发总批次
     */
    public void setTotalBatch(Integer totalBatch) {
        this.totalBatch = totalBatch;
    }

	/**
	 * 获取群发消息ID
	 *
	 * @return msg_id - 群发消息ID
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * 设置群发消息ID
	 *
	 * @param msgId
	 *            群发消息ID
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId == null ? null : msgId.trim();
	}

	/**
	 * 获取群发消息数据ID
	 *
	 * @return msg_data_id - 群发消息数据ID
	 */
	public String getMsgDataId() {
		return msgDataId;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * 设置群发消息数据ID
	 *
	 * @param msgDataId 群发消息数据ID
	 */
	public void setMsgDataId(String msgDataId) {
		this.msgDataId = msgDataId == null ? null : msgDataId.trim();
	}

	/**
	 * 获取结果
	 *
	 * @return status - 结果
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置结果
	 *
	 * @param status
	 *            结果
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取tag_id下粉丝数；或者openid_list中的粉丝数
	 *
	 * @return total_count - tag_id下粉丝数；或者openid_list中的粉丝数
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置tag_id下粉丝数；或者openid_list中的粉丝数
	 *
	 * @param totalCount
	 *            tag_id下粉丝数；或者openid_list中的粉丝数
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取过滤后准备发送的粉丝数
	 *
	 * @return filter_count - 过滤后准备发送的粉丝数
	 */
	public Integer getFilterCount() {
		return filterCount;
	}

	/**
	 * 设置过滤后准备发送的粉丝数
	 *
	 * @param filterCount
	 *            过滤后准备发送的粉丝数
	 */
	public void setFilterCount(Integer filterCount) {
		this.filterCount = filterCount;
	}

	/**
	 * 获取发送成功的粉丝数
	 *
	 * @return send_count - 发送成功的粉丝数
	 */
	public Integer getSendCount() {
		return sendCount;
	}

	/**
	 * 设置发送成功的粉丝数
	 *
	 * @param sendCount
	 *            发送成功的粉丝数
	 */
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	/**
	 * 获取发送失败的粉丝数
	 *
	 * @return error_count - 发送失败的粉丝数
	 */
	public Integer getErrorCount() {
		return errorCount;
	}

	/**
	 * 设置发送失败的粉丝数
	 *
	 * @param errorCount
	 *            发送失败的粉丝数
	 */
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * 获取群发会话ID
	 *
	 * @return conversation_id - 群发会话ID
	 */
	public Integer getConversationId() {
		return conversationId;
	}

	/**
	 * 设置群发会话ID
	 *
	 * @param conversationId
	 *            群发会话ID
	 */
	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

	/**
	 * 获取公众号ID
	 *
	 * @return wechat_id - 公众号ID
	 */
	public Integer getWechatId() {
		return wechatId;
	}

	/**
	 * 设置公众号ID
	 *
	 * @param wechatId
	 *            公众号ID
	 */
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	/**
	 * 获取条件
	 *
	 * @return conditions - 条件
	 */
	public String getConditions() {
		return conditions;
	}

	/**
	 * 设置条件
	 *
	 * @param conditions
	 *            条件
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions == null ? null : conditions.trim();
	}
}