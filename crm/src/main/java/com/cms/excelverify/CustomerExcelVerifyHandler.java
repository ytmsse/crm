package com.cms.excelverify;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import com.cms.entity.CmsCustomer;
import com.cms.service.ICmsCustomerService;

public class CustomerExcelVerifyHandler implements IExcelVerifyHandler<CmsCustomer>
{

	List<String> phone = new ArrayList<String>();

	private ICmsCustomerService getCmsCustomerService()
	{
		return (ICmsCustomerService) ApplicationContextUtil.getContext().getBean("CmsCustomerService");
	}

	@Override
	public ExcelVerifyHanlderResult verifyHandler(CmsCustomer domain)
	{

		boolean success = true;
		StringBuilder builder = new StringBuilder();

		if(StringUtil.isEmpty(domain.getCusName()))
		{
			builder.append("客户名称不能为空");
		}
		else if(domain.getCusName().trim().length() > 20)
		{
			builder.append("客户名称不能超过30个字符");
		}

		if(StringUtil.isEmpty(domain.getCusSex()))
		{
			builder.append("性别不能为空");
		}
		else if(domain.getCusSex().trim().length() > 2)
		{
			builder.append("性别输入有误！");
		}
		else
		{
			if(domain.getCusSex().trim().equals("男"))
			{
				domain.setCusSex("0");
			}
			else if(domain.getCusSex().trim().equals("女"))
			{
				domain.setCusSex("1");
			}
			else
			{
				builder.append("性别输入有误！");
			}
		}

		if(StringUtil.isEmpty(domain.getCusTel1()) && StringUtil.isEmpty(domain.getCusTel2()))
		{
			builder.append("电话1、电话2不能同时为空");
		}

		if(!StringUtil.isEmpty(domain.getCusTel1()))
		{
			if(domain.getCusTel1().trim().length() != 11)
			{
				builder.append("电话1长度必须为11位！");
			}
		}
		if(!StringUtil.isEmpty(domain.getCusTel1()))
		{
			Long telCount1 = getCmsCustomerService().isExistTel(domain.getCusTel1(), null);
			if(telCount1 > 0)
			{
				builder.append("电话1已存在");
			}
			if(!phone.contains(domain.getCusTel1()))
			{
				phone.add(domain.getCusTel1().trim());
			}
			else
			{
				builder.append("电话重复");
			}
		}
		if(!StringUtil.isEmpty(domain.getCusTel2()))
		{

			Long telCount2 = getCmsCustomerService().isExistTel(domain.getCusTel2(), null);
			if(telCount2 > 0)
			{
				builder.append("电话2已存在");
			}
			if(!phone.contains(domain.getCusTel2()))
			{
				phone.add(domain.getCusTel2().trim());
			}
			else
			{
				builder.append("电话重复");
			}

		}

		if(StringUtil.isEmpty(domain.getSellUserName()))
		{
			builder.append("发行员不能为空");
		}
		else if(domain.getSellUserName().trim().length() > 20)
		{
			builder.append("发行员不能超过20个字符");
		}

		if(StringUtil.isEmpty(domain.getDataOrgCode()))
		{
			builder.append("分店编号不能为空");
		}

		if(StringUtil.isNotEmpty(builder.toString()))
		{
			success = false;
		}

		return new ExcelVerifyHanlderResult(success, builder.toString());
	}
}
