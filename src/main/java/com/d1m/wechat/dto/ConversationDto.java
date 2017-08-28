package com.d1m.wechat.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationDto {

	/** conversation id */
	private Integer cid;

	/** mass conversation result id */
	private Integer id;

	private Byte msgType;

	private String memberPhoto;

	private String memberId;

	private String memberNickname;

	private String memberOpenId;

	private String kfPhoto;

	private String content;

	private String createdAt;

	private String current;

	private List<ImageTextDto> items;

	private Integer dir;

	private Integer isMass;

	private MassConversationResultDto result;

	private Byte status;

	private Integer materialId;

	private Byte event;

	private String sendAt;

	private String wxSendAt;

	private String auditAt;

	private String auditBy;

	private String reason;

	private String runAt;

	private String voiceUrl;

	private String videoUrl;

	private Integer csrId;

}
