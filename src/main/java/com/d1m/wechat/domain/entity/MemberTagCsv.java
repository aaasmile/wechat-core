package com.d1m.wechat.domain.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.d1m.wechat.model.enums.MemberTagCsvStatus;

@Table(name = "`member_tag_csv`")
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

	public MemberTagCsv(Integer fileId, String oriFile, MemberTagCsvStatus status, String task, Integer wechatId,
			Integer creatorId, Timestamp createdAt, String sourceFilePath, String exceptionFilePath, String fileSize,
			Integer rows, String format, String encoding, Timestamp lastUpdateTime, Integer successCount,
			Integer failCount, String errorMsg, String remark) {
		super();
		this.fileId = fileId;
		this.oriFile = oriFile;
		this.status = status;
		this.task = task;
		this.wechatId = wechatId;
		this.creatorId = creatorId;
		this.createdAt = createdAt;
		this.sourceFilePath = sourceFilePath;
		this.exceptionFilePath = exceptionFilePath;
		this.fileSize = fileSize;
		this.rows = rows;
		this.format = format;
		this.encoding = encoding;
		this.lastUpdateTime = lastUpdateTime;
		this.successCount = successCount;
		this.failCount = failCount;
		this.errorMsg = errorMsg;
		this.remark = remark;
	}

	public MemberTagCsv() {
		super();
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getOriFile() {
		return oriFile;
	}

	public void setOriFile(String oriFile) {
		this.oriFile = oriFile;
	}

	public MemberTagCsvStatus getStatus() {
		return status;
	}

	public void setStatus(MemberTagCsvStatus status) {
		this.status = status;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}

	public String getExceptionFilePath() {
		return exceptionFilePath;
	}

	public void setExceptionFilePath(String exceptionFilePath) {
		this.exceptionFilePath = exceptionFilePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static class Builder {
		private Integer fileId;
		private String oriFile;
		private MemberTagCsvStatus status;
		private String task;
		private Integer wechatId;
		private Integer creatorId;
		private Timestamp createdAt;
		private String sourceFilePath;
		private String exceptionFilePath;
		private String fileSize;
		private Integer rows;
		private String format;
		private String encoding;
		private Timestamp lastUpdateTime;
		private Integer successCount;
		private Integer failCount;
		private String errorMsg;
		private String remark;

		public Builder fileId(Integer fileId) {
			this.fileId = fileId;
			return this;
		}

		public Builder oriFile(String oriFile) {
			this.oriFile = oriFile;
			return this;
		}

		public Builder status(MemberTagCsvStatus status) {
			this.status = status;
			return this;
		}

		public Builder task(String task) {
			this.task = task;
			return this;
		}

		public Builder wechatId(Integer wechatId) {
			this.wechatId = wechatId;
			return this;
		}

		public Builder creatorId(Integer creatorId) {
			this.creatorId = creatorId;
			return this;
		}

		public Builder createdAt(Timestamp createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder sourceFilePath(String sourceFilePath) {
			this.sourceFilePath = sourceFilePath;
			return this;
		}

		public Builder exceptionFilePath(String exceptionFilePath) {
			this.exceptionFilePath = exceptionFilePath;
			return this;
		}

		public Builder fileSize(String fileSize) {
			this.fileSize = fileSize;
			return this;
		}

		public Builder rows(Integer rows) {
			this.rows = rows;
			return this;
		}

		public Builder format(String format) {
			this.format = format;
			return this;
		}

		public Builder encoding(String encoding) {
			this.encoding = encoding;
			return this;
		}

		public Builder lastUpdateTime(Timestamp lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
			return this;
		}

		public Builder successCount(Integer successCount) {
			this.successCount = successCount;
			return this;
		}

		public Builder failCount(Integer failCount) {
			this.failCount = failCount;
			return this;
		}

		public Builder errorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
			return this;
		}

		public Builder remark(String remark) {
			this.remark = remark;
			return this;
		}

		public MemberTagCsv build() {
			return new MemberTagCsv(this);
		}
	}

	private MemberTagCsv(Builder builder) {
		this.fileId = builder.fileId;
		this.oriFile = builder.oriFile;
		this.status = builder.status;
		this.task = builder.task;
		this.wechatId = builder.wechatId;
		this.creatorId = builder.creatorId;
		this.createdAt = builder.createdAt;
		this.sourceFilePath = builder.sourceFilePath;
		this.exceptionFilePath = builder.exceptionFilePath;
		this.fileSize = builder.fileSize;
		this.rows = builder.rows;
		this.format = builder.format;
		this.encoding = builder.encoding;
		this.lastUpdateTime = builder.lastUpdateTime;
		this.successCount = builder.successCount;
		this.failCount = builder.failCount;
		this.errorMsg = builder.errorMsg;
		this.remark = builder.remark;
	}

	@Override
	public String toString() {
		return "MemberTagCsv [fileId=" + fileId + ", oriFile=" + oriFile + ", status=" + status + ", task=" + task
				+ ", wechatId=" + wechatId + ", creatorId=" + creatorId + ", createdAt=" + createdAt
				+ ", sourceFilePath=" + sourceFilePath + ", exceptionFilePath=" + exceptionFilePath + ", fileSize="
				+ fileSize + ", rows=" + rows + ", format=" + format + ", encoding=" + encoding + ", lastUpdateTime="
				+ lastUpdateTime + ", successCount=" + successCount + ", failCount=" + failCount + ", errorMsg="
				+ errorMsg + ", remark=" + remark + "]";
	}
}