package com.stylefeng.guns.modular.restapi.commans.params;

/**
 * 参数TAG常量类
 * 
 * @author leigang
 *
 */
public class Params {
	
	/**
	 * 传递的参数错误
	 */
	public final static String PARAMS="参数错误";
	/**
	 * 程序中未知异常
	 */
	public final static String EXCEPTION="异常";
	/**
	 * 编码格式
	 */
	public final static String CHARSET = "UTF-8";

	/**
	 * Mapper文件包名
	 */
	public static final String MAPPER_PATH = "cn.tooan.auth.data";
	
	/**
	 * 直达应用类型默认图标
	 */
	public static final String ICON_DEFULT_APP = "";
	public static final String ICON_DEFULT_WEB = "";
	public static final String ICON_DEFULT_FILE = "";
	

	/**
	 * 与鼠标的初始密钥
	 */
	public static final String initDesKey = "usr3hhhh";

	public static final String SUCCESS = "N";

	public static final String FAIL = "E";

	public static final String MSG = "OK";

	/**
	 * 安全芯片ID
	 */
	public final static byte[] TAG_CHIP_ID = { (byte) 0xA0 };
	/**
	 * 随机数
	 */
	public final static byte[] TAG_RAND = { (byte) 0xA1 };
	/**
	 * 工作密钥
	 */
	public final static byte[] TAG_3DES_KEY = { (byte) 0xA2 };
	/**
	 * 用图正证书签名结果
	 */
	public final static byte[] TAG_ROOT_SIGNATURE = { (byte) 0xA3 };
	/**
	 * 用第三方证书签名结果
	 */
	public final static byte[] TAG_USER_SIGNATURE = { (byte) 0xA4 };
	/**
	 * 应用ID
	 */
	public final static byte[] TAG_APP_ID = { (byte) 0xA5 };
	/**
	 * 证书
	 */
	public final static byte[] TAG_CERT = { (byte) 0xA6 };
	/**
	 * 指纹ID
	 */
	public final static byte[] TAG_FINGERPRINT_ID = { (byte) 0xA7 };
	/**
	 * 指纹特征值
	 */
	public final static byte[] TAG_FINGERPRINT_CAHR = { (byte) 0xA8 };
	/**
	 * 公钥
	 */
	public final static byte[] TAG_PUBLIC_KEY = { (byte) 0xA9 };
	//返回KEY
	public static final String RESPONSESTATUS = "responseStatus";
	public static final String RESPONSECODE = "responseCode";
	public static final String RESPONSEMESSAGE = "responseMessage";
	public static final String RESPONSEBODY="responsebody";
	
}
