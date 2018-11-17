package com.d1m.wechat.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/9/12 15:25
 * @Author: Liu weilin
 * @Description: 批量处理工具类
 */
@Component
public class BatchUtils {

    /**
     * @param batchSize 每一批处理的数量
     * @param amount    需要处理的集合大小
     * @return
     */
    public static Map<String, Integer> getTimes(Integer batchSize, Integer amount) {
        //times 批次
        Integer times = amount / batchSize;
        //remainAmount 剩余总数量
        Integer remainAmount = amount % batchSize;
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("times", times);
        map.put("remainAmount", remainAmount);
        return map;
    }


    public static void main(String[] ages) {
        List list = new ArrayList();
        list.add(0, "v1");
        list.add(1, "v2");
        list.add(2, "v3");
        list.add(3, "v4");
        list.add(4, "v5");
        list.add(5, "v6");
        list.add(6, "v7");
        list.add(7, "v8");
        list.add(8, "v9");
        list.add(9, "v10");
        list.add(10, "v11");
        list.add(11, "v12");
        list.add(12, "v13");
        list.add(13, "v14");
        list.add(14, "v15");
        list.add(15, "v16");
        list.add(16, "v17");
        List list1 = list.subList(0, 3);
        List list2 = list.subList(3, 6);
        List list3 = list.subList(6, 9);
        List list4 = list.subList(9, 12);
        List list5 = list.subList(12, 15);
        System.out.println(list.get(1));
    }
}
