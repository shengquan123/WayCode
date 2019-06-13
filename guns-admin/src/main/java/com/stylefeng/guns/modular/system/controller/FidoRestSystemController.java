package com.stylefeng.guns.modular.system.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.WayFidoU2FSystem;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/fidorest/system")
public class FidoRestSystemController extends BaseController {

	private static String PREFIX = "/fidorest/system/";
	@Autowired
	private HttpServletRequest request;

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

	/**
	 * 1.FIDOREST系统管理
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "fidorest_system.html";
	}

	/**
	 * 2.1 系统列表
	 */
	@ApiOperation("查询FIDOREST系统列表")
	@RequestMapping("/list")
	@ResponseBody
	public Object systemList(@RequestBody List<Map<String, String>> list) {
		request.getSession().setAttribute("systemLists", list);
		return "success";
	}

	/**
	 * 2.2 系统列表
	 */
	@ApiOperation("查询FIDOREST系统列表")
	@RequestMapping("/list2")
	@ResponseBody
	public Object systemList2() {
		List<Map<String, Object>> list = (List<Map<String, Object>>) request.getSession().getAttribute("systemLists");
		return super.warpObject(new MenuWarpper(list));
	}

	/**
	 * 跳转到系统添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String addSystem() {

		return PREFIX + "fidorest_system_add.html";
	}

	/**
	 * 跳转到系统修改页面
	 */
	@RequestMapping("/edit/{id}")
	public String editSystem(@PathVariable("id") Integer id, Model model) {
		try {
			URL url;
			if ("produce".equals(isPro)) {
				url = new URL(proURL + "system/" + id);
			} else if ("dev".equals(isPro)) {
				url = new URL(devURL + "system/" + id);
			} else {
				url = new URL(localURL + "system/" + id);
			}
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
			WayFidoU2FSystem wayFidoU2FSystem = JSON.parseObject(map.get("object").toString(), WayFidoU2FSystem.class);
			model.addAttribute("wayFidoU2FSystem", wayFidoU2FSystem);
			LogObjectHolder.me().set(wayFidoU2FSystem);
			return PREFIX + "fidorest_system_edit.html";
		} catch (Exception e) {
			e.printStackTrace();
			return "/404.html";
		}
	}
}
