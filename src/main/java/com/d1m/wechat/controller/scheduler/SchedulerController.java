package com.d1m.wechat.controller.scheduler;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.model.Exceptions;
import com.d1m.wechat.model.Scheduler;
import com.d1m.wechat.model.SchedulerVO;
import com.d1m.wechat.model.enums.Category;
import com.d1m.wechat.model.enums.State;
import com.d1m.wechat.service.ExceptionsService;
import com.d1m.wechat.service.SchedulerService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "任务中心", tags = "任务中心测试接口")
@RestController
@RequestMapping("/scheduler")
public class SchedulerController extends BaseController {

  private final static Logger log = LoggerFactory.getLogger(SchedulerController.class);

  @Autowired
  private SchedulerService schedulerService;
  @Autowired
  private ExceptionsService exceptionsService;

  @ApiOperation( value = "创建任务", tags = "任务中心测试接口")
  @ApiResponse(code = 200, message = "创建任务")
  @RequestMapping(value = "create/{category}", method = RequestMethod.POST)
  public BaseResponse create(@RequestParam MultipartFile file,@PathVariable Integer category) {
    String id = UUID.randomUUID().toString().replaceAll("-","");
    try {
      final String originalFilename = file.getOriginalFilename();
      if (!originalFilename.endsWith(".csv") && !originalFilename.endsWith(".xls")
          && !originalFilename.endsWith(".xlsx")) {
        return new BaseResponse.Builder().resultCode(5001).msg("上传失败，格式有误，请上传.xlsx或.csv（utf-8格式）文件").build();
      }
      FileUploadConfigUtil instance = FileUploadConfigUtil.getInstance();
      String uploadPath = instance.getValue(getWechatId(), "upload_path");
      log.info("upload_path : " + uploadPath);
      String fileFullName = uploadPath + "/" + DateUtil.formatYYYYMMDD(new Date()) + "/"
          + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
      log.info("fileFullName : " + fileFullName);
      final File targetFile = new File(fileFullName);

      Scheduler schedulerC = new Scheduler();
      schedulerC.setId(id);
      schedulerC.setUploadFile(fileFullName);
      schedulerC.setCategory(category);
      schedulerC.setState(State.STEP0.getValue());
      schedulerC.setWechatCode(String.valueOf(this.getWechatId()));
      schedulerService.create(schedulerC);

      FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
      state(id, State.STEP1.getValue());
      return new BaseResponse.Builder().resultCode(Message.SCHEDULER_SUCCESS.getCode())
          .msg(Message.SCHEDULER_SUCCESS.getName()).build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      state(id, State.STEP_1.getValue());
      log(id, e.getMessage());
      return new BaseResponse.Builder().resultCode(Message.SCHEDULER_FAIL.getCode())
          .msg(Message.SCHEDULER_FAIL.getName()).data(id).build();
    }
  }

  @ApiOperation( value = "查询任务", tags = "任务中心测试接口")
  @ApiResponse(code = 200, message = "查询任务")
  @RequestMapping(value = "list", method = RequestMethod.POST)
  public JSONObject list(@RequestBody SchedulerVO schedulerVO) {
    try {
      schedulerVO.setWechatCode(String.valueOf(getWechatId()));
      List<Scheduler> list = schedulerService.list(schedulerVO);
      Integer count = schedulerService.count(schedulerVO);
      return representation(Message.SCHEDULER_SUCCESS, list, schedulerVO.getPageSize(), schedulerVO.getPageNum(), count);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return representation(Message.SCHEDULER_FAIL);
    }
  }

  @ApiOperation( value = "创建任务", tags = "任务中心用户导出")
  @ApiResponse(code = 200, message = "创建任务")
  @RequestMapping(value = "memberExport", method = RequestMethod.POST)
  public BaseResponse memberExport(Scheduler schedulerC) {
    String id = UUID.randomUUID().toString().replaceAll("-","");
    try {
      schedulerC.setId(id);
      schedulerC.setCategory(Category.MEMBER_EXPORT.getValue());
      schedulerC.setState(State.STEP0.getValue());
      schedulerC.setWechatCode(String.valueOf(this.getWechatId()));
      schedulerService.create(schedulerC);

      state(id, State.STEP1.getValue() );
      return new BaseResponse.Builder().resultCode(Message.SCHEDULER_SUCCESS.getCode())
          .msg(Message.SCHEDULER_SUCCESS.getName()).build();
    } catch (Exception e) {
      state(id,State.STEP_1.getValue() );
      log.error(e.getMessage(), e);
      log(id, e.getMessage());
      return new BaseResponse.Builder().resultCode(Message.SCHEDULER_FAIL.getCode())
          .msg(Message.SCHEDULER_FAIL.getName()).build();
    }
  }

  private void state(String id, Integer state) {
    Scheduler scheduler = new Scheduler();
    scheduler.setId(id);
    scheduler.setState(state);
    schedulerService.updateState(scheduler);
  }
  private void log(String schedulerId, String exception) {
    Exceptions exceptions = new Exceptions();
    exceptions.setException(exception);
    exceptions.setSchedulerId(schedulerId);
    exceptions.setId(UUID.randomUUID().toString().replaceAll("-",""));
    exceptionsService.create(exceptions);
  }
}
