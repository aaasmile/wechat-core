package com.d1m.wechat.model.enums;

/**
 * Created by jone.wang on 2018/11/6.
 * Description: 状态(0 导入中，1 已导入，2 导入失败，3 处理中，4 处理成功，5 处理失败)
 */
public enum MemberTagCsvStatus {
    /**
     * 导入中
     */
    IN_IMPORT,
    /**
     * 已导入
     */
    ALREADY_IMPORTED,
    /**
     * 导入失败
     */
    IMPORT_FAILURE,
    /**
     * 处理中
     */
    IN_PROCESS,
    /**
     * 处理成功
     */
    PROCESS_SUCCEED,
    /**
     * 处理失败
     */
    PROCESS_FAILURE
}
