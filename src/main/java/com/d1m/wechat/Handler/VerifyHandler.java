package com.d1m.wechat.Handler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.d1m.wechat.service.impl.MemberTagDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;

/**
 * @program: wechat-core
 * @Date: 2018/11/13 12:08
 * @Author: Liu weilin
 * @Description: excel导入校验
 */
@Slf4j
public class VerifyHandler implements IExcelVerifyHandler<MemberTagDataServiceImpl.BatchEntity> {


    @Override
    public ExcelVerifyHandlerResult verifyHandler(MemberTagDataServiceImpl.BatchEntity batchEntity) {
        ExcelVerifyHandlerResult result =new ExcelVerifyHandlerResult();
        if(batchEntity==null){
            log.info("上传文件种存在空格");
            result.setSuccess(false);
        }else {
            result.setSuccess(true);
        }
        return result;
    }
}
