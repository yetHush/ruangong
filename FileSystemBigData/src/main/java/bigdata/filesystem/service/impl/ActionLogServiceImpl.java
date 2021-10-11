package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseConstant;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.dto.ActionLogDeleteDTO;
import bigdata.filesystem.dto.ActionLogUnfreezeDTO;
import bigdata.filesystem.dto.query.GetLogsDTO;
import bigdata.filesystem.dto.query.GetLogsResultDTO;
import bigdata.filesystem.entity.ActionLog;
import bigdata.filesystem.mapper.ActionLogMapper;
import bigdata.filesystem.mapper.UserMapper;
import bigdata.filesystem.service.ActionLogService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class ActionLogServiceImpl extends BaseService<ActionLog, Long> implements ActionLogService {

    @Autowired
    private ActionLogMapper actionLogMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void unfreezeActionLog(ActionLogUnfreezeDTO actionLogUnfreezeDTO) {
        ActionLog actionLog = new ActionLog();
        BeanUtils.copyProperties(this.getById(actionLogUnfreezeDTO.getLogId()), actionLog); //user是需要被解冻的用户
        actionLog.setStatus(BaseConstant.STATUS_NORMAL);
        this.update(actionLog);
    }

    @Override
    public void deleteActionLog(ActionLogDeleteDTO actionLogDeleteDTO) {
        ActionLog actionLog = new ActionLog();
        BeanUtils.copyProperties(this.getById(actionLogDeleteDTO.getLogId()), actionLog); //user是需要被解冻的用户
        actionLog.setStatus(BaseConstant.STATUS_DELETE);
        this.update(actionLog);
    }

    @Override
    public void hardDeleteActionLog(ActionLogDeleteDTO actionLogDeleteDTO) {
        this.deleteById(actionLogDeleteDTO.getLogId());
    }

    @Override
    public Object getLogs(GetLogsDTO getLogsDTO) {

        int pageSize = getLogsDTO.getPageSize();
        startPage(getLogsDTO.getPageNum(), pageSize);

        List<GetLogsResultDTO> list = actionLogMapper.getLogs(getLogsDTO);

        PageInfo<GetLogsResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public Object getHomeData() {
        Map<String, Object> result = new HashMap<>();
        // 获取今日访问数量、昨天访问数量
        String loginUrl = "/user/login";
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        calendar.setTime(now);
        try {
            Date day = sdf.parse(sdf.format(now));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day1 = sdf.format(calendar.getTime());
            Date day_1 = sdf.parse(day1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day2 = sdf.format(calendar.getTime());
            Date day_2 = sdf.parse(day2);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day3 = sdf.format(calendar.getTime());
            Date day_3 = sdf.parse(day3);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day4 = sdf.format(calendar.getTime());
            Date day_4 = sdf.parse(day4);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day5 = sdf.format(calendar.getTime());
            Date day_5 = sdf.parse(day5);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day6 = sdf.format(calendar.getTime());
            Date day_6 = sdf.parse(day6);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String day7 = sdf.format(calendar.getTime());
            Date day_7 = sdf.parse(day7);
            // 成功获取到时间
            List<String> days = new ArrayList<>();
            days.add(day7);
            days.add(day6);
            days.add(day5);
            days.add(day4);
            days.add(day3);
            days.add(day2);
            days.add(day1);
            result.put("days", days);
            // 访问数量
            Long todayLoginNum = actionLogMapper.getLoginNum(day, now);
            Long yesterdayLoginNum = actionLogMapper.getLoginNum(day_1, day);
            result.put("todayLoginNum", todayLoginNum);
            result.put("yesterdayLoginNum", yesterdayLoginNum);
            // 提交次数
            Long todaySubmitNum = actionLogMapper.getSubmitNum(day, now);
            Long yesterdaySubmitNum = actionLogMapper.getSubmitNum(day_1, day);
            result.put("todaySubmitNum", todaySubmitNum);
            result.put("yesterdaySubmitNum", yesterdaySubmitNum);
            // 上传数量
            Long todayUploadNum = actionLogMapper.getUploadNum(day, now);
            Long yesterdayUploadNum = actionLogMapper.getUploadNum(day_1, day);
            result.put("todayUploadNum", todayUploadNum);
            result.put("yesterdayUploadNum", yesterdayUploadNum);
            // 下载数量
            Long todayDownloadNum = actionLogMapper.getDownloadNum(day, now);
            Long yesterdayDownloadNum = actionLogMapper.getDownloadNum(day_1, day);
            result.put("todayDownloadNum", todayDownloadNum);
            result.put("yesterdayDownloadNum", yesterdayDownloadNum);
            // 获取用户不同状态的数量
            Long userNormalNum = userMapper.getNumByStatus(BaseConstant.STATUS_NORMAL);
            Long userFreezeNum = userMapper.getNumByStatus(BaseConstant.STATUS_FREEZE);
            Long userDeleteNum = userMapper.getNumByStatus(BaseConstant.STATUS_DELETE);
            List<Long> userNums = new ArrayList<>();
            userNums.add(userNormalNum);
            userNums.add(userFreezeNum);
            userNums.add(userDeleteNum);
            result.put("userNums", userNums);
            // 获取过去一周每天的文件上传下载数量
            Long uploadDay1 = yesterdayUploadNum;
            Long uploadDay2 = actionLogMapper.getUploadNum(day_2, day_1);
            Long uploadDay3 = actionLogMapper.getUploadNum(day_3, day_2);
            Long uploadDay4 = actionLogMapper.getUploadNum(day_4, day_3);
            Long uploadDay5 = actionLogMapper.getUploadNum(day_5, day_4);
            Long uploadDay6 = actionLogMapper.getUploadNum(day_6, day_5);
            Long uploadDay7 = actionLogMapper.getUploadNum(day_7, day_6);
            Long downloadDay1 = yesterdayDownloadNum;
            Long downloadDay2 = actionLogMapper.getDownloadNum(day_2, day_1);
            Long downloadDay3 = actionLogMapper.getDownloadNum(day_3, day_2);
            Long downloadDay4 = actionLogMapper.getDownloadNum(day_4, day_3);
            Long downloadDay5 = actionLogMapper.getDownloadNum(day_5, day_4);
            Long downloadDay6 = actionLogMapper.getDownloadNum(day_6, day_5);
            Long downloadDay7 = actionLogMapper.getDownloadNum(day_7, day_6);
            List<Long> uploadNums = new ArrayList<>();
            uploadNums.add(uploadDay7);
            uploadNums.add(uploadDay6);
            uploadNums.add(uploadDay5);
            uploadNums.add(uploadDay4);
            uploadNums.add(uploadDay3);
            uploadNums.add(uploadDay2);
            uploadNums.add(uploadDay1);
            List<Long> downloadNums = new ArrayList<>();
            downloadNums.add(downloadDay7);
            downloadNums.add(downloadDay6);
            downloadNums.add(downloadDay5);
            downloadNums.add(downloadDay4);
            downloadNums.add(downloadDay3);
            downloadNums.add(downloadDay2);
            downloadNums.add(downloadDay1);
            result.put("uploadNums", uploadNums);
            result.put("downloadNums", downloadNums);
        } catch (ParseException e) {
            this.throwException(e, BaseMessage.DATA_ERROR.getMsg());
        }
        return result;
    }
}
