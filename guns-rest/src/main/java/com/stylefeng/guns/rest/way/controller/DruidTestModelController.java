package com.stylefeng.guns.rest.way.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.rest.common.persistence.dao.DruidTestModelMapper;
import com.stylefeng.guns.rest.common.persistence.model.DruidTestModel;
import com.stylefeng.guns.rest.way.model.ResponseModel;

@Controller
@RequestMapping("testDruid/")
public class DruidTestModelController {

	private static final Logger LOG = LoggerFactory.getLogger(DruidTestModelController.class);
	
	@Autowired
	private DruidTestModelMapper druidTestModelMapper;
	
	
	@RequestMapping("selectAll")
	@ResponseBody
	public ResponseEntity<?> selectAll() {
		ResponseModel responseModel = new ResponseModel();
		
		Integer selectCount = druidTestModelMapper.selectCount(null);
		LOG.info("查询全部");
		responseModel.setResponseMessage("查询结果：" + selectCount);
		return ResponseEntity.ok(responseModel);
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public ResponseEntity<?> insert() {
		ResponseModel responseModel = new ResponseModel();
		
		DruidTestModel druidTestModel = new DruidTestModel();
		druidTestModel.setValue(String.valueOf((int)((Math.random()*9+1)*100000)));
		Integer insert = druidTestModelMapper.insert(druidTestModel);
		responseModel.setResponseMessage("插入结果：" + insert);
		LOG.info("插入一条记录");
		return ResponseEntity.ok(responseModel);
	}
	
}
