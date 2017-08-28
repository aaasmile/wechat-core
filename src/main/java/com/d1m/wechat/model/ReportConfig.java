package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "report_config")
public class ReportConfig {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 报表索引键
     */
    @Column(name = "report_key")
    private String reportKey;

    /**
     * 导出的文件名
     */
    @Column(name = "report_name")
    private String reportName;

    /**
     * 报表索引键
     */
    @Column(name = "sheet_key")
    private String sheetKey;

    /**
     * 工作表名
     */
    @Column(name = "sheet_name")
    private String sheetName;

    /**
     * 报表SQL
     */
    @Column(name = "report_sql")
    private String reportSql;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取报表索引键
     *
     * @return report_key - 报表索引键
     */
    public String getReportKey() {
        return reportKey;
    }

    /**
     * 设置报表索引键
     *
     * @param reportKey 报表索引键
     */
    public void setReportKey(String reportKey) {
        this.reportKey = reportKey == null ? null : reportKey.trim();
    }

    /**
     * 获取导出的文件名
     *
     * @return report_name - 导出的文件名
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * 设置导出的文件名
     *
     * @param reportName 导出的文件名
     */
    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
    }

    /**
     * 获取报表索引键
     *
     * @return sheet_key - 报表索引键
     */
    public String getSheetKey() {
        return sheetKey;
    }

    /**
     * 设置报表索引键
     *
     * @param sheetKey 报表索引键
     */
    public void setSheetKey(String sheetKey) {
        this.sheetKey = sheetKey == null ? null : sheetKey.trim();
    }

    /**
     * 获取工作表名
     *
     * @return sheet_name - 工作表名
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * 设置工作表名
     *
     * @param sheetName 工作表名
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName == null ? null : sheetName.trim();
    }

    /**
     * 获取报表SQL
     *
     * @return report_sql - 报表SQL
     */
    public String getReportSql() {
        return reportSql;
    }

    /**
     * 设置报表SQL
     *
     * @param reportSql 报表SQL
     */
    public void setReportSql(String reportSql) {
        this.reportSql = reportSql == null ? null : reportSql.trim();
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }
}