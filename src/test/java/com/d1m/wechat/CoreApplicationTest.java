package com.d1m.wechat;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.domain.dao.MemberTagDataDao;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.dto.GiftCardOrderDto;
import com.d1m.wechat.pamametermodel.*;
import com.d1m.wechat.service.IEstoreOrderService;
import com.d1m.wechat.service.IEstoreProductService;
import com.d1m.wechat.service.IGiftCardOrderService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class CoreApplicationTest {
    private Logger log = LoggerFactory.getLogger(CoreApplicationTest.class);
    public Integer wechatId = 32;
    public Long memberId = 316L;
    public Long goodsId = 70L;

    //    @Resource
//    private IPopupGoodsService service;
//    @Resource
//    private IPopupOrderService orderService;
//    @Resource
//    private PopupGoodsMapper popupGoodsMapper;
//    @Resource
//    private PopupGoodsSkuMapper popupGoodsSkuMapper;
//    @Resource
//    private PopupOrderMapper popupOrderMapper;
//    @Resource
//    private PopupOrderGoodsRelMapper popupOrderGoodsRelMapper;
//    @Resource
//    private PopupOrderExpressMapper popupOrderExpressMapper;
//    @Resource
//    private PopupOrderDeliveryAddrMapper popupOrderDeliveryAddrMapper;
//    @Resource
//    private PopupDeliveryAddrMapper popupDeliveryAddrMapper;
//    @Resource
//    private PopupOrderInvoiceMapper popupOrderInvoiceMapper;
//    @Resource
//    private PopupOrderExtraAttrMapper popupOrderExtraAttrMapper;
    @Autowired
    IEstoreProductService estoreProductServiceImpl;
    @Autowired
    IEstoreOrderService estoreOrderServiceImpl;
    @Autowired
    IGiftCardOrderService giftCardOrderService;

    //    @Transactional
    @Test
    public void contextLoads() {
        //createOrder();
        //listOrder();
        listGiftCardOrder();
    }

    public void listOrder() {
        EstoreOrderSearch estoreOrderSearch = new EstoreOrderSearch();
        estoreOrderSearch.setPageSize(1);
        estoreOrderSearch.setPageNum(1);
//        estoreOrderSearch.setName("test name");
//        estoreOrderSearch.setOrderId(3L);
        List<EstoreOrderEntity> listEstoreOrderEntity = estoreOrderServiceImpl.selectOrderList(wechatId, estoreOrderSearch, true);
        log.info(JSONObject.toJSONString(listEstoreOrderEntity));
    }

    public void listGiftCardOrder() {
        GiftCardOrderSearch giftCardOrderSearch = new GiftCardOrderSearch();
        giftCardOrderSearch.setPageSize(1);
        giftCardOrderSearch.setPageNum(1);
//        estoreOrderSearch.setName("test name");
//        estoreOrderSearch.setOrderId(3L);
        List<GiftCardOrderDto> list = giftCardOrderService.selectOrderList(wechatId, giftCardOrderSearch, true);
        log.info(JSONObject.toJSONString(list));
    }

    public void getOrder() {
        EstoreOrderEntity estoreOrderEntity = estoreOrderServiceImpl.getEstoreOrder(3L, wechatId);
        log.info(JSONObject.toJSONString(estoreOrderEntity));
    }

    public void createOrder() {
        Date now = new Date();
        EstoreOrderEntity estoreOrderEntity = new EstoreOrderEntity();
        estoreOrderEntity.setMemberId(memberId);
        estoreOrderEntity.setTotalPoint(0);
        estoreOrderEntity.setTotalAmount(new BigDecimal(1));
        estoreOrderEntity.setProductAmount(1L);
        estoreOrderEntity.setDeliveryFee(1L);
        estoreOrderEntity.setDiscount(0L);
        estoreOrderEntity.setPayStatus((byte) 0);
        estoreOrderEntity.setStatus((byte) 0);
        estoreOrderEntity.setRemark("");
        estoreOrderEntity.setDeliveryType((byte) 1);
        estoreOrderEntity.setDeliveryExt("");
        estoreOrderEntity.setDeliveryName("小王");
        estoreOrderEntity.setDeliveryPhone("13312341234");
        estoreOrderEntity.setDeliveryOtherPhone("13388888888");
        estoreOrderEntity.setDeliveryProvince("上海");
        estoreOrderEntity.setDeliveryCity("上海");
        estoreOrderEntity.setDeliveryDistrict("");
        estoreOrderEntity.setDeliveryAddress("联创国际第一秒电商");
        estoreOrderEntity.setExpressNo("");
        estoreOrderEntity.setNeedInvoice((byte) 1);
        estoreOrderEntity.setInvoiceType((byte) 1);
        estoreOrderEntity.setInvoiceTitle("联创国际第一秒电商");
        estoreOrderEntity.setInvoiceTaxNo("AJSJS11112");
        estoreOrderEntity.setInvoiceContent("");
        estoreOrderEntity.setInvoiceDeliveryType((byte) 1);
        estoreOrderEntity.setInvoiceName("小王");
        estoreOrderEntity.setInvoicePhone("13312341234");
        estoreOrderEntity.setInvoiceProvince("上海");
        estoreOrderEntity.setInvoiceCity("上海");
        estoreOrderEntity.setInvoiceDistrict("");
        estoreOrderEntity.setInvoiceAddress("联创国际第一秒电商");
        estoreOrderEntity.setNeedGift((byte) 1);
        estoreOrderEntity.setGiftContent("this is gift");
        estoreOrderEntity.setCreateAt("2017-11-16 18:23:35");
        estoreOrderEntity.setUpdateAt("2017-11-16 18:23:35");
        estoreOrderEntity.setWechatId(wechatId.longValue());
        estoreOrderEntity.setPayType("WECHAT_PAY");
        List<EstoreOrderProductEntity> listEstoreOrderProductEntity = new ArrayList<>();
        EstoreOrderProductEntity estoreOrderProductEntity;
        for (int i = 2; i < 5; i++) {
            estoreOrderProductEntity = new EstoreOrderProductEntity();
            estoreOrderProductEntity.setProductId(i + 0L);
            estoreOrderProductEntity.setProductSpecId(i + 0L);
            estoreOrderProductEntity.setPrice(BigDecimal.ONE);
            estoreOrderProductEntity.setQuantity(1);
            listEstoreOrderProductEntity.add(estoreOrderProductEntity);
        }
        estoreOrderEntity.setListOrderProduct(listEstoreOrderProductEntity);
        estoreOrderServiceImpl.addEstoreOrder(estoreOrderEntity);
    }

    public void createProduct() {
        EstoreProductEntity estoreProductEntity = new EstoreProductEntity();
        estoreProductEntity.setCategory("1,2");
        estoreProductEntity.setDeliveryFree((byte) 1);
        estoreProductEntity.setDeliveryTplId(null);
        estoreProductEntity.setDescription("test desc");
        estoreProductEntity.setExtAttr(null);
        estoreProductEntity.setName("test name");
        estoreProductEntity.setOnSale((byte) 1);
        estoreProductEntity.setSpecType((byte) 1);
        estoreProductEntity.setSpecMeta(
                JSONObject.parseObject("{\"颜色\":\"红色,蓝色,黄色\",\"尺码\":\"39,40,41\"}"));
        estoreProductEntity.setTag("1,2");
        estoreProductEntity.setStatus((byte) 1);
        estoreProductEntity.setWechatId(wechatId.longValue());
        List<EstoreProductImageEntity> listImg = new ArrayList<>(3);
        EstoreProductImageEntity estoreProductImageEntity;
        for (int i = 1; i <= 3; i++) {
            estoreProductImageEntity = new EstoreProductImageEntity();
            estoreProductImageEntity.setMaterialId(i + 0L);
            estoreProductImageEntity.setSeq(0);
            estoreProductImageEntity.setType((byte) 0);
            listImg.add(estoreProductImageEntity);
        }
        estoreProductEntity.setListImg(listImg);

        String[] arrColor = {"红色", "蓝色", "黄色"};
        String[] arrSize = {"39", "40", "41"};
        List<EstoreProductSpecEntity> listSpec = new ArrayList<>(3);
        EstoreProductSpecEntity estoreProductSpecEntity;
        for (int i = 1; i <= 3; i++) {
            estoreProductSpecEntity = new EstoreProductSpecEntity();
            estoreProductSpecEntity.setMarketPrice(BigDecimal.ONE);
            estoreProductSpecEntity.setPrice(BigDecimal.ONE);
            estoreProductSpecEntity.setPoint(0);
            estoreProductSpecEntity.setSpecType((byte) 1);

            estoreProductSpecEntity.setSpecValue(
                    JSONObject.parseObject("{\"颜色\":\"" + arrColor[i - 1] + "\",\"尺码\":\"" + arrSize[i - 1] + "\"}"));
            estoreProductSpecEntity.setStock(10);
            estoreProductSpecEntity.setStatus((byte) 1);
            estoreProductSpecEntity.setVolume(null);
            estoreProductSpecEntity.setWeight(null);
            estoreProductSpecEntity.setSku("000" + i);
            List<EstoreProductImageEntity> listSpecImg = new ArrayList<>(3);
            EstoreProductImageEntity estoreProductSpecImageEntity;
            for (int j = 1; j <= 3; j++) {
                estoreProductSpecImageEntity = new EstoreProductImageEntity();
                estoreProductSpecImageEntity.setMaterialId(j + 0L);
                estoreProductSpecImageEntity.setSeq(0);
                estoreProductSpecImageEntity.setType((byte) 1);
                listSpecImg.add(estoreProductSpecImageEntity);
            }
            estoreProductSpecEntity.setListImg(listSpecImg);
            listSpec.add(estoreProductSpecEntity);
        }
        estoreProductEntity.setListSpec(listSpec);

        log.info(JSONObject.toJSONString(estoreProductEntity));
        estoreProductServiceImpl.updateEstoreProduct(estoreProductEntity, "add");
    }


    public void editProduct() {
        Long productId = 3L;
        EstoreProductEntity estoreProductEntity = new EstoreProductEntity();
        estoreProductEntity.setId(productId);
        estoreProductEntity.setSaleId(3L);
        estoreProductEntity.setCategory("1,2");
        estoreProductEntity.setDeliveryFree((byte) 1);
        estoreProductEntity.setDeliveryTplId(null);
        estoreProductEntity.setDescription("test desc");
        estoreProductEntity.setExtAttr(null);
        estoreProductEntity.setName("test name");
        estoreProductEntity.setOnSale((byte) 1);
        estoreProductEntity.setSpecType((byte) 1);
        estoreProductEntity.setSpecMeta(
                JSONObject.parseObject("{\"颜色\":\"红色,蓝色,黄色\",\"尺码\":\"39,40,41\"}"));
        estoreProductEntity.setTag("1,2");
        estoreProductEntity.setStatus((byte) 1);
        estoreProductEntity.setWechatId(wechatId.longValue());
        List<EstoreProductImageEntity> listImg = new ArrayList<>(3);
        EstoreProductImageEntity estoreProductImageEntity;
        for (int i = 1; i <= 3; i++) {
            estoreProductImageEntity = new EstoreProductImageEntity();
            estoreProductImageEntity.setId(i + 13L);
            estoreProductImageEntity.setMaterialId(i + 0L);
            estoreProductImageEntity.setSeq(0);
            estoreProductImageEntity.setType((byte) 0);
            listImg.add(estoreProductImageEntity);
        }
        estoreProductEntity.setListImg(listImg);

        String[] arrColor = {"红色", "蓝色", "黄色"};
        String[] arrSize = {"39", "40", "41"};
        List<EstoreProductSpecEntity> listSpec = new ArrayList<>(3);
        EstoreProductSpecEntity estoreProductSpecEntity;
        for (int i = 1; i <= 3; i++) {
            estoreProductSpecEntity = new EstoreProductSpecEntity();
            estoreProductSpecEntity.setId(i + 3L);
            estoreProductSpecEntity.setMarketPrice(BigDecimal.ONE);
            estoreProductSpecEntity.setPrice(BigDecimal.ONE);
            estoreProductSpecEntity.setPoint(0);
            estoreProductSpecEntity.setSpecType((byte) 1);

            estoreProductSpecEntity.setSpecValue(
                    JSONObject.parseObject("{\"颜色\":\"" + arrColor[i - 1] + "\",\"尺码\":\"" + arrSize[i - 1] + "\"}"));
            estoreProductSpecEntity.setStock(10);
            estoreProductSpecEntity.setStatus((byte) 1);
            estoreProductSpecEntity.setVolume(null);
            estoreProductSpecEntity.setWeight(null);
            estoreProductSpecEntity.setSku("000" + i);
            List<EstoreProductImageEntity> listSpecImg = new ArrayList<>(3);
            EstoreProductImageEntity estoreProductSpecImageEntity;
            for (int j = 1; j <= 3; j++) {
                estoreProductSpecImageEntity = new EstoreProductImageEntity();
                estoreProductSpecImageEntity.setId(j + 16L + (i - 1) * 3);
                estoreProductSpecImageEntity.setMaterialId(j + 0L);
                estoreProductSpecImageEntity.setSeq(0);
                estoreProductSpecImageEntity.setType((byte) 1);
                estoreProductSpecImageEntity.setIsDel((byte) 1);
                listSpecImg.add(estoreProductSpecImageEntity);
            }
            estoreProductSpecEntity.setListImg(listSpecImg);
            listSpec.add(estoreProductSpecEntity);
        }
        estoreProductEntity.setListSpec(listSpec);

        log.info(JSONObject.toJSONString(estoreProductEntity));
        estoreProductServiceImpl.updateEstoreProduct(estoreProductEntity, "edit");
    }

    public void getProduct() {
        Long productId = 4L;
        EstoreProductEntity estoreProductEntity = estoreProductServiceImpl.getEstoreProduct(productId, wechatId.longValue());
        log.info(JSONObject.toJSONString(estoreProductEntity));
    }

    public void listProduct() {
        EstoreProductSearch estoreProductSearch = new EstoreProductSearch();
//        estoreProductSearch.setPageSize(2);
//        estoreProductSearch.setPageNum(1);
        estoreProductSearch.setSku("0003");
        estoreProductSearch.setWechatId(wechatId.longValue());
        List<EstoreProductEntity> listEstoreProductEntity = estoreProductServiceImpl.selectProductList(estoreProductSearch);
        log.info(JSONObject.toJSONString(listEstoreProductEntity));
    }

////    @Transactional
////    @Test
//    public void createOrder() {
//        PopupOrder orderBase = new PopupOrder();
//        orderBase.setWechatId(wechatId);
//        orderBase.setMemberId(memberId);
//        orderBase.setPayType((byte) 0);
//        orderBase.setPayStatus((byte) 0);
//        orderBase.setNotifyStatus((byte) 0);
//        orderBase.setNotifyUpdateTime(new Date());
//        orderBase.setCreateTime(new Date());
//        popupOrderMapper.insert(orderBase);
//
//        List<PopupOrderGoodsRel> listOrderGoodsRel = new ArrayList<PopupOrderGoodsRel>();
//
//        PopupOrderGoodsRel orderGoodsRel = new PopupOrderGoodsRel();
//        orderGoodsRel.setOrderId(orderBase.getId());
//        orderGoodsRel.setGoodsId(goodsId.longValue());
//        orderGoodsRel.setSum(1);
//        orderGoodsRel.setPrice(0);
//        orderGoodsRel.setPoints(1L);
//        orderGoodsRel.setCreateTime(new Date());
//        orderGoodsRel.setSku("AA660");
//        popupOrderGoodsRelMapper.insert(orderGoodsRel);
//        listOrderGoodsRel.add(orderGoodsRel);
//
//        orderGoodsRel = new PopupOrderGoodsRel();
//        orderGoodsRel.setOrderId(orderBase.getId());
//        orderGoodsRel.setGoodsId(goodsId.longValue());
//        orderGoodsRel.setSum(1);
//        orderGoodsRel.setPrice(0);
//        orderGoodsRel.setPoints(1L);
//        orderGoodsRel.setCreateTime(new Date());
//        orderGoodsRel.setSku("AA661");
//        popupOrderGoodsRelMapper.insert(orderGoodsRel);
//        listOrderGoodsRel.add(orderGoodsRel);
//
//        PopupDeliveryAddr deliveryAddr = new PopupDeliveryAddr();
//        deliveryAddr.setProvince(110000);
//        deliveryAddr.setCity(110100);
//        deliveryAddr.setArea(110101);
//        deliveryAddr.setAddr("上海市杨浦区控江路联创设计谷");
//        deliveryAddr.setReceiverName("顾斌");
//        deliveryAddr.setReceiverPhone("13817006428");
//        deliveryAddr.setMemberId(memberId.longValue());
//        popupDeliveryAddrMapper.insert(deliveryAddr);
//
//        PopupOrderDeliveryAddr orderDeliveryAddr = new PopupOrderDeliveryAddr();
//        orderDeliveryAddr.setOrderId(orderBase.getId());
//        orderDeliveryAddr.setAddrId(deliveryAddr.getId());
//        orderDeliveryAddr.setDeliveryType((byte)0);
//        orderDeliveryAddr.setIsInvoice((byte)0);
//        orderDeliveryAddr.setMsmOpen((byte)0);
//        orderDeliveryAddr.setMsmPhone("");
//        popupOrderDeliveryAddrMapper.insert(orderDeliveryAddr);
//
//        PopupOrderInvoice orderInvoice = new PopupOrderInvoice();
//        orderInvoice.setOrderId(orderBase.getId());
//        orderInvoice.setCreditCode("123456789012345678");
//        orderInvoice.setTitle("第一秒电商科技");
//        orderInvoice.setProp((byte)0);
//        orderInvoice.setStatus((byte)1);
//        orderInvoice.setType((byte)0);
//        popupOrderInvoiceMapper.insert(orderInvoice);
//
//        PopupOrderExtraAttr orderExtraAttr = new PopupOrderExtraAttr();
//        orderExtraAttr.setOrderId(orderBase.getId());
//        orderExtraAttr.setGiftContent("");
//        orderExtraAttr.setRemark("");
//        popupOrderExtraAttrMapper.insert(orderExtraAttr);
//
//        PopupOrderExpress orderExpress = new PopupOrderExpress();
//        orderExpress.setCompany("顺丰快递");
//        orderExpress.setOrderId(orderBase.getId());
//        orderExpress.setStatus((byte)1);
//        orderExpress.setTrackNo("83882920100101");
//        orderExpress.setCreateDate(new Date());
//        popupOrderExpressMapper.insert(orderExpress);
//
//        PopupOrderEntity orderEntity = new PopupOrderEntity();
//        orderEntity.setOrderBase(orderBase);
//        orderEntity.setLiOrderGoods(listOrderGoodsRel);
//        orderEntity.setOrderDeliveryAddr(orderDeliveryAddr);
//        orderEntity.setOrderExpress(orderExpress);
//        orderEntity.setOrderInvoice(orderInvoice);
//        orderEntity.setOrderExtraAttr(orderExtraAttr);
//
//        JSONObject object = new JSONObject();
//        object.put("data", orderEntity);
//        log.info(object.toJSONString());
//
//    }
//
//    //    @Test
//    public void selectOrder() {
//        PopupOrderFilter model = new PopupOrderFilter();
//        model.setWechatId(wechatId);
//        model.setPageNum(1);
//        model.setPageSize(10);
//        model.setStartDate("2017-09-10");
//        model.setEndDate("2017-10-10");
//        model.setPayStatus((byte)1);
//        model.setPayType((byte)4);
//        model.setReceiverPhone("13817006428");
//        log.info(JSONObject.toJSONString(model));
//        Page<PopupOrderList> data = orderService.selectOrderList(model);
//
//        JSONObject object = new JSONObject();
//        object.put("data", data);
//        log.info(object.toJSONString());
//    }
//
////    @Test
//    public void insert() {
//
//        PopupGoodsEntity goodsEntity = new PopupGoodsEntity();
//        PopupGoods goods = new PopupGoods();
//        goods.setWechatId(wechatId);
//        goods.setName("test 2");
//        String imgurls = "{\"bg\":\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\",\"img\":[\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\",\"http://dev.wechat.d1m.cn/static/marni/product_img02_.jpg\",\"http://dev.wechat.d1m.cn/static/marni/product_img03_.jpg\"]}";
//        goods.setImgUrls(JSONObject.parseObject(imgurls));
//        goods.setCategoryId(0L);
//        goods.setCount(20);
//        goods.setDesc("");
//        goods.setGoodsNo("");
//        goods.setLimitCount(1);
//        goods.setPermit("1,2,3");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String curDateTime = sdf.format(new Date());
//        goods.setCreateTime(new Date());
////        t.setUpdateTime(curDateTime);
//        goods.setStatus((byte)1);
//        goods.setType((byte)0);
//        goods.setPrice(1);
//        goods.setSafetyStock(0);
//        goods.setPoints(10);
//        goods.setSku("A11111");
//        goods.setShadeOpen((byte)1);
//        goods.setSort(1);
//        goods.setSortBestSell(1);
//        popupGoodsMapper.insert(goods);
//        log.info("~~~~~~~~~~~~~~~~~" + goods.getId());
//        goodsEntity.setGoods(goods);
//
//        List<PopupGoodsSku> list = new ArrayList<PopupGoodsSku>(2);
//        for (int i = 0; i < 2; i++) {
//            PopupGoodsSku goodsSku = new PopupGoodsSku();
//            goodsSku.setSku("AA" +goods.getId()+ i);
//            goodsSku.setColor("FF000" + i);
//            goodsSku.setGoodsId(goods.getId());
//            goodsSku.setShade("Light" + i);
//            goodsSku.setStock(10);
//            goodsSku.setStatus((byte)1);
////            goodsSku.setImgUrls("{\"bg\":\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\"}");
//            list.add(goodsSku);
//            popupGoodsSkuMapper.insert(goodsSku);
//        }
//        goodsEntity.setLiGoodsSku(list);
//        log.info("~~~~~~~~~~~~~~~~~" + JSONObject.toJSONString(goodsEntity));
//    }
//
////    @Test
//    public void update() {
//        PopupGoods goods = new PopupGoods();
//        goods.setId(6L);
//        goods.setWechatId(wechatId);
//        goods.setName("test 1");
//        String imgurls = "[{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img02_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img03_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img04_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img05_.jpg\",\"bigImg\":\"http://xxx2\"}]";
//        goods.setImgUrls(JSONObject.parseObject(imgurls));
//        goods.setCategoryId(0L);
//        goods.setCount(0);
//        goods.setDesc("");
//        goods.setGoodsNo("");
//        goods.setLimitCount(1);
//        goods.setPermit("1,2,3");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String curDateTime = sdf.format(new Date());
//        goods.setCreateTime(new Date());
////        t.setUpdateTime(curDateTime);
//        goods.setStatus((byte)1);
//        goods.setType((byte)0);
//        goods.setPrice(1);
//        goods.setSafetyStock(10);
//        goods.setPoints(10);
//        goods.setSku("A11111");
//        goods.setShadeOpen((byte)1);
//        goods.setSort(1);
//        popupGoodsMapper.updateByPrimaryKey(goods);
//    }
//
////    @Test
////    public void delete() {
////        PopupGoods t = new PopupGoods();
////        t.setName("test 1");
////        popupGoodsMapper.delete(t);
////    }
//
////    @Test
//    public void select() {
////        List<PopupGoodsList> list = service.queryPopupGoods(wechatId);
////        for (PopupGoodsList t : list) {
////            log.info(t.toString());
////        }
//        PopupGoodsFilter model = new PopupGoodsFilter();
//        model.setWechatId(wechatId);
//        model.setPageNum(1);
//        model.setPageSize(2);
//        Page<PopupGoodsList> data = service.selectGoodsList(model);
//
//        JSONObject object = new JSONObject();
//        object.put("data", data);
//        log.info(object.toJSONString());
//    }
//
//
////    @Test
//    public void selectById() {
//        PopupGoodsEntity goodsEntity = new PopupGoodsEntity();
//        PopupGoods goods = popupGoodsMapper.selectByPrimaryKey(goodsId);
//        goodsEntity.setGoods(goods);
//        goodsEntity.setLiGoodsSku(popupGoodsSkuMapper.selectPopupGoodsSkuByGoodsId(goodsId));
//        JSONObject object = new JSONObject();
//        object.put("data", goodsEntity);
//        log.info(object.toJSONString());
//    }
//
////    @Test
//    public void selectGoodsSku() {
//        List<PopupGoodsSku> list = popupGoodsSkuMapper.selectPopupGoodsSkuByGoodsId(goodsId);
//        for (PopupGoodsSku t : list) {
//            log.info(t.getSku());
//        }
//    }

    @Autowired
    private MemberTagDataDao memberTagDataDao;

    @Test
    public void batchUpdateTest() {
        final MemberTagData data1 = new MemberTagData.Builder().dataId(1).originalTag("批量更新1").build();
        final MemberTagData data2 = new MemberTagData.Builder().dataId(2).originalTag("批量更新2").build();
        memberTagDataDao.updateBatch(Lists.newArrayList(data1, data2));
    }

}
