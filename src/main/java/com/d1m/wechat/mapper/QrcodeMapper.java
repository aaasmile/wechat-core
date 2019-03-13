package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.ReportQrcodeItemDto;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface QrcodeMapper extends MyMapper<Qrcode> {

	Integer countDuplicateName(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id, @Param("name") String name,
			@Param("status") byte status);

	Page<QrcodeDto> list(@Param("wechatId") Integer wechatId,
			@Param("query") String query,
			@Param("qrcodeTypeId") Integer qrcodeTypeId,
			@Param("status") byte status, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

	QrcodeDto get(@Param("wechatId") Integer wechatId, @Param("id") Integer id);

	List<ReportQrcodeItemDto> qrcodeReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("status") Integer status, @Param("qrcodeId") Integer qrcodeId, @Param("scene") String scene);

	Integer qrcodeTotalCount(@Param("wechatId") Integer wechatId,
			@Param("status") Integer status);
	
	Integer qrcodeCurrentTotalCount(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("status") Integer status, @Param("qrcodeId") Integer qrcodeId, @Param("scene") String scene);

	List<ReportQrcodeItemDto> qrcodeListReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end, 
			@Param("qrcodeId") Integer qrcodeId, @Param("scene") String scene, @Param("status") Integer status);

	List<QrcodeDto> qrcodeList(@Param("wechatId") Integer wechatId, @Param("status") Integer status);

	List<Qrcode> getLackWxQrcode();

	List<ReportQrcodeItemDto> qrcodeNameList(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("status") Integer status, @Param("qrcodeId") Integer qrcodeId,
			@Param("scene") String scene);

	List<Qrcode> getAllByWechatId(@Param("wechatId") Integer wechatId);
}
