package com.stylefeng.guns.modular.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.restapi.commans.params.ErrorCodeParam;
import com.stylefeng.guns.modular.restapi.commans.params.Params;
import com.stylefeng.guns.modular.restapi.commans.utils.ModelUtil;
import com.stylefeng.guns.modular.restapi.commans.utils.TimeUtil;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogUserActionMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryModelUserMapper;

/**
 * 定时任务接口
 * 
 * @author lori
 *
 */
@Controller
@RequestMapping("wayapi/v1/accessory/task")
public class TaskController {
	private static final Logger LOG = LoggerFactory.getLogger(AppBindController.class);
	private static String Tag = "StatisticalController";
	@Autowired
	private WayAccessoryModelUserMapper wayAccessoryModelUserMapper;
	@Autowired
	private WayAccessoryLogUserActionMapper wayAccessoryLogUserActionMapper;

	/**
	 * TODO 统计历史新增数量用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "newUserCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> newUserCount() {

		String methodName = "/newUserCount";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			String Today = TimeUtil.getTodayDateString();
			// 获得昨日新增人数

			String yesterday = TimeUtil.getYesterdayDateString();
			int sizeDay = wayAccessoryModelUserMapper.selectYesterdayAmount(yesterday);

			ModelUtil.setWayAccessoryStatisticalNewUser(Today, 1, sizeDay);
			// 判断今日是否是周一
			if (TimeUtil.dayForWeek(Today).equals("星期一")) {
				int sizeWeek = wayAccessoryModelUserMapper.selectLastWeekAmount(TimeUtil.getYesterdayDateString());
				ModelUtil.setWayAccessoryStatisticalNewUser(Today, 2, sizeWeek);
				// 判断今日是否是1号
			} else if (TimeUtil.isLastDayOfMonth(Today)) {
				int sizeMonth = wayAccessoryModelUserMapper.selectLastMonthAmount(TimeUtil.getYesterdayDateString());
				ModelUtil.setWayAccessoryStatisticalNewUser(Today, 2, sizeMonth);
			}
			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, "");
			return map;
		} catch (Exception e) {
			
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			e.printStackTrace();
			return map;
		}

	}

	/**
	 * TODO 统计用户行为数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "userAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userAction() {

		String methodName = "/userAction";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {

			String Today = TimeUtil.getTodayDateString();
			// 获得昨日用户行为
			List<Map<String, Object>> actionYesterdayList = wayAccessoryLogUserActionMapper
					.selectYesterdayAmount(TimeUtil.getYesterdayDateString());
			if (ToolUtil.isNotEmpty(actionYesterdayList)) {
				for (Map<String, Object> object : actionYesterdayList) {

					ModelUtil.setWayAccessoryStatisticalUserAction(1, Today,
							Integer.valueOf(object.get("type").toString()),
							Integer.valueOf(object.get("count").toString()));
				}

			}
			// 判断今日是否是周一
			if (TimeUtil.dayForWeek(Today).equals("星期一")) {
				List<Map<String, Object>> actionLastWeekAmountList = wayAccessoryLogUserActionMapper
						.selectLastWeekAmount(TimeUtil.getYesterdayDateString());
				if (ToolUtil.isNotEmpty(actionLastWeekAmountList)) {

					for (Map<String, Object> object : actionLastWeekAmountList) {

						ModelUtil.setWayAccessoryStatisticalUserAction(1, Today,
								Integer.valueOf(object.get("type").toString()),
								Integer.valueOf(object.get("count").toString()));
					}
				}
				// 判断今日是否是1号
			} else if (TimeUtil.isLastDayOfMonth(Today)) {

				List<Map<String, Object>> actionLastMonthAmountList = wayAccessoryLogUserActionMapper
						.selectLastMonthAmount(TimeUtil.getYesterdayDateString());
				if (ToolUtil.isNotEmpty(actionLastMonthAmountList)) {
					for (Map<String, Object> object : actionLastMonthAmountList) {

						ModelUtil.setWayAccessoryStatisticalUserAction(1, Today,
								Integer.valueOf(object.get("type").toString()),
								Integer.valueOf(object.get("count").toString()));
					}
				}
			}
			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, "");
			return map;

		} catch (Exception e) {
			
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			e.printStackTrace();
			return map;
		}
	}
}
