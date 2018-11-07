package com.d1m.wechat.model.enums;

/**
 * Created by jone.wang on 2018/11/6.
 * Description: 状态（0 未处理，1 处理中，2 处理成功，3 处理失败）
 */
public enum MemberTagDataStatus {
    /**
     * 未处理
     */
    UNTREATED,
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
