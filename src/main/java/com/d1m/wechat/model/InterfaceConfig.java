package com.d1m.wechat.model;

import com.d1m.wechat.model.enums.InterfaceMethodType;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.d1m.wechat.model.enums.InterfaceType;
import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "interface_config")
public class InterfaceConfig {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String brand;

	private String name;


	/**
	 * 请求方式； 0 POST，1 GET
	 */
	@Column(name = "method_type")
	private InterfaceMethodType methodType;

	/**
	 * 接口类型； 0 DCRM主动推送，1 第三方拉取
	 */
   	private InterfaceType type;

	private String event;

	private String parameter;

	private String description;

	private String url;

	private int sequence;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_at")
	private String updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	/**
	 * 接口状态； 0 生效中，1 未使用
	 * 第一期不做处理
	 */
	private InterfaceStatus status;


}
