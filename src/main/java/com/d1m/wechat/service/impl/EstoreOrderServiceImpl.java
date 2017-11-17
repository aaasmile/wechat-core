
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.pamametermodel.*;
import com.d1m.wechat.service.IEstoreOrderService;
import com.d1m.wechat.service.IEstoreProductService;
import com.d1m.wechat.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstoreOrderServiceImpl implements IEstoreOrderService {
    private static final Logger log = LoggerFactory.getLogger(EstoreOrderServiceImpl.class);
    @Autowired
    private EstoreOrderMapper estoreOrderMapper;
    @Autowired
    private EstoreOrderPayMapper estoreOrderPayMapper;
    @Autowired
    private EstorePaymentMapper estorePaymentMapper;
    @Autowired
    private EstoreOrderProductMapper estoreOrderProductMapper;
    @Autowired
    private EstoreProductMapper estoreProductMapper;
    @Autowired
    private EstoreConfigMapper estoreConfigMapper;

    @Override
    public List<EstoreOrderEntity> selectOrderList(EstoreOrderSearch estoreOrderSearch) {
        List<EstoreOrderListResult> listEstoreOrderListResult = estoreOrderMapper.selectOrderList(estoreOrderSearch);
        EstoreOrderEntity estoreOrderEntity;
        List<EstoreOrderEntity> listEstoreOrderEntity = new ArrayList<>();
        HashMap<Long, EstoreOrderEntity> mapEstoreOrderEntity = new HashMap<>();
        HashMap<Long, List<EstoreOrderProductEntity>> mapEstoreOrderProductEntity = new HashMap<>();
        for (EstoreOrderListResult result : listEstoreOrderListResult) {
            Long orderId = result.getId();
            Long wechatId = result.getWechatId();
            if (!mapEstoreOrderEntity.containsKey(orderId)) {
                estoreOrderEntity = new EstoreOrderEntity();
                estoreOrderEntity.setId(orderId);
                estoreOrderEntity.setOrderNo(result.getOrderNo());
                estoreOrderEntity.setMemberId(result.getMemberId());
                estoreOrderEntity.setTotalPoint(result.getTotalPoint());
                estoreOrderEntity.setTotalAmount(result.getTotalAmount());
                estoreOrderEntity.setProductAmount(result.getProductAmount());
                estoreOrderEntity.setDeliveryFee(result.getDeliveryFee());
                estoreOrderEntity.setDiscount(result.getDiscount());
                estoreOrderEntity.setPayStatus(result.getPayStatus());
                estoreOrderEntity.setStatus(result.getStatus());
                estoreOrderEntity.setRemark(result.getRemark());
                estoreOrderEntity.setDeliveryType(result.getDeliveryType());
                estoreOrderEntity.setDeliveryExt(result.getDeliveryExt());
                estoreOrderEntity.setDeliveryName(result.getDeliveryName());
                estoreOrderEntity.setDeliveryPhone(result.getDeliveryPhone());
                estoreOrderEntity.setDeliveryOtherPhone(result.getDeliveryOtherPhone());
                estoreOrderEntity.setDeliveryProvince(result.getDeliveryProvince());
                estoreOrderEntity.setDeliveryCity(result.getDeliveryCity());
                estoreOrderEntity.setDeliveryDistrict(result.getDeliveryDistrict());
                estoreOrderEntity.setDeliveryAddress(result.getDeliveryAddress());
                estoreOrderEntity.setExpressNo(result.getExpressNo());
                estoreOrderEntity.setNeedInvoice(result.getNeedInvoice());
                estoreOrderEntity.setInvoiceType(result.getInvoiceType());
                estoreOrderEntity.setInvoiceTitle(result.getInvoiceTitle());
                estoreOrderEntity.setInvoiceTaxNo(result.getInvoiceTaxNo());
                estoreOrderEntity.setInvoiceContent(result.getInvoiceContent());
                estoreOrderEntity.setInvoiceDeliveryType(result.getInvoiceDeliveryType());
                estoreOrderEntity.setInvoiceName(result.getInvoiceName());
                estoreOrderEntity.setInvoicePhone(result.getInvoicePhone());
                estoreOrderEntity.setInvoiceProvince(result.getInvoiceProvince());
                estoreOrderEntity.setInvoiceCity(result.getInvoiceCity());
                estoreOrderEntity.setInvoiceDistrict(result.getInvoiceDistrict());
                estoreOrderEntity.setInvoiceAddress(result.getInvoiceAddress());
                estoreOrderEntity.setNeedGift(result.getNeedGift());
                estoreOrderEntity.setGiftContent(result.getGiftContent());
                estoreOrderEntity.setCreateAt(DateUtil.formatYYYYMMDDHHMMSS(result.getCreateAt()));
                estoreOrderEntity.setUpdateAt(DateUtil.formatYYYYMMDDHHMMSS(result.getUpdateAt()));
                estoreOrderEntity.setWechatId(result.getWechatId());
                EstoreOrderPay estoreOrderPay = new EstoreOrderPay();
                estoreOrderPay.setOrderId(orderId);
                estoreOrderPay.setWechatId(wechatId);
                estoreOrderPay = estoreOrderPayMapper.selectOne(estoreOrderPay);
                if (estoreOrderPay != null && estoreOrderPay.getPaymentId() != null) {
                    EstorePayment estorePayment = estorePaymentMapper.selectByPrimaryKey(estoreOrderPay.getPaymentId());
                    estoreOrderEntity.setPayType(estorePayment.getCode());
                    estoreOrderEntity.setPaymentId(estorePayment.getId());
                    estoreOrderEntity.setPayTime(DateUtil.formatYYYYMMDDHHMMSS(estoreOrderPay.getCreateAt()));
                } else {
                    estoreOrderEntity.setPayType("");
                    estoreOrderEntity.setPayTime("");
                    estoreOrderEntity.setPaymentId(0L);
                }
                mapEstoreOrderEntity.put(orderId, estoreOrderEntity);
            }

            EstoreOrderProductEntity estoreOrderProductEntity;
            estoreOrderProductEntity = new EstoreOrderProductEntity();
            estoreOrderProductEntity.setOrderId(orderId);
            estoreOrderProductEntity.setProductId(result.getProductId());
            estoreOrderProductEntity.setProductSpecId(result.getProductSpecId());
            estoreOrderProductEntity.setPrice(result.getPrice());
            estoreOrderProductEntity.setPoint(result.getPoint());
            estoreOrderProductEntity.setQuantity(result.getQuantity());
            EstoreProductSearch estoreProductSearch = new EstoreProductSearch();
            estoreProductSearch.setPId(result.getProductId());
            estoreProductSearch.setSpecId(result.getProductSpecId());
            estoreProductSearch.setWechatId(result.getWechatId());
            List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(estoreProductSearch);
            if (listEstoreProductListResult != null && listEstoreProductListResult.size() > 0 ) {
                EstoreProductListResult productListResult = listEstoreProductListResult.get(0);
                estoreOrderProductEntity.setProductName(productListResult.getName());
                estoreOrderProductEntity.setSpSpecType(productListResult.getSpSpecType());
                estoreOrderProductEntity.setSpSpecValue(productListResult.getSpSpecValue());
                estoreOrderProductEntity.setExtAttr(productListResult.getExtAttr());
                estoreOrderProductEntity.setSku(productListResult.getSku());
                estoreOrderProductEntity.setWeight(productListResult.getWeight());
                estoreOrderProductEntity.setVolume(productListResult.getVolume());
            }

            if (!mapEstoreOrderProductEntity.containsKey(orderId)){
                List<EstoreOrderProductEntity> listOP = new ArrayList<>();
                listOP.add(estoreOrderProductEntity);
                mapEstoreOrderProductEntity.put(orderId, listOP);
            } else {
                mapEstoreOrderProductEntity.get(orderId).add(estoreOrderProductEntity);
            }
        }

        for (Map.Entry<Long, EstoreOrderEntity> entry : mapEstoreOrderEntity.entrySet()) {
            entry.getValue().setListOrderProduct(mapEstoreOrderProductEntity.get(entry.getKey()));
            listEstoreOrderEntity.add(entry.getValue());
        }
        Integer pageNum = estoreOrderSearch.getPageNum();
        Integer pageSize = estoreOrderSearch.getPageSize();
        Integer fromIdx = (pageNum-1)*pageSize;
        Integer toIdx = (listEstoreOrderEntity.size() < pageNum*pageSize) ? listEstoreOrderEntity.size() : pageNum*pageSize;
        return listEstoreOrderEntity.subList(fromIdx, toIdx);
    }

    @Override
    public void addEstoreOrder(EstoreOrderEntity orderEntity) {
        String rnd = String.valueOf(100000 + new Random().nextInt(899999));
        String orderNo = DateUtils.formatDate(new Date(), "YYYYmmddHHmmss") + rnd;
        EstoreConfig estoreConfig = new EstoreConfig();
        estoreConfig.setSegment("order");
        estoreConfig.setKey("prefix");
        estoreConfig.setWechatId(orderEntity.getWechatId());
        estoreConfig = estoreConfigMapper.selectOne(estoreConfig);
        String prefix = "";
        if (estoreConfig != null) {
            prefix = estoreConfig.getValue();
        }
        EstoreOrder estoreOrder = new EstoreOrder();
        estoreOrder.setOrderNo(prefix + orderNo);
        estoreOrder.setMemberId(orderEntity.getMemberId());
        estoreOrder.setTotalPoint(orderEntity.getTotalPoint());
        estoreOrder.setTotalAmount(orderEntity.getTotalAmount());
        estoreOrder.setProductAmount(orderEntity.getProductAmount());
        estoreOrder.setDeliveryFee(orderEntity.getDeliveryFee());
        estoreOrder.setDiscount(orderEntity.getDiscount());
        estoreOrder.setPayStatus(orderEntity.getPayStatus());
        estoreOrder.setStatus(orderEntity.getStatus());
        estoreOrder.setRemark(orderEntity.getRemark());
        estoreOrder.setDeliveryType(orderEntity.getDeliveryType());
        estoreOrder.setDeliveryExt(orderEntity.getDeliveryExt());
        estoreOrder.setDeliveryName(orderEntity.getDeliveryName());
        estoreOrder.setDeliveryPhone(orderEntity.getDeliveryPhone());
        estoreOrder.setDeliveryOtherPhone(orderEntity.getDeliveryOtherPhone());
        estoreOrder.setDeliveryProvince(orderEntity.getDeliveryProvince());
        estoreOrder.setDeliveryCity(orderEntity.getDeliveryCity());
        estoreOrder.setDeliveryDistrict(orderEntity.getDeliveryDistrict());
        estoreOrder.setDeliveryAddress(orderEntity.getDeliveryAddress());
        estoreOrder.setExpressNo(orderEntity.getExpressNo());
        estoreOrder.setNeedInvoice(orderEntity.getNeedInvoice());
        estoreOrder.setInvoiceType(orderEntity.getInvoiceType());
        estoreOrder.setInvoiceTitle(orderEntity.getInvoiceTitle());
        estoreOrder.setInvoiceTaxNo(orderEntity.getInvoiceTaxNo());
        estoreOrder.setInvoiceContent(orderEntity.getInvoiceContent());
        estoreOrder.setInvoiceDeliveryType(orderEntity.getInvoiceDeliveryType());
        estoreOrder.setInvoiceName(orderEntity.getInvoiceName());
        estoreOrder.setInvoicePhone(orderEntity.getInvoicePhone());
        estoreOrder.setInvoiceProvince(orderEntity.getInvoiceProvince());
        estoreOrder.setInvoiceCity(orderEntity.getInvoiceCity());
        estoreOrder.setInvoiceDistrict(orderEntity.getInvoiceDistrict());
        estoreOrder.setInvoiceAddress(orderEntity.getInvoiceAddress());
        estoreOrder.setNeedGift(orderEntity.getNeedGift());
        estoreOrder.setGiftContent(orderEntity.getGiftContent());
        estoreOrder.setCreateAt(DateUtil.parse(orderEntity.getCreateAt()));
        estoreOrder.setUpdateAt(DateUtil.parse(orderEntity.getUpdateAt()));
        estoreOrder.setWechatId(orderEntity.getWechatId());
        estoreOrder.setPaymentId(0L);
        estoreOrderMapper.insert(estoreOrder);
        Long orderId = estoreOrder.getId();
        EstoreOrderProduct estoreOrderProduct;
        for (EstoreOrderProductEntity entity : orderEntity.getListOrderProduct()) {
            estoreOrderProduct = new EstoreOrderProduct();
            estoreOrderProduct.setOrderId(orderId);
            estoreOrderProduct.setProductId(entity.getProductId());
            estoreOrderProduct.setProductSpecId(entity.getProductSpecId());
            estoreOrderProduct.setPrice(entity.getPrice());
            estoreOrderProduct.setPoint(entity.getPoint());
            estoreOrderProduct.setQuantity(entity.getQuantity());
            estoreOrderProduct.setWechatId(orderEntity.getWechatId());
            estoreOrderProductMapper.insert(estoreOrderProduct);
        }
    }

    @Override
    public EstoreOrderEntity getEstoreOrder(Long orderId, Long wechatId) {
        EstoreOrder estoreOrder = estoreOrderMapper.selectByPrimaryKey(orderId);
        EstoreOrderEntity estoreOrderEntity = new EstoreOrderEntity();
        estoreOrderEntity.setId(estoreOrder.getId());
        estoreOrderEntity.setOrderNo(estoreOrder.getOrderNo());
        estoreOrderEntity.setMemberId(estoreOrder.getMemberId());
        estoreOrderEntity.setTotalPoint(estoreOrder.getTotalPoint());
        estoreOrderEntity.setTotalAmount(estoreOrder.getTotalAmount());
        estoreOrderEntity.setProductAmount(estoreOrder.getProductAmount());
        estoreOrderEntity.setDeliveryFee(estoreOrder.getDeliveryFee());
        estoreOrderEntity.setDiscount(estoreOrder.getDiscount());
        estoreOrderEntity.setPayStatus(estoreOrder.getPayStatus());
        estoreOrderEntity.setStatus(estoreOrder.getStatus());
        estoreOrderEntity.setRemark(estoreOrder.getRemark());
        estoreOrderEntity.setDeliveryType(estoreOrder.getDeliveryType());
        estoreOrderEntity.setDeliveryExt(estoreOrder.getDeliveryExt());
        estoreOrderEntity.setDeliveryName(estoreOrder.getDeliveryName());
        estoreOrderEntity.setDeliveryPhone(estoreOrder.getDeliveryPhone());
        estoreOrderEntity.setDeliveryOtherPhone(estoreOrder.getDeliveryOtherPhone());
        estoreOrderEntity.setDeliveryProvince(estoreOrder.getDeliveryProvince());
        estoreOrderEntity.setDeliveryCity(estoreOrder.getDeliveryCity());
        estoreOrderEntity.setDeliveryDistrict(estoreOrder.getDeliveryDistrict());
        estoreOrderEntity.setDeliveryAddress(estoreOrder.getDeliveryAddress());
        estoreOrderEntity.setExpressNo(estoreOrder.getExpressNo());
        estoreOrderEntity.setNeedInvoice(estoreOrder.getNeedInvoice());
        estoreOrderEntity.setInvoiceType(estoreOrder.getInvoiceType());
        estoreOrderEntity.setInvoiceTitle(estoreOrder.getInvoiceTitle());
        estoreOrderEntity.setInvoiceTaxNo(estoreOrder.getInvoiceTaxNo());
        estoreOrderEntity.setInvoiceContent(estoreOrder.getInvoiceContent());
        estoreOrderEntity.setInvoiceDeliveryType(estoreOrder.getInvoiceDeliveryType());
        estoreOrderEntity.setInvoiceName(estoreOrder.getInvoiceName());
        estoreOrderEntity.setInvoicePhone(estoreOrder.getInvoicePhone());
        estoreOrderEntity.setInvoiceProvince(estoreOrder.getInvoiceProvince());
        estoreOrderEntity.setInvoiceCity(estoreOrder.getInvoiceCity());
        estoreOrderEntity.setInvoiceDistrict(estoreOrder.getInvoiceDistrict());
        estoreOrderEntity.setInvoiceAddress(estoreOrder.getInvoiceAddress());
        estoreOrderEntity.setNeedGift(estoreOrder.getNeedGift());
        estoreOrderEntity.setGiftContent(estoreOrder.getGiftContent());
        estoreOrderEntity.setCreateAt(DateUtil.formatYYYYMMDDHHMMSS(estoreOrder.getCreateAt()));
        estoreOrderEntity.setUpdateAt(DateUtil.formatYYYYMMDDHHMMSS(estoreOrder.getUpdateAt()));
        estoreOrderEntity.setWechatId(estoreOrder.getWechatId());
        EstoreOrderPay estoreOrderPay = new EstoreOrderPay();
        estoreOrderPay.setOrderId(orderId);
        estoreOrderPay.setWechatId(wechatId);
        estoreOrderPay = estoreOrderPayMapper.selectOne(estoreOrderPay);
        if (estoreOrderPay != null && estoreOrderPay.getPaymentId() != null) {
            EstorePayment estorePayment = estorePaymentMapper.selectByPrimaryKey(estoreOrderPay.getPaymentId());
            estoreOrderEntity.setPayType(estorePayment.getCode());
            estoreOrderEntity.setPaymentId(estorePayment.getId());
            estoreOrderEntity.setPayTime(DateUtil.formatYYYYMMDDHHMMSS(estoreOrderPay.getCreateAt()));
        } else {
            estoreOrderEntity.setPayType("");
            estoreOrderEntity.setPayTime("");
            estoreOrderEntity.setPaymentId(0L);
        }
        EstoreOrderProduct estoreOrderProduct = new EstoreOrderProduct();
        estoreOrderProduct.setOrderId(orderId);
        estoreOrderProduct.setWechatId(wechatId);
        EstoreOrderProductEntity estoreOrderProductEntity;
        List<EstoreOrderProduct> listEstoreOrderProduct = estoreOrderProductMapper.select(estoreOrderProduct);
        List<EstoreOrderProductEntity> listEstoreOrderProductEntity = new ArrayList<>(listEstoreOrderProduct.size());
        for (EstoreOrderProduct op : listEstoreOrderProduct) {
            estoreOrderProductEntity = new EstoreOrderProductEntity();
            estoreOrderProductEntity.setOrderId(op.getOrderId());
            estoreOrderProductEntity.setProductId(op.getProductId());
            estoreOrderProductEntity.setProductSpecId(op.getProductSpecId());
            estoreOrderProductEntity.setPrice(op.getPrice());
            estoreOrderProductEntity.setPoint(op.getPoint());
            estoreOrderProductEntity.setQuantity(op.getQuantity());
            EstoreProductSearch estoreProductSearch = new EstoreProductSearch();
            estoreProductSearch.setPId(op.getProductId());
            estoreProductSearch.setSpecId(op.getProductSpecId());
            List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(estoreProductSearch);
            if (listEstoreProductListResult != null && listEstoreProductListResult.size() > 0 ) {
                EstoreProductListResult productListResult = listEstoreProductListResult.get(0);
                estoreOrderProductEntity.setProductName(productListResult.getName());
                estoreOrderProductEntity.setExtAttr(productListResult.getExtAttr());
                estoreOrderProductEntity.setSku(productListResult.getSku());
                estoreOrderProductEntity.setSpSpecType(productListResult.getSpSpecType());
                estoreOrderProductEntity.setSpSpecValue(productListResult.getSpSpecValue());
                estoreOrderProductEntity.setWeight(productListResult.getWeight());
                estoreOrderProductEntity.setVolume(productListResult.getVolume());
            }
            listEstoreOrderProductEntity.add(estoreOrderProductEntity);
        }
        estoreOrderEntity.setListOrderProduct(listEstoreOrderProductEntity);
        return estoreOrderEntity;
    }


}
