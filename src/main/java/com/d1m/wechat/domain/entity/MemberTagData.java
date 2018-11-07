package com.d1m.wechat.domain.entity;

import com.d1m.wechat.model.enums.MemberTagDataStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "`member_tag_data`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTagData {
    @Id
    @Column(name = "`data_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    /**
     * 上传文件编号
     */
    @Column(name = "`file_id`")
    private Integer fileId;

    @Column(name = "`open_id`")
    private String openId;

    @Column(name = "`wechat_id`")
    private Integer wechatId;

    @Column(name = "`tag`")
    private String tag;

    /**
     * 问题标签
     */
    @Column(name = "`error_tag`")
    private String errorTag;

    /**
     * 原始标签
     */
    @Column(name = "`original_tag`")
    private String originalTag;

    /**
     * 状态（0 未处理，4 处理中，5 处理成功，6 处理失败）
     */
    @Column(name = "`status`")
    private MemberTagDataStatus status;

    /**
     * 数据检查状态
     * (1 正常，0 有问题)
     */
    @Column(name = "`check_status`")
    private Boolean checkStatus;

    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`error_msg`")
    private String errorMsg;

    /**
     * 乐观锁版本号
     */
    @Column(name = "`version`")
    private Integer version;

    @Column(name = "`created_at`")
    private Timestamp createdAt;

    @Column(name = "`finish_time`")
    private Timestamp finishTime;

}