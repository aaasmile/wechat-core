package com.d1m.wechat.service;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;

import java.io.File;
import java.util.List;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
public interface MemberTagDataService extends BaseService<MemberTagData> {

    void batchInsertFromExcel(Integer fileId, File file);

    void batchInsertFromCsv(Integer fileId, File file);

    /**
     * 导入数据检查
     *
     * @param list
     */
     void checkDataIsOK(List<MemberTagData> list) throws Exception;

}
