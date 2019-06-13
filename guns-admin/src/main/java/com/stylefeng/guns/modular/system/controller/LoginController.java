package com.stylefeng.guns.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.InvalidKaptchaException;
import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.node.MenuNode;
import com.stylefeng.guns.core.shiro.EasyTypeToken;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ApiMenuFilter;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IMenuService;
import com.stylefeng.guns.modular.system.service.IUserService;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import static com.stylefeng.guns.core.support.HttpKit.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
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
    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }
    
    @RequestMapping(value = "/startLogin", method = RequestMethod.GET)
    @ResponseBody
    public Object startLogin(@RequestParam("userName") String userName) {
    	Map<String, Object> mapStartLogin = new HashMap<>();
    	User byAccount = userService.getByAccount(userName);
    	if (!Optional.ofNullable(byAccount).isPresent()) {
    		mapStartLogin.put("responseCode", "FIDOFIDO2VALIDATE102");
        	mapStartLogin.put("responseMessage", "用户名不存在!");
		} else {
			mapStartLogin.put("responseCode", "FIDOFIDO2VALIDATE101");
			mapStartLogin.put("responseMessage", "用户存在!");
		}
    	return mapStartLogin;
    }
    
    @RequestMapping(value = "/finishLogin")
    @ResponseBody
    public Object finishLogin(@RequestParam("userName") String userName) {
    	Map<String, Object> mapStartLogin = new HashMap<>();
    	Subject currentUser = ShiroKit.getSubject();
		EasyTypeToken easyTypeToken = new EasyTypeToken(userName);
		currentUser.login(easyTypeToken);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());
        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));
        ShiroKit.getSession().setAttribute("sessionFlag", true);
        mapStartLogin.put("responseCode", "FIDOFIDO2LOGIN001");
		mapStartLogin.put("responseMessage", "Login Success!");
    	return mapStartLogin;
    }

    /**
     * 免密登录-认证1
     * @return
     */
    @RequestMapping(value = "/startLogin", method = RequestMethod.POST)
    @ResponseBody
    public Object startLogin(@Param(value="systemName")String systemName,
    		@Param(value="userName")String userName, HttpServletRequest request) {
    	Map<String, Object> mapStartLogin = new HashMap<>();
    	String token = request.getHeader("Authorization");
    	User byAccount = userService.getByAccount(userName);
        /* 认证前获取数据接口需要的参数：systemName(当前系统，固定值), userUUId(根据username获取用户user_uuid)*/
        if (byAccount == null) {
        	mapStartLogin.put("responseCode", "FIDOU2FVALIDATE102");
        	mapStartLogin.put("responseMessage", "此用户不存在!");
			return mapStartLogin;
		} else {
			try {
	        	URL url = new URL(("produce".equals(isPro) ? proURL : ("dev".equals(isPro) ? devURL : localURL))
	    				+ "device2/startSignature");
				HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
				httpConn.setRequestMethod("POST");
				// httpConn.setRequestProperty("Content-Type", "application/json");
				httpConn.setRequestProperty("Authorization", token);
				// 发送POST请求必须设置如下两行
	    		httpConn.setDoOutput(true);
	    		httpConn.setDoInput(true);
				String param = "systemName=" + systemName + "&userUUId=" + byAccount.getUseruuid();
				PrintWriter printWriter = new PrintWriter(httpConn.getOutputStream());
				printWriter.write(param);
	            printWriter.flush(); // flush输出流的缓冲
				httpConn.connect();
				BufferedReader readerGetDevices = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				String lineGetDevices;
				StringBuffer bufferGetDevices = new StringBuffer();
				while ((lineGetDevices = readerGetDevices.readLine()) != null) {
					bufferGetDevices.append(lineGetDevices);
				}
				readerGetDevices.close();
				httpConn.disconnect();
				mapStartLogin = (Map<String, Object>) JSON.parse(bufferGetDevices.toString());
				return mapStartLogin;
			} catch (Exception e) {
				//e.printStackTrace();
				log.info(e.getMessage());
				mapStartLogin.put("responseCode", "FIDOU2FVALIDATE102");
	        	mapStartLogin.put("responseMessage", "System Error!");
				return mapStartLogin;
			}
		}
    }

    /**
     * 免密登录-认证2
     * @return
     */
    @RequestMapping(value = "/finishLogin", method = RequestMethod.POST)
    @ResponseBody
    public Object finishLogin(@Param(value = "systemName")String systemName, @Param(value = "userName")String userName,
    		@Param(value = "tokenResponse")String tokenResponse, HttpServletRequest request) {
    	Map<String, Object> mapStartLogin = new HashMap<>();
    	String token = request.getHeader("Authorization");
    	User byAccount = userService.getByAccount(userName);
        /* 认证前获取数据接口需要的参数：systemName(当前系统，固定值), userUUId(根据username获取用户user_uuid)*/
        if (byAccount == null) {
        	mapStartLogin.put("responseCode", "FIDOU2FVALIDATE401");
        	mapStartLogin.put("responseMessage", "此用户不存在！");
			return mapStartLogin;
		} else {
			try {
				URL url = new URL(("produce".equals(isPro) ? proURL : ("dev".equals(isPro) ? devURL : localURL))
						+ "device2/finishSignature");
				HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
				httpConn.setRequestMethod("POST");
				httpConn.setRequestProperty("Authorization", token);
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				String param = "systemName=" + systemName + "&userUUId=" + byAccount.getUseruuid() + "&tokenResponse="
						+ tokenResponse;
				PrintWriter printWriter = new PrintWriter(httpConn.getOutputStream());
				printWriter.write(param);
	            printWriter.flush();
				httpConn.connect();
				BufferedReader readerGetDevices = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				String lineGetDevices;
				StringBuffer bufferGetDevices = new StringBuffer();
				while ((lineGetDevices = readerGetDevices.readLine()) != null) {
					bufferGetDevices.append(lineGetDevices);
				}
				readerGetDevices.close();
				httpConn.disconnect();
				mapStartLogin = (Map<String, Object>) JSON.parse(bufferGetDevices.toString());

				Subject currentUser = ShiroKit.getSubject();
				EasyTypeToken easyTypeToken = new EasyTypeToken(userName);
				currentUser.login(easyTypeToken);

		        ShiroUser shiroUser = ShiroKit.getUser();
		        super.getSession().setAttribute("shiroUser", shiroUser);
		        super.getSession().setAttribute("username", shiroUser.getAccount());
		        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));
		        ShiroKit.getSession().setAttribute("sessionFlag", true);
				return mapStartLogin;
			} catch (Exception e) {
				//e.printStackTrace();
				log.info(e.getMessage());
				mapStartLogin.put("responseCode", "FIDOU2FVALIDATE401");
				mapStartLogin.put("responseMessage", "System Error!");
				return  mapStartLogin;
			}
		}
    }

    /**
     * 密码登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {
    	String username = super.getPara("username").trim(),
    			remember = super.getPara("remember"),
    			password = super.getPara("password").trim();
    	//验证验证码是否正确
		if (KaptchaUtil.getKaptchaOnOff()) {
			String kaptcha = super.getPara("kaptcha").trim();
			String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
				throw new InvalidKaptchaException();
			}
		}

        Subject currentUser = ShiroKit.getSubject();
        //UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        EasyTypeToken easyTypeToken = new EasyTypeToken(username, password);
        if ("on".equals(remember)) {
        	easyTypeToken.setRememberMe(true);
        } else {
        	easyTypeToken.setRememberMe(false);
        }

        currentUser.login(easyTypeToken);
        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());
        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));
        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
