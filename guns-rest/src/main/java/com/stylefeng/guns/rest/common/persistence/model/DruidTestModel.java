package com.stylefeng.guns.rest.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 测试数据连接池实体类
 * 
 * @author shengquan
 * @since 2019-01-23
 */
@TableName("test")
public class DruidTestModel extends Model<DruidTestModel> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField("value")
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DruidTestModel [id=" + id + ", value=" + value + "]";
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
