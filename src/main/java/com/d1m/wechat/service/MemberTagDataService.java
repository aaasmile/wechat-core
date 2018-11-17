package com.d1m.wechat.service;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
public interface MemberTagDataService extends BaseService<MemberTagData> {

    void batchInsertFromExcel(Integer fileId, File file,Date runTask);

    void batchInsertFromCsv(Integer fileId, File file,Date runTask);

    /**
     * 导入数据检查
     *
     * @param list
     */
     void checkDataIsOK(CopyOnWriteArrayList<MemberTagData> list) throws Exception;

    /**
     * 更新上传数据状态
     *
     * @param fileId
     * @param status
     */
     void updateDataStatus(Integer fileId, Integer status);

    /**
     * 获取待处理的数据
     *
     * @param fileId
     */
    CopyOnWriteArrayList<MemberTagData> getMembertagCsvData(Integer fileId);


    /**
     * 获取待加签的正确数据
     *
     * @param fileId
     */
     List<MemberTagData> getCsvData(Integer fileId);

    /**
     * 批量导入加标签处理
     *
     * @param list
     * @throws Exception
     */
     Boolean addTags(List<MemberTagData> list) throws Exception;

    /**
     * 因必填信息引起的错误，需要更新状态为完成
     * @param dataId
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorStatus(Integer dataId, String errorMsg) throws Exception;

    /**
     * 分批处理
     * @param fileId
     */
    public void batchExecute(Integer fileId, CopyOnWriteArrayList<MemberTagData> list);

}
