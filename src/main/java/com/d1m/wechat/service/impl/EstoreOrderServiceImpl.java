
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.pamametermodel.*;
import com.d1m.wechat.service.IEstoreOrderService;
import com.d1m.wechat.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

@Service
public class EstoreOrderServiceImpl extends BaseService<EstoreOrder> implements IEstoreOrderService {
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
    public Page<EstoreOrderEntity> selectOrderList(Integer wechatId, EstoreOrderSearch estoreOrderSearch, boolean queryCount) {
        if(estoreOrderSearch.pagable()){
            PageHelper.startPage(estoreOrderSearch.getPageNum(), estoreOrderSearch.getPageSize(), queryCount);
        }
        Page<EstoreOrderListResult> listEstoreOrderListResult = estoreOrderMapper.selectOrderList(wechatId, estoreOrderSearch);
        Page<EstoreOrderEntity> listEstoreOrderEntity = new Page();
        listEstoreOrderEntity.setTotal(listEstoreOrderListResult.getTotal());
        EstoreOrderEntity estoreOrderEntity;
        for (EstoreOrderListResult result : listEstoreOrderListResult) {
            Long orderId = result.getId();
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
            estoreOrderEntity.setOpenId(result.getOpenId());
            EstoreOrderPay estoreOrderPay = new EstoreOrderPay();
            estoreOrderPay.setOrderId(orderId);
            estoreOrderPay.setWechatId(wechatId.longValue());
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
            List<EstoreOrderProduct> listOrderProduct = result.getListOrderProduct();
            List<EstoreOrderProductEntity> listEstoreOrderProductEntity = new ArrayList<>(listOrderProduct.size());
            EstoreOrderProductEntity estoreOrderProductEntity;
            for (EstoreOrderProduct estoreOrderProduct : listOrderProduct) {
                estoreOrderProductEntity = new EstoreOrderProductEntity();
                estoreOrderProductEntity.setProductId(estoreOrderProduct.getProductId());
                estoreOrderProductEntity.setProductSpecId(estoreOrderProduct.getProductSpecId());
                estoreOrderProductEntity.setPrice(estoreOrderProduct.getPrice());
                estoreOrderProductEntity.setQuantity(estoreOrderProduct.getQuantity());
                EstoreProductSearch estoreProductSearch = new EstoreProductSearch();
                estoreProductSearch.setProductId(estoreOrderProduct.getProductId());
                estoreProductSearch.setSpecId(estoreOrderProduct.getProductSpecId());
                estoreProductSearch.setWechatId(estoreOrderProduct.getWechatId());
                List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(estoreProductSearch);
                if (listEstoreProductListResult != null && listEstoreProductListResult.size() > 0) {
                    EstoreProductListResult productResult = listEstoreProductListResult.get(0);
                    EstoreProductSpecListResult productSpec = productResult.getListProductSpec().get(0);
                    estoreOrderProductEntity.setProductCode(productResult.getCode());
                    estoreOrderProductEntity.setProductName(productResult.getName());
                    estoreOrderProductEntity.setMarketPrice(productSpec.getMarketPrice());
                    estoreOrderProductEntity.setSpSpecType(productSpec.getSpecType());
                    estoreOrderProductEntity.setSpSpecValue(productSpec.getSpecValue());
                    estoreOrderProductEntity.setExtAttr(productResult.getExtAttr());
                    estoreOrderProductEntity.setSku(productSpec.getSku());
                    estoreOrderProductEntity.setWeight(productSpec.getWeight());
                    estoreOrderProductEntity.setVolume(productSpec.getVolume());
                }
                listEstoreOrderProductEntity.add(estoreOrderProductEntity);
            }
            estoreOrderEntity.setListOrderProduct(listEstoreOrderProductEntity);
            listEstoreOrderEntity.add(estoreOrderEntity);
        }

        return listEstoreOrderEntity;
    }

    @Override
    public void addEstoreOrder(EstoreOrderEntity orderEntity) {
        String rnd = String.valueOf(100000 + new Random().nextInt(899999));
        String orderNo = DateUtil.getCurrentyyyyMMddHHmmss() + rnd;
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
    public EstoreOrderEntity getEstoreOrder(Long orderId, Integer wechatId) {
        EstoreOrderSearch estoreOrderSearch = new EstoreOrderSearch();
        estoreOrderSearch.setOrderId(orderId);
        List<EstoreOrderListResult> listEstoreOrder = estoreOrderMapper.selectOrderList(wechatId, estoreOrderSearch);
        EstoreOrderEntity estoreOrderEntity = new EstoreOrderEntity();
        EstoreOrderListResult estoreOrder;
        if (listEstoreOrder != null && listEstoreOrder.size() > 0) {
            estoreOrder = listEstoreOrder.get(0);
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
            estoreOrderEntity.setPayType(estoreOrder.getPayCode());
            estoreOrderEntity.setPaymentId(estoreOrder.getPaymentId());
            estoreOrderEntity.setPayTime(estoreOrder.getPayTime());

            List<EstoreOrderProduct> listOrderProduct = estoreOrder.getListOrderProduct();
            if (listOrderProduct != null ) {
                List<EstoreOrderProductEntity> listEstoreOrderProductEntity = new ArrayList<>(listOrderProduct.size());
                EstoreOrderProductEntity estoreOrderProductEntity;
                for (EstoreOrderProduct estoreOrderProduct : listOrderProduct) {
                    estoreOrderProductEntity = new EstoreOrderProductEntity();
                    estoreOrderProductEntity.setProductId(estoreOrderProduct.getProductId());
                    estoreOrderProductEntity.setProductSpecId(estoreOrderProduct.getProductSpecId());
                    estoreOrderProductEntity.setPrice(estoreOrderProduct.getPrice());
                    estoreOrderProductEntity.setQuantity(estoreOrderProduct.getQuantity());
                    EstoreProductSearch estoreProductSearch = new EstoreProductSearch();
                    estoreProductSearch.setProductId(estoreOrderProduct.getProductId());
                    estoreProductSearch.setSpecId(estoreOrderProduct.getProductSpecId());
                    estoreProductSearch.setWechatId(estoreOrderProduct.getWechatId());
                    List<EstoreProductListResult> listEstoreProductListResult = estoreProductMapper.selectProductList(estoreProductSearch);
                    if (listEstoreProductListResult != null && listEstoreProductListResult.size() > 0) {
                        EstoreProductListResult productResult = listEstoreProductListResult.get(0);
                        EstoreProductSpecListResult productSpec = productResult.getListProductSpec().get(0);
                        estoreOrderProductEntity.setProductName(productResult.getName());
                        estoreOrderProductEntity.setSpSpecType(productSpec.getSpecType());
                        estoreOrderProductEntity.setSpSpecValue(productSpec.getSpecValue());
                        estoreOrderProductEntity.setExtAttr(productResult.getExtAttr());
                        estoreOrderProductEntity.setSku(productSpec.getSku());
                        estoreOrderProductEntity.setWeight(productSpec.getWeight());
                        estoreOrderProductEntity.setVolume(productSpec.getVolume());
                    }
                    listEstoreOrderProductEntity.add(estoreOrderProductEntity);
                }
                estoreOrderEntity.setListOrderProduct(listEstoreOrderProductEntity);
            }
        }
        return estoreOrderEntity;
    }

    @Override
    public void updateTrackNo(Long orderId, String trackNo) {
        EstoreOrder estoreOrder = new EstoreOrder();
        estoreOrder.setId(orderId);
        estoreOrder.setExpressNo(trackNo);
        estoreOrder.setStatus((byte)2);
        estoreOrderMapper.updateByPrimaryKeySelective(estoreOrder);
    }

    @Override
    public Mapper<EstoreOrder> getGenericMapper() {
        return estoreOrderMapper;
    }
}
