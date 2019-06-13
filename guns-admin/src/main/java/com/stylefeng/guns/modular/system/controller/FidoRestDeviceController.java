package com.stylefeng.guns.modular.system.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.system.model.WayFido2Device;
import com.stylefeng.guns.modular.system.model.WayFido2RelyParty;
import com.stylefeng.guns.modular.system.model.WayFidoU2FDevice;
import com.stylefeng.guns.modular.system.service.IWayFido2RelyPartyService;
import com.stylefeng.guns.modular.system.warpper.FidoRestDeviceWarpper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/fidorest/device")
public class FidoRestDeviceController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(FidoRestDeviceController.class);
	/**
	 * 获取当前软件运行环境:开发还是生产
	 */
	@Value("${spring.profiles.active}")
	private String isPro;
	/**
	 * 获取接口访问路径
	 */
	@Value("${u2f-rest.local-url}")
	private String localURL;

	@Value("${u2f-rest.dev-url}")
	private String devURL;

	@Value("${u2f-rest.pro-url}")
	private String proURL;
	private static String PREFIX = "/fidorest/device/";
	
	@Autowired
	private IWayFido2RelyPartyService IWayFido2RelyPartyService;

	/**
	 * 1.FIDOREST设备管理
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "fidorest_device.html";
	}

	/**
	 * TODO
	 * 配置多数据源，直接查询Fido2项目数据库，取消接口查询
	 * @return
	 */
	@ApiOperation("查询FIDOREST设备列表")
	@RequestMapping("/list2")
	@ResponseBody
	public Object deviceList() {
		Page<WayFido2Device> page = new PageFactory<WayFido2Device>().defaultPage();
		URL url;
		StringBuffer urlString = new StringBuffer();
		try {
			if ("produce".equals(isPro)) {
				urlString.append(proURL);
			} else if ("dev".equals(isPro)) {
				urlString.append(devURL);
			} else {
				urlString.append(localURL);
			}
			urlString.append("manager/device?current=" + page.getCurrent() + "&size=" + page.getSize());
			url = new URL(urlString.toString());
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			httpConn.disconnect();
			Map<String, Object> map = (Map<String, Object>) JSON.parse(buffer.toString());
			Map<String, Object> mapObject = (Map<String, Object>) JSON.parse(JSON.toJSONString(map.get("object")));
			List<WayFido2Device> deviceList = JSON.parseArray(mapObject.get("devices").toString(),WayFido2Device.class);

			for (WayFido2Device wayFidoU2FDevice : deviceList) {
				WayFido2RelyParty selectById = IWayFido2RelyPartyService.selectById(wayFidoU2FDevice.getRpId());
				if (Optional.ofNullable(selectById).isPresent()) {
					wayFidoU2FDevice.setRpName(selectById.getRpName());
				}
			}
			page.setTotal(Integer.parseInt(mapObject.get("total").toString()));
			page.setRecords(deviceList);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return super.packForBT(page);
	}

	/**
	 * 2. 设备列表
	 */
	@ApiOperation("查询FIDOREST设备列表")
	@RequestMapping("/list")
	@ResponseBody
	public Object appList(@RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String systemName) {
		Page<WayFidoU2FDevice> page = new PageFactory<WayFidoU2FDevice>().defaultPage();

		URL url;
		StringBuffer urlString = new StringBuffer();
		try {
			if ("produce".equals(isPro)) {
				urlString.append(proURL);
			} else if ("dev".equals(isPro)) {
				urlString.append(devURL);
			} else {
				urlString.append(localURL);
			}
			urlString.append("device?current=" + page.getCurrent() + "&size=" + page.getSize() +
					"&beginTime=" + beginTime + "&systemName=" + systemName + "&endTime=" + endTime);
			url = new URL(urlString.toString());
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			httpConn.disconnect();
			Map<String, Object> map = (Map<String, Object>) JSON.parse(buffer.toString());
			Map<String, Object> mapObject = (Map<String, Object>) JSON.parse(JSON.toJSONString(map.get("object")));
			List<WayFidoU2FDevice> deviceList = JSON.parseArray(mapObject.get("devices").toString(),WayFidoU2FDevice.class);

			List<Map<String, Object>> list = new ArrayList<>();
			for (WayFidoU2FDevice wayFidoU2FDevice : deviceList) {
				Map<String, Object> mapDevices = JSON.parseObject(JSON.toJSONString(wayFidoU2FDevice));
				list.add(mapDevices);
			}
			page.setTotal(Integer.parseInt(mapObject.get("total").toString()));
			page.setRecords((List<WayFidoU2FDevice>) new FidoRestDeviceWarpper(list).warp());
		} catch (Exception e) {
			//e.printStackTrace();
			log.info(e.getMessage());
		}
		return super.packForBT(page);
	}
}