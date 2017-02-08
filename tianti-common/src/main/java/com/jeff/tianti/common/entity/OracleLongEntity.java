package com.jeff.tianti.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
/**
 * Oracle数据库的主键生成定义:系统生成自增长整数型数据作为主键
 * @author Jeff Xu
 * @since 2015-12-09
 */
@MappedSuperclass
public class OracleLongEntity extends IdEntity{
	
	protected Long id;

	@Id
	@SequenceGenerator(name = "ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
