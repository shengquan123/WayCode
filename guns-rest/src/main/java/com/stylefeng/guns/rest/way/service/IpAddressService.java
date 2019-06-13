package com.stylefeng.guns.rest.way.service;

import java.io.File;
import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

/**
 * IP地址服务
 */
@Service
public class IpAddressService {

	private static Logger logger = LoggerFactory.getLogger(IpAddressService.class);

	private static DatabaseReader reader;

	@PostConstruct
	public void init() {
		try {
			File database = ResourceUtils.getFile("classpath:GeoLite2-City.mmdb");
			reader = new DatabaseReader.Builder(database).build();
		} catch (Exception e) {
			logger.error("IP地址服务初始化异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 获取省名和城市名 中间使用"^"符号隔开
	 * 
	 * @param ipAddress
	 * @return
	 */
	public String getSubdivisionAndCity(String ipAddress) {
		try {
			CityResponse response = reader.city(InetAddress.getByName(ipAddress));
			return response.getMostSpecificSubdivision().getNames().get("zh-CN") + "^"
					+ response.getCity().getNames().get("zh-CN");
		} catch (Exception e) {
			logger.error("根据IP[{}]获取省份失败:{}", ipAddress, e.getMessage());
			return null;
		}
	}

	/**
	 * 获取省名
	 * 
	 * @param ipAddress
	 * @return
	 */
	public String getSubdivision(String ipAddress) {
		try {
			CityResponse response = reader.city(InetAddress.getByName(ipAddress));
			return response.getMostSpecificSubdivision().getNames().get("zh-CN");
		} catch (Exception e) {
			logger.error("根据IP[{}]获取省份失败:{}", ipAddress, e.getMessage());
			return null;
		}
	}

	/**
	 * 获取城市名
	 * 
	 * @param ipAddress
	 * @return
	 */
	public String getCity(String ipAddress) {
		try {
			CityResponse response = reader.city(InetAddress.getByName(ipAddress));
			return response.getCity().getNames().get("zh-CN");
		} catch (Exception e) {
			logger.error("根据IP[{}]获取省份失败:{}", ipAddress, e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		// 创建 GeoLite2 数据库
		File database = ResourceUtils.getFile("classpath:GeoLite2-City.mmdb");
		// 读取数据库内容
		DatabaseReader reader = new DatabaseReader.Builder(database).build();
		InetAddress ipAddress = InetAddress.getByName("171.108.233.157");

		// 获取查询结果
		CityResponse response = reader.city(ipAddress);

		// 获取国家信息
		Country country = response.getCountry();
		System.out.println(country.getIsoCode()); // 'CN'
		System.out.println(country.getName()); // 'China'
		System.out.println(country.getNames().get("zh-CN")); // '中国'

		// 获取省份
		Subdivision subdivision = response.getMostSpecificSubdivision();
		System.out.println(subdivision.getName()); // 'Guangxi Zhuangzu Zizhiqu'
		System.out.println(subdivision.getIsoCode()); // '45'
		System.out.println(subdivision.getNames().get("zh-CN")); // '广西壮族自治区'

		// 获取城市
		City city = response.getCity();
		System.out.println(city.getName()); // 'Nanning'
		Postal postal = response.getPostal();
		System.out.println(postal.getCode()); // 'null'
		System.out.println(city.getNames().get("zh-CN")); // '南宁'
		Location location = response.getLocation();
		System.out.println(location.getLatitude()); // 22.8167
		System.out.println(location.getLongitude()); // 108.3167

	}
}