package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EstoreOrderListResult {

    private Long id;
    private String orderNo;
    private Long memberId;
    private Integer totalPoint;
    private Long totalAmount;
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
    private Date createAt;
    private Date updateAt;
    private Long wechatId;
    private String cbStatus;
    private String cbData;
    private String payTime;
    private String payName;
    private String payCode;
//    private Long productId;
//    private Long productSpecId;
//    private Integer quantity;
//    private BigDecimal price;
//    private Integer point;
    private List<EstoreOrderProduct> listOrderProduct;
    private String openId;
}