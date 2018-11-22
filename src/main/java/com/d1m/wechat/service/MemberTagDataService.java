package com.d1m.wechat.service;

import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.dto.AnyschResolveDto;
import com.d1m.wechat.exception.WechatException;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
public interface MemberTagDataService extends BaseService<MemberTagData> {

    void batchInsertFromExcel(Integer fileId, File file,Date runTask,String tenant);

    void batchInsertFromCsv(Integer fileId, File file,Date runTask,String tenant);

    /**
     * 导入数据检查
     *
     * @param list
     */
     CopyOnWriteArrayList<MemberTagData> checkDataIsOK(CopyOnWriteArrayList<MemberTagData> list) throws Exception;


    /**
     * 获取待处理的数据
     *
     * @param fileId
     */
    CopyOnWriteArrayList<MemberTagData> getMembertagCsvData(Integer fileId, int pageNum, int batchSize);



    /**
     * 批量导入加标签处理
     *
     * @param list
     * @throws Exception
     */
     Integer addTags(List<MemberTagData> list) throws Exception;

    /**
     * 因必填信息引起的错误，需要更新状态为完成
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorStatus(String errorMsg, MemberTagData memberTagData) throws Exception;

    /**
     * 分批处理
     */
    public Integer batchExecute(CopyOnWriteArrayList<MemberTagData> list);

    /**
     * 异步解析
     * @param resolveDto
     */
     void anyscResolve(AnyschResolveDto resolveDto);



}
