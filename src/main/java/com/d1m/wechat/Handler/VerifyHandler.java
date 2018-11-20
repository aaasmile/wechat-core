package com.d1m.wechat.Handler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d1m.wechat.service.impl.MemberTagDataServiceImpl;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wechat-core
 * @Date: 2018/11/13 12:08
 * @Author: Liu weilin
 * @Description: excel导入校验
 */
@Slf4j
public class VerifyHandler implements IExcelVerifyHandler<MemberTagDataServiceImpl.BatchEntity> {

	private static final Logger log = LoggerFactory.getLogger(VerifyHandler.class);
    @Override
    public ExcelVerifyHandlerResult verifyHandler(MemberTagDataServiceImpl.BatchEntity batchEntity) {
        ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult();
        if (batchEntity != null) {
            if (StringUtils.isEmpty(batchEntity.getOpenid()) && StringUtils.isEmpty(batchEntity.getTag())) {
                log.info("上传文件中存在含有空格的行");
                result.setMsg("上传文件中存在含有空格的行");
                result.setSuccess(false);
            } else {
                result.setSuccess(true);
            }
        } else {
            result.setSuccess(false);
        }
        return result;
    }
}
