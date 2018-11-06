package com.d1m.wechat.domain.entity;

import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "`member_tag_csv`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTagCsv {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "`file_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    /**
     * 原始文件名
     */
    @Column(name = "`ori_file`")
    private String oriFile;

    /**
     * //处理状态（0-未处理，1-进行中，2-处理完成）
     * 状态(0 导入中，1 已导入，2 导入失败，3 处理中，4 处理成功，5 处理失败)
     */
    @Column(name = "`status`")
    private MemberTagCsvStatus status;

    /**
     * 任务名称
     */
    @Column(name = "`task`")
    private String task;

    /**
     * 公众号ID
     */
    @Column(name = "`wechat_id`")
    private Integer wechatId;

    /**
     * 创建人ID
     */
    @Column(name = "`creator_id`")
    private Integer creatorId;

    /**
     * 任务执行时间
     */
    @Column(name = "`created_at`")
    private Timestamp createdAt;

    /**
     * 源文件路径
     */
    @Column(name = "`source_file_path`")
    private String sourceFilePath;

    /**
     * 异常信息文件路径
     */
    @Column(name = "`exception_file_path`")
    private String exceptionFilePath;

    /**
     * 文件大小
     */
    @Column(name = "`file_size`")
    private String fileSize;

    /**
     * 行数
     */
    @Column(name = "`rows`")
    private Integer rows;

    /**
     * 文件格式
     */
    @Column(name = "`format`")
    private String format;

    /**
     * 文件编码
     */
    @Column(name = "`encoding`")
    private String encoding;

    @Column(name = "`last_update_time`")
    private Timestamp lastUpdateTime;

    /**
     * 成功条数
     */
    @Column(name = "`success_count`")
    private Integer successCount;

    /**
     * 失败条数
     */
    @Column(name = "`fail_count`")
    private Integer failCount;

    /**
     * 失败原因
     */
    @Column(name = "`error_msg`")
    private String errorMsg;

    @Column(name = "`remark`")
    private String remark;

}