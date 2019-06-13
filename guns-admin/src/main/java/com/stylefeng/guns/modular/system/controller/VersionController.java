package com.stylefeng.guns.modular.system.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.system.model.OperationLog;
import com.stylefeng.guns.modular.system.model.WayAccessorySoftwareVersion;
import com.stylefeng.guns.modular.system.service.IVersionService;
import com.stylefeng.guns.modular.system.transfer.VersionDto;
import com.stylefeng.guns.modular.system.warpper.WayAccessorySoftwareVersionWarpper;

/**
 * 版本管理的控制器
 * 
 * @author shengquan
 * @Date 2018年2月27日
 */
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

	private static String PREFIX = "/system/version/";
	private static File newFile;

	/**
	 * 获取当前软件运行环境:开发还是生产
	 */
	@Value("${spring.profiles.active}")
	private String isPro;

	/**
	 * 获取测试环境文件上传保存路径
	 */
	@Value("${WAYSoftware.devFilePath}")
	private String devFilePath;

	/**
	 * 获取生产环境文件上传保存路径
	 */
	@Value("${WAYSoftware.proFilePath}")
	private String proFilePath;

	/**
	 * 获取测试环境文件下载路径
	 */
	@Value("${WAYSoftware.devDownloadPath}")
	private String devDownloadPath;

	/**
	 * 获取生产环境文件下载路径
	 */
	@Value("${WAYSoftware.proDownloadPath}")
	private String proDownloadPath;

	@Autowired
	private IVersionService versionService;

	/**
	 * 1.跳转到版本管理首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "version.html";
	}

	/**
	 * 2.查询版本列表List
	 * 初始化时查询全部版本列表,可根据所选条件进行条件查询
	 * 参数(软件类型:1:客户端App-SAFE,2:客户端App-NORMAL,3:设备固件-SAFE,4:设备固件-NORMAL1,5:设备固件-NORMAL3)非必须
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(value = "softType", required = false) Integer type) {

		Page<OperationLog> page = new PageFactory<OperationLog>().defaultPage();
		List<Map<String, Object>> result = versionService.getVersions(page, type, page.getOrderByField(), page.isAsc());
		page.setRecords((List<OperationLog>) new WayAccessorySoftwareVersionWarpper(result).warp());
		return super.packForBT(page);
	}

	/**
	 * 3.跳转到添加软件的页面
	 */
	@RequestMapping("/version_add")
	public String versionAdd() {
		return PREFIX + "version_add.html";
	}

	/**
	 * 4.上传文件
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	@ResponseBody
	//, @RequestParam("prefix") String prefix
	public String fileUpload(@RequestPart("file") MultipartFile file) {

		// 生产环境文件保存路径proFilePath:/opt/tomcat8/webapps/ROOT/WAYSoftware
		// 测试环境文件保存路径devDownloadPath:/usr/local/tomcat/webapps/ROOT/WAYSoftware
		File fileParent = new File(("produce".equals(isPro) ? proFilePath : devFilePath) + "/temp/");
		//File fileParent = new File("WAYSoftware/temp/"); // 本机测试

		newFile = new File(fileParent + "/" + file.getOriginalFilename());
		if (fileParent.exists()) {
			newFile.delete();
		}
		try {
			file.transferTo(new File(newFile.getAbsolutePath()));
		} catch (IOException e) {
			throw new GunsException(BizExceptionEnum.FILE_UPLOAD_ERROR); // 文件上传错误
		}
		return "上传文件临时保存位置:" + newFile.getAbsolutePath();
	}

	/**
	 * 5.添加软件提交
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/addSubmit")
	@ResponseBody
	public Tip addSubmit(@Valid VersionDto versionDto, BindingResult result) {
		if (result.hasErrors()) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}

		// 1:waySAFEClient_v1.exe 2:wayNORMALClient_v1.exe
		// 3:waySAFE-USB_v2.bin 4:wayNORMAL-USB_v2.bin
		int softType = versionDto.getSoftType(); // 获取上传软件类型
		String versionName = versionDto.getVersionName(); // 获取版本名称
		File submitNewFile = null;
		File solidFile = null;
		String submitNewFileName = "";
		String solidFileName = "";
		switch (softType) {
			case 1:
				submitNewFileName = "/app/safe/waySAFEClient_" + versionName + ".exe";
				solidFileName = "/app/safe/waySAFEClient.exe";
				break;
			case 2:
				submitNewFileName = "/app/normal/wayNORMALClient_" + versionName + ".exe";
				solidFileName = "/app/normal/wayNORMALClient.exe";
				break;
			case 3:
				submitNewFileName = "/firmware/safe/waySAFE-USB_" + versionName + ".bin";
				solidFileName = "/firmware/safe/waySAFE-USB.bin";
				break;
			case 4:
				submitNewFileName = "/firmware/normal1/wayNORMAL1-USB_" + versionName + ".bin";
				solidFileName = "/firmware/normal1/wayNORMAL1-USB.bin";
				break;
			case 5:
				submitNewFileName = "/firmware/normal3/wayNORMAL3-USB_" + versionName + ".bin";
				solidFileName = "/firmware/normal3/wayNORMAL3-USB.bin";
			default:
				break;
		}
		submitNewFile = new File(("produce".equals(isPro) ? proFilePath : devFilePath) + submitNewFileName);
		solidFile = new File(("produce".equals(isPro) ? proFilePath : devFilePath) + solidFileName);
		/*
		 * 本机测试
		 */
		//submitNewFile = new File("WAYSoftware" + submitNewFileName);
		//solidFile = new File("WAYSoftware" + solidFileName);

		if (submitNewFile.exists()) {
			submitNewFile.delete();
		}
		if (solidFile.exists()) {
			solidFile.delete();
		}

		try {
			Files.copy(newFile.toPath(), submitNewFile.toPath());
			Files.copy(newFile.toPath(), solidFile.toPath());
		} catch (IOException e) {
			throw new GunsException(BizExceptionEnum.FILE_COPY_ERROR); // 文件复制错误
		}

		WayAccessorySoftwareVersion version = new WayAccessorySoftwareVersion();
		version.setType(versionDto.getSoftType());				 // 1设置软件类型
		version.setVersionName(versionDto.getVersionName()); 	 // 2设置软件版本名称
		version.setVersionNumber(versionDto.getVersionNumber()); // 3设置软件版本号
		version.setFileCheckCode(versionDto.getFileCheckCode()); // 4设置软件校验码
		// 4设置软件下载地址
		String downLoadPath = ("produce".equals(isPro)) ? proDownloadPath : devDownloadPath;
		switch (versionDto.getSoftType()) {
			case 1:
				downLoadPath += "/app/safe/" + submitNewFile.getName();
				break;
			case 2:
				downLoadPath += "/app/normal/" + submitNewFile.getName();
				break;
			case 3:
				downLoadPath += "/firmware/safe/" + submitNewFile.getName();
				break;
			case 4:
				downLoadPath += "/firmware/normal1/" + submitNewFile.getName();
				break;
			case 5:
				downLoadPath += "/firmware/normal3/" + submitNewFile.getName();
				break;
			default:
				break;
		}
		version.setDownloadPath(downLoadPath);
		version.setPublishTime(new Date()); // 5设置软件发布时间
		versionService.addVersion(version); // 插入数据库
		return SUCCESS_TIP;
	}

	/**
	 * 6.删除一条版本记录(根据Id)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/delete")
	@ResponseBody
	public Tip deleteVersion(@RequestParam("versionId") Integer versionId) {
		versionService.deleteVersionById(versionId);
		return SUCCESS_TIP;
	}
}
