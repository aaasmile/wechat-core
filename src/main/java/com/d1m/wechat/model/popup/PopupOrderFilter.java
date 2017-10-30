package com.d1m.wechat.model.popup;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
@ApiModel("微店订单参数")
public class PopupOrderFilter {
	
	@ApiModelProperty("结束时间")
    private String endDate;
	@ApiModelProperty("开始时间")
    private String startDate;
	@ApiModelProperty("收件人名字")
    private String receiverName;
	@ApiModelProperty("收件人手机")
    private String receiverPhone;
	@ApiModelProperty("支付类型")
    private Byte payType;
	@ApiModelProperty("支付状态")
    private Byte payStatus;
	@ApiModelProperty("订单ID")
    private Integer orderId;
	@ApiModelProperty("公众号ID")
    private Integer wechatId;
	@ApiModelProperty("分页大小")
    Integer pageSize = 10;
	@ApiModelProperty("当前页")
    Integer pageNum = 1;
}
