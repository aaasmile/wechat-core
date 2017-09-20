package com.d1m.wechat;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.popup.*;
import com.d1m.wechat.model.popup.dao.PopupOrderDeliveryAddr;
import com.d1m.wechat.model.enums.GoodsEnum;
import com.d1m.wechat.model.enums.OrderEnum;
import com.d1m.wechat.model.enums.PayTypeEnum;
import com.d1m.wechat.model.popup.dao.*;
import com.d1m.wechat.service.IPopupGoodsService;
import com.d1m.wechat.service.IPopupOrderService;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class CoreApplicationTest {
    private Logger log = LoggerFactory.getLogger(CoreApplicationTest.class);
    public Integer wechatId = 32;
    public Long memberId = 316L;
    public Long goodsId = 70L;

    @Resource
    private IPopupGoodsService service;
    @Resource
    private IPopupOrderService orderService;
    @Resource
    private PopupGoodsMapper popupGoodsMapper;
    @Resource
    private PopupGoodsSkuMapper popupGoodsSkuMapper;
    @Resource
    private PopupOrderMapper popupOrderMapper;
    @Resource
    private PopupOrderGoodsRelMapper popupOrderGoodsRelMapper;
    @Resource
    private PopupOrderExpressMapper popupOrderExpressMapper;
    @Resource
    private PopupOrderDeliveryAddrMapper popupOrderDeliveryAddrMapper;
    @Resource
    private PopupDeliveryAddrMapper popupDeliveryAddrMapper;
    @Resource
    private PopupOrderInvoiceMapper popupOrderInvoiceMapper;
    @Resource
    private PopupOrderExtraAttrMapper popupOrderExtraAttrMapper;

//    @Transactional
    @Test
    public void contextLoads() {
//        insert();
//        select();
//        selectById();
//        selectGoodsSku();
//        createOrder();
//        selectOrder();
        Long[] g = {69L, 70L};
        String[] s = {"AA215", "AA216"};
        String gg = "69,70";
        String ss = "AA215,AA216";
        List<HashMap<String, Object>> list = popupGoodsMapper.selectPopupGoodsByGoodsIdAndSku(
                gg, ss);

//        popupGoodsSku.setGoodsId(68L);
//        popupGoodsSku.setSku("AA660");
//        popupGoodsSku.setStatus((byte)1);
//        popupGoodsSku.setStock(10);
//        popupGoodsSku.setColor("FF0000");
//        popupGoodsSku.setShade("Light311111");
        PopupGoods popupGoods = new PopupGoods();
        popupGoods.setId(68L);
        popupGoods = popupGoodsMapper.selectByPrimaryKey(popupGoods);
        log.info(JSONObject.toJSONString(popupGoods));

        PopupGoodsSku popupGoodsSku = new PopupGoodsSku();
        popupGoodsSku.setId(212L);
        PopupGoodsSku popupGoodsSkuNew = popupGoodsSkuMapper.selectByPrimaryKey(popupGoodsSku);
//        int state = popupGoodsSkuMapper.updateByPrimaryKey(popupGoodsSku);
        log.info(JSONObject.toJSONString(popupGoodsSkuNew));
    }

    public void searchOrder() {

    }

//    @Transactional
//    @Test
    public void createOrder() {
        PopupOrder orderBase = new PopupOrder();
        orderBase.setWechatId(wechatId);
        orderBase.setMemberId(memberId);
        orderBase.setPayType((byte) 0);
        orderBase.setPayStatus((byte) 0);
        orderBase.setNotifyStatus((byte) 0);
        orderBase.setNotifyUpdateTime(new Date());
        orderBase.setCreateTime(new Date());
        popupOrderMapper.insert(orderBase);

        List<PopupOrderGoodsRel> listOrderGoodsRel = new ArrayList<PopupOrderGoodsRel>();

        PopupOrderGoodsRel orderGoodsRel = new PopupOrderGoodsRel();
        orderGoodsRel.setOrderId(orderBase.getId());
        orderGoodsRel.setGoodsId(goodsId.longValue());
        orderGoodsRel.setSum(1);
        orderGoodsRel.setPrice(0);
        orderGoodsRel.setPoints(1L);
        orderGoodsRel.setCreateTime(new Date());
        orderGoodsRel.setSku("AA660");
        popupOrderGoodsRelMapper.insert(orderGoodsRel);
        listOrderGoodsRel.add(orderGoodsRel);

        orderGoodsRel = new PopupOrderGoodsRel();
        orderGoodsRel.setOrderId(orderBase.getId());
        orderGoodsRel.setGoodsId(goodsId.longValue());
        orderGoodsRel.setSum(1);
        orderGoodsRel.setPrice(0);
        orderGoodsRel.setPoints(1L);
        orderGoodsRel.setCreateTime(new Date());
        orderGoodsRel.setSku("AA661");
        popupOrderGoodsRelMapper.insert(orderGoodsRel);
        listOrderGoodsRel.add(orderGoodsRel);

        PopupDeliveryAddr deliveryAddr = new PopupDeliveryAddr();
        deliveryAddr.setProvince(110000);
        deliveryAddr.setCity(110100);
        deliveryAddr.setArea(110101);
        deliveryAddr.setAddr("上海市杨浦区控江路联创设计谷");
        deliveryAddr.setReceiverName("顾斌");
        deliveryAddr.setReceiverPhone("13817006428");
        deliveryAddr.setMemberId(memberId.longValue());
        popupDeliveryAddrMapper.insert(deliveryAddr);

        PopupOrderDeliveryAddr orderDeliveryAddr = new PopupOrderDeliveryAddr();
        orderDeliveryAddr.setOrderId(orderBase.getId());
        orderDeliveryAddr.setAddrId(deliveryAddr.getId());
        orderDeliveryAddr.setDeliveryType((byte)0);
        orderDeliveryAddr.setIsInvoice((byte)0);
        orderDeliveryAddr.setMsmOpen((byte)0);
        orderDeliveryAddr.setMsmPhone("");
        popupOrderDeliveryAddrMapper.insert(orderDeliveryAddr);

        PopupOrderInvoice orderInvoice = new PopupOrderInvoice();
        orderInvoice.setOrderId(orderBase.getId());
        orderInvoice.setCreditCode("123456789012345678");
        orderInvoice.setTitle("第一秒电商科技");
        orderInvoice.setProp((byte)0);
        orderInvoice.setStatus((byte)1);
        orderInvoice.setType((byte)0);
        popupOrderInvoiceMapper.insert(orderInvoice);

        PopupOrderExtraAttr orderExtraAttr = new PopupOrderExtraAttr();
        orderExtraAttr.setOrderId(orderBase.getId());
        orderExtraAttr.setGiftContent("");
        orderExtraAttr.setRemark("");
        popupOrderExtraAttrMapper.insert(orderExtraAttr);

        PopupOrderExpress orderExpress = new PopupOrderExpress();
        orderExpress.setCompany("顺丰快递");
        orderExpress.setOrderId(orderBase.getId());
        orderExpress.setStatus((byte)1);
        orderExpress.setTrackNo("83882920100101");
        orderExpress.setCreateDate(new Date());
        popupOrderExpressMapper.insert(orderExpress);

        PopupOrderEntity orderEntity = new PopupOrderEntity();
        orderEntity.setOrderBase(orderBase);
        orderEntity.setLiOrderGoods(listOrderGoodsRel);
        orderEntity.setOrderDeliveryAddr(orderDeliveryAddr);
        orderEntity.setOrderExpress(orderExpress);
        orderEntity.setOrderInvoice(orderInvoice);
        orderEntity.setOrderExtraAttr(orderExtraAttr);

        JSONObject object = new JSONObject();
        object.put("data", orderEntity);
        log.info(object.toJSONString());

    }

    //    @Test
    public void selectOrder() {
        PopupOrderFilter model = new PopupOrderFilter();
        model.setWechatId(wechatId);
        model.setPageNum(1);
        model.setPageSize(10);
        model.setStartDate("2017-09-10");
        model.setEndDate("2017-10-10");
        model.setPayStatus((byte)1);
        model.setPayType((byte)4);
        model.setReceiverPhone("13817006428");
        log.info(JSONObject.toJSONString(model));
        Page<PopupOrderList> data = orderService.selectOrderList(model);

        JSONObject object = new JSONObject();
        object.put("data", data);
        log.info(object.toJSONString());
    }

//    @Test
    public void insert() {

        PopupGoodsEntity goodsEntity = new PopupGoodsEntity();
        PopupGoods goods = new PopupGoods();
        goods.setWechatId(wechatId);
        goods.setName("test 2");
        String imgurls = "{\"bg\":\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\",\"img\":[\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\",\"http://dev.wechat.d1m.cn/static/marni/product_img02_.jpg\",\"http://dev.wechat.d1m.cn/static/marni/product_img03_.jpg\"]}";
        goods.setImgUrls(imgurls);
        goods.setCategoryId(0L);
        goods.setCount(20);
        goods.setDesc("");
        goods.setGoodsNo("");
        goods.setLimitCount(1);
        goods.setPermit("1,2,3");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDateTime = sdf.format(new Date());
        goods.setCreateTime(new Date());
//        t.setUpdateTime(curDateTime);
        goods.setStatus((byte)1);
        goods.setType((byte)0);
        goods.setPrice(1);
        goods.setSafetyStock(0);
        goods.setPoints(10);
        goods.setSku("A11111");
        goods.setShadeOpen((byte)1);
        goods.setSort(1);
        goods.setSortBestSell(1);
        popupGoodsMapper.insert(goods);
        log.info("~~~~~~~~~~~~~~~~~" + goods.getId());
        goodsEntity.setGoods(goods);

        List<PopupGoodsSku> list = new ArrayList<PopupGoodsSku>(2);
        for (int i = 0; i < 2; i++) {
            PopupGoodsSku goodsSku = new PopupGoodsSku();
            goodsSku.setSku("AA" +goods.getId()+ i);
            goodsSku.setColor("FF000" + i);
            goodsSku.setGoodsId(goods.getId());
            goodsSku.setShade("Light" + i);
            goodsSku.setStock(10);
            goodsSku.setStatus((byte)1);
//            goodsSku.setImgUrls("{\"bg\":\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\"}");
            list.add(goodsSku);
            popupGoodsSkuMapper.insert(goodsSku);
        }
        goodsEntity.setLiGoodsSku(list);
        log.info("~~~~~~~~~~~~~~~~~" + JSONObject.toJSONString(goodsEntity));
    }

//    @Test
    public void update() {
        PopupGoods goods = new PopupGoods();
        goods.setId(6L);
        goods.setWechatId(wechatId);
        goods.setName("test 1");
        String imgurls = "[{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img01_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img02_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img03_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img04_.jpg\",\"bigImg\":\"http://xxx2\"},{\"img\":\"http://dev.wechat.d1m.cn/static/marni/product_img05_.jpg\",\"bigImg\":\"http://xxx2\"}]";
        goods.setImgUrls(imgurls);
        goods.setCategoryId(0L);
        goods.setCount(0);
        goods.setDesc("");
        goods.setGoodsNo("");
        goods.setLimitCount(1);
        goods.setPermit("1,2,3");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDateTime = sdf.format(new Date());
        goods.setCreateTime(new Date());
//        t.setUpdateTime(curDateTime);
        goods.setStatus((byte)1);
        goods.setType((byte)0);
        goods.setPrice(1);
        goods.setSafetyStock(10);
        goods.setPoints(10);
        goods.setSku("A11111");
        goods.setShadeOpen((byte)1);
        goods.setSort(1);
        popupGoodsMapper.updateByPrimaryKey(goods);
    }

//    @Test
//    public void delete() {
//        PopupGoods t = new PopupGoods();
//        t.setName("test 1");
//        popupGoodsMapper.delete(t);
//    }

//    @Test
    public void select() {
//        List<PopupGoodsList> list = service.queryPopupGoods(wechatId);
//        for (PopupGoodsList t : list) {
//            log.info(t.toString());
//        }
        PopupGoodsFilter model = new PopupGoodsFilter();
        model.setWechatId(wechatId);
        model.setPageNum(1);
        model.setPageSize(2);
        Page<PopupGoodsList> data = service.selectGoodsList(model);

        JSONObject object = new JSONObject();
        object.put("data", data);
        log.info(object.toJSONString());
    }


//    @Test
    public void selectById() {
        PopupGoodsEntity goodsEntity = new PopupGoodsEntity();
        PopupGoods goods = popupGoodsMapper.selectByPrimaryKey(goodsId);
        goodsEntity.setGoods(goods);
        goodsEntity.setLiGoodsSku(popupGoodsSkuMapper.selectPopupGoodsSkuByGoodsId(goodsId));
        JSONObject object = new JSONObject();
        object.put("data", goodsEntity);
        log.info(object.toJSONString());
    }

//    @Test
    public void selectGoodsSku() {
        List<PopupGoodsSku> list = popupGoodsSkuMapper.selectPopupGoodsSkuByGoodsId(goodsId);
        for (PopupGoodsSku t : list) {
            log.info(t.getSku());
        }
    }

}
