package com.d1m.wechat.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("会话DTO")
public class ConversationDto {
	
	/** conversation id */
	@ApiModelProperty("群发会话ID")
	private Integer cid;

	/** mass conversation result id */
	@ApiModelProperty("群发会话结果ID")
	private Integer id;

	@ApiModelProperty("消息类型")
	private Byte msgType;
	
	@ApiModelProperty("会员头像")
	private String memberPhoto;
	
	@ApiModelProperty("会员ID")
	private String memberId;
	
	@ApiModelProperty("昵称")
	private String memberNickname;
	
	@ApiModelProperty("openId")
	private String memberOpenId;
	
	@ApiModelProperty("客服头像")
	private String kfPhoto;
	
	@ApiModelProperty("文本")
	private String content;

	@ApiModelProperty("会话时间")
	private String createdAt;
	
	@ApiModelProperty("当前时间")
	private String current;

	private List<ImageTextDto> items;
	
	@ApiModelProperty("会话方向(0:进,1:出)")
	private Integer dir;
	
	@ApiModelProperty("是否是群发会话")
	private Integer isMass;

	private MassConversationResultDto result;
	
	@ApiModelProperty("状态(0:未回复,1:已回复)")
	private Byte status;
	
	@ApiModelProperty("素材ID")
	private Integer materialId;
	
	@ApiModelProperty("事件类型")
	private Byte event;
	
	@ApiModelProperty("发送时间")
	private String sendAt;
	
	@ApiModelProperty("微信发送回调时间")
	private String wxSendAt;
	
	@ApiModelProperty("审核时间")
	private String auditAt;
	
	@ApiModelProperty("审核人")
	private String auditBy;

	@ApiModelProperty("审核备注")
	private String reason;
	
	@ApiModelProperty("执行时间")
	private String runAt;
	
	@ApiModelProperty("语音url")
	private String voiceUrl;
	
	@ApiModelProperty("视频url")
	private String videoUrl;
	
	@ApiModelProperty("群发会话结果ID")
	private Integer csrId;

}
