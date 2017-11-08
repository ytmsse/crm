package com.cms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 
 * <p>
 * Description: 客户信息管理实体类
 * </p>
 * 
 * @author <a href="mailto: xiebin@gaotai.cn">xiebin</a>
 * @Date：2017-08-09
 * @version $Revision$
 */
@Entity
@Table(name = "cms_customer")
public class CmsCustomer implements java.io.Serializable
{

	private Long id; //

	@Excel(name = "创建日期") private String exportCreateDate;//导出的日期格式

	@Excel(name = "客户名称") private String cusName; //客户名称

	@Excel(name = "性别") private String cusSex; //性别

	@Excel(name = "电话1") private String cusTel1; //电话1

	@Excel(name = "电话2") private String cusTel2; //电话2

	@Excel(name = "备注") private String cusRemark; //备注

	@Excel(name = "发行员") private String sellUserName; //发行员

	@Excel(name = "会期") private String meetingTime; //会期

	@Excel(name = "单价") private String cusPrice; //单价

	@Excel(name = "套数") private String cusSetNumber; //套数

	@Excel(name = "合计金额") private String cusTotalMeony; //合计金额

	@Excel(name = "pass") private String pass; //pass

	private String isExport;//是否已导出

	private String orgCode;//机构编码

	@Excel(name = "分店编号") private String dataOrgCode;// 部门

	private Date createDate; //创建日期

	private String createBy; //创建人登录名称

	private String createName; //创建人名称

	private String updateBy; //更新人登录名称

	private String updateName; //更新人名称

	private Date updateDate; //更新日期

	private String dataOrgCodeName;//部门名称

	public CmsCustomer()
	{}

	/**
	 * 
	 * <p>
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, length = 11)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * 
	 * <p>
	 * 客户名称
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_name", length = 64)
	public String getCusName()
	{
		return cusName;
	}

	public void setCusName(String cusName)
	{
		this.cusName = cusName;
	}

	/**
	 * 
	 * <p>
	 * 性别
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_sex", length = 4)
	public String getCusSex()
	{
		return cusSex;
	}

	public void setCusSex(String cusSex)
	{
		this.cusSex = cusSex;
	}

	/**
	 * 
	 * <p>
	 * 电话1
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_tel_1", length = 32)
	public String getCusTel1()
	{
		return cusTel1;
	}

	public void setCusTel1(String cusTel1)
	{
		this.cusTel1 = cusTel1;
	}

	/**
	 * 
	 * <p>
	 * 电话2
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_tel_2", length = 32)
	public String getCusTel2()
	{
		return cusTel2;
	}

	public void setCusTel2(String cusTel2)
	{
		this.cusTel2 = cusTel2;
	}

	/**
	 * 
	 * <p>
	 * 备注
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_remark", length = 512)
	public String getCusRemark()
	{
		return cusRemark;
	}

	public void setCusRemark(String cusRemark)
	{
		this.cusRemark = cusRemark;
	}

	/**
	 * 
	 * <p>
	 * 销售员
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "sell_user_name", length = 32)
	public String getSellUserName()
	{
		return sellUserName;
	}

	public void setSellUserName(String sellUserName)
	{
		this.sellUserName = sellUserName;
	}

	/**
	 * 
	 * <p>
	 * 会期
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "meeting_time", length = 512)
	public String getMeetingTime()
	{
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime)
	{
		this.meetingTime = meetingTime;
	}

	/**
	 * 
	 * <p>
	 * 单价
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_price", length = 32)
	public String getCusPrice()
	{
		return cusPrice;
	}

	public void setCusPrice(String cusPrice)
	{
		this.cusPrice = cusPrice;
	}

	/**
	 * 
	 * <p>
	 * 套数
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_set_number", length = 32)
	public String getCusSetNumber()
	{
		return cusSetNumber;
	}

	public void setCusSetNumber(String cusSetNumber)
	{
		this.cusSetNumber = cusSetNumber;
	}

	/**
	 * 
	 * <p>
	 * 合计金额
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "cus_total_meony", length = 32)
	public String getCusTotalMeony()
	{
		return cusTotalMeony;
	}

	public void setCusTotalMeony(String cusTotalMeony)
	{
		this.cusTotalMeony = cusTotalMeony;
	}

	/**
	 * 
	 * <p>
	 * pass
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "pass", length = 4)
	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	/**
	 * 
	 * <p>
	 * 创建人登录名称
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "create_by", updatable = false, length = 50)
	public String getCreateBy()
	{
		return createBy;
	}

	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}

	/**
	 * 
	 * <p>
	 * 创建人名称
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "create_name", updatable = false, length = 50)
	public String getCreateName()
	{
		return createName;
	}

	public void setCreateName(String createName)
	{
		this.createName = createName;
	}

	/**
	 * 
	 * <p>
	 * 创建日期
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "create_date", updatable = false, length = 19)
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * 
	 * <p>
	 * 更新人登录名称
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "update_by", length = 50)
	public String getUpdateBy()
	{
		return updateBy;
	}

	public void setUpdateBy(String updateBy)
	{
		this.updateBy = updateBy;
	}

	/**
	 * 
	 * <p>
	 * 更新人名称
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "update_name", length = 50)
	public String getUpdateName()
	{
		return updateName;
	}

	public void setUpdateName(String updateName)
	{
		this.updateName = updateName;
	}

	/**
	 * 
	 * <p>
	 * 更新日期
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-08-09
	 * @update: [updatedate] [changer][change description]
	 */

	@Column(name = "update_date", length = 19)
	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	@Column(name = "org_Code", length = 32)
	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	@Column(name = "data_Org_Code", length = 32)
	public String getDataOrgCode()
	{
		return dataOrgCode;
	}

	public void setDataOrgCode(String dataOrgCode)
	{
		this.dataOrgCode = dataOrgCode;
	}

	@Transient
	public String getDataOrgCodeName()
	{
		return dataOrgCodeName;
	}

	public void setDataOrgCodeName(String dataOrgCodeName)
	{
		this.dataOrgCodeName = dataOrgCodeName;
	}

	@Transient
	public String getExportCreateDate()
	{
		return exportCreateDate;
	}

	public void setExportCreateDate(String exportCreateDate)
	{
		this.exportCreateDate = exportCreateDate;
	}

	@Column(name = "isExport", length = 4)
	public String getIsExport()
	{
		return isExport;
	}

	public void setIsExport(String isExport)
	{
		this.isExport = isExport;
	}

}
