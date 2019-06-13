package com.stylefeng.guns.modular.system.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.WayFidoU2FApplication;
import com.stylefeng.guns.modular.system.model.WayFidoU2FSystem;
import com.stylefeng.guns.modular.system.warpper.FidoRestAppWarpper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/fidorest/app")
public class FidoRestAppController extends BaseController {

	private static String PREFIX = "/fidorest/app/";
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
	 * 获取当前环境：测试/生产
	 * 
	 * @return
	 */
	@RequestMapping("/enviroment")
	@ResponseBody
	public Map<String, Object> getEnviroment() {
		Map<String, Object> map = new HashMap<>();
		map.put("isPro", isPro);
		if ("produce".equals(isPro)) {
			map.put("url", proURL);
		} else if ("dev".equals(isPro)) {
			map.put("url", devURL);
		} else {
			map.put("url", localURL);
		}
		return map;
	}

	/**
	 * 1.FIDOREST应用管理
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "fidorest_app.html";
	}

	/**
	 * 2.1 应用列表
	 */
	//@ApiOperation("查询FIDOREST应用列表")
	@RequestMapping("/list")
	@ResponseBody
	public Object appList(@RequestBody List<Map<String, String>> list) {
		request.getSession().setAttribute("appLists", list);
		return "success";
	}

	/**
	 * 2.2 应用列表
	 */
	//@ApiOperation("查询FIDOREST应用列表")
	@RequestMapping("/list2")
	@ResponseBody
	public Object appList2() {
		List<Map<String, Object>> list = (List<Map<String, Object>>) request.getSession().getAttribute("appLists");
		return super.warpObject(new FidoRestAppWarpper(list));
	}

	/**
	 * 跳转到应用添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String addApp(Model model) {
		return PREFIX + "fidorest_app_add.html";
	}

	/**
	 * 获取系统的tree列表
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<ZTreeNode> tree() {
		try {
			URL url;
			if ("produce".equals(isPro)) {
				url = new URL(proURL + "system/");
			} else if ("dev".equals(isPro)) {
				url = new URL(devURL + "system/");
			} else {
				url = new URL(localURL + "system/");
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

			// List<WayFidoU2FSystem> appList = (List<WayFidoU2FSystem>) JSON.parse(map.get("object").toString());
			List<WayFidoU2FSystem> appList = JSON.parseArray(map.get("object").toString(), WayFidoU2FSystem.class);
			List<ZTreeNode> tree = new ArrayList<>();
			for (WayFidoU2FSystem application : appList) {
				ZTreeNode zTreeNode = new ZTreeNode();
				zTreeNode.setId((long) application.getId());
				zTreeNode.setName(application.getSystemName());
				tree.add(zTreeNode);
			}
			return tree;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 跳转到应用修改页面
	 */
	@RequestMapping("/edit/{id}")
	public String editApp(@PathVariable("id") Integer id, Model model) {
		try {
			URL url;
			if ("produce".equals(isPro)) {
				url = new URL(proURL + "app/" + id);
			} else if ("dev".equals(isPro)) {
				url = new URL(devURL + "app/" + id);
			} else {
				url = new URL(localURL + "app/" + id);
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
			WayFidoU2FApplication app = JSON.parseObject(map.get("object").toString(), WayFidoU2FApplication.class);
			model.addAttribute("app", app);
			return PREFIX + "fidorest_app_edit.html";
		} catch (Exception e) {
			e.printStackTrace();
			return "/404.html";
		}
	}
}
