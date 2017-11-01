package com.d1m.wechat.schedule.job;

import cn.d1m.wechat.client.model.WxMessage;
import com.d1m.wechat.mapper.MassConversationBatchMemberMapper;
import com.d1m.wechat.mapper.MassConversationBatchResultMapper;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.model.MassConversationBatchMember;
import com.d1m.wechat.model.MassConversationBatchResult;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.util.ParamUtil;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 批量群发任务类
 */
@JobHander(value="batchMassConversationJob")
@Component
public class BatchMassConversationJob extends BaseJobHandler {
	@Resource
	private MassConversationBatchResultMapper massConversationBatchResultMapper;

	@Resource
	private MassConversationBatchMemberMapper massConversationBatchMemberMapper;

	@Resource
	private MemberMapper memberMapper;

	@Resource
	private ConfigService configService;
	
	@Override
	public ReturnT<String> run(String... strings) throws Exception {
		try {
			// 参数1为微信ID，参数2为群发ID
			Integer wechatId = ParamUtil.getInt(strings[0], null);
			Integer batchMassId = ParamUtil.getInt(strings[1], null);
			XxlJobLogger.log("wechatId : "+wechatId + ", batchMassId : " + batchMassId);

			MassConversationBatchResult param = new MassConversationBatchResult();
			param.setWechatId(wechatId);
			param.setConversationId(batchMassId);
			param.setStatus(MassConversationResultStatus.WAIT_SEND.getValue());
			List<MassConversationBatchResult> batchList = massConversationBatchResultMapper.select(param);
			for(MassConversationBatchResult batchResult : batchList){
				MsgType msgType = MsgType.getByValue(batchResult.getMsgType());
				String wxMassMessage = batchResult.getMsgContent();

				MassConversationBatchMember batchMember = new MassConversationBatchMember();
				batchMember.setBatchId(batchResult.getId());
				batchMember.setWechatId(batchResult.getWechatId());
				List<MassConversationBatchMember> list = massConversationBatchMemberMapper.select(batchMember);

				List<String> wxusers = getWxusers(list);

				WxMessage wxMessage = WechatClientDelegate.sendMessage(wechatId, wxusers, msgType.toString().toLowerCase(), wxMassMessage);
				XxlJobLogger.log("batch mass return: "+wxMessage.getJson());
				if (wxMessage.fail()) {
					XxlJobLogger.log(wxMessage.getErrmsg());
				} else {
					batchResult.setMsgId(wxMessage.getMsgId());
					batchResult.setMsgDataId(wxMessage.getDataId());
					batchResult.setStatus(MassConversationResultStatus.SENDING.getValue());
					massConversationBatchResultMapper.updateByPrimaryKey(batchResult);

					// 分组推送时，更新会员本月群发数
					List<Integer> idList = new ArrayList<Integer>();
					for(MassConversationBatchMember bm:list){
						idList.add(bm.getMemberId());
					}
					if(!idList.isEmpty()){
						memberMapper.updateBatchSendMonth(idList);
					}
				}

				int batchInterval = 10;//秒为单位
				String batchIntervalStr = configService.getConfigValue(wechatId, "MASS_CONVERSATION", "BATCH_INTERVAL");
				if(StringUtils.isNotBlank(batchIntervalStr)){
					batchInterval = Integer.parseInt(batchIntervalStr);
				}
				TimeUnit.SECONDS.sleep(batchInterval);
			}
			return ReturnT.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("批量群发定时消息失败：" + e.getMessage());
			return ReturnT.FAIL;
		}
	}

	private List<String> getWxusers(List<MassConversationBatchMember> list){
		List<String> wxusers = null;
		if(list!=null&&!list.isEmpty()){
			wxusers = new ArrayList<String>();
			for(int i=0; i<list.size();i++){
				wxusers.add(list.get(i).getOpenId());
			}
		}
		return wxusers;
	}
}
