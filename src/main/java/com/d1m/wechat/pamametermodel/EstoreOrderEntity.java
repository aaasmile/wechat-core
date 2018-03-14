package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EstoreOrderEntity {
    private Long id;
    private String orderNo;
    private Long memberId;
    private Integer totalPoint;
    private BigDecimal totalAmount;
    private Long productAmount;
    private Long deliveryFee;
    private Long discount;
    private Long paymentId;
    private Byte payStatus;
    private Byte status;
    private String remark;
    private Byte deliveryType;
    private String deliveryExt;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryOtherPhone;
    private String deliveryProvince;
    private String deliveryCity;
    private String deliveryDistrict;
    private String deliveryAddress;
    private String expressNo;
    private Byte needInvoice;
    private Byte invoiceType;
    private String invoiceTitle;
    private String invoiceTaxNo;
    private String invoiceContent;
    private Byte invoiceDeliveryType;
    private String invoiceName;
    private String invoicePhone;
    private String invoiceProvince;
    private String invoiceCity;
    private String invoiceDistrict;
    private String invoiceAddress;
    private Byte needGift;
    private String giftContent;
    private String createAt;
    private String updateAt;
    private Long wechatId;
    private String payType;
    private String payTime;
//    private EstoreOrderPayEntity estoreOrderPayEntity;
    private List<EstoreOrderProductEntity> listOrderProduct;
    private String openId;
}
