package com.cms.service.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.entity.CmsCustomer;
import com.cms.service.ICmsCustomerService;

/**
 * 
 * <p>
 * Description: 客户信息管理Service接口
 * </p>
 * 
 * @author <a href="mailto: xiebin@gaotai.cn">xiebin</a>
 * @Date：2017-04-25
 * @version $Revision$
 */

@Service("CmsCustomerService")
@Transactional
public class CmsCustomerService extends CommonServiceImpl implements ICmsCustomerService
{

	@Override
	public CmsCustomer doSave(CmsCustomer entity)
	{
		if(StringUtil.isEmpty(entity.getOrgCode()))
		{
			entity.setOrgCode(ResourceUtil.getSessionUserName().getOrgCode());
		}
		if(StringUtil.isEmpty(entity.getDataOrgCode()))
		{
			entity.setDataOrgCode(ResourceUtil.getSessionUserName().getDataOrgCode());
		}
		Serializable id = super.save(entity);
		entity.setId(Long.parseLong(id.toString()));
		return entity;
	}

	@Override
	public void doDeleteById(String ids)
	{
		String str[] = ids.split(",");
		for (String id : str)
		{
			super.deleteEntityById(CmsCustomer.class, Long.parseLong(id));

		}

	}

	@Override
	public CmsCustomer doGetById(Serializable id)
	{
		return super.get(CmsCustomer.class, id);
	}

	@Override
	public CmsCustomer doGetByPropertyName(String propertyName, String value)
	{
		return super.findUniqueByProperty(CmsCustomer.class, propertyName, value);
	}

	@Override
	public Long doGetCountBySql(String sql)
	{
		return super.getCountForJdbc(sql);
	}

	@Override
	public List<CmsCustomer> doGetListPropertyName(String propertyName, String value, boolean isAsc)
	{
		return super.findByPropertyisOrder(CmsCustomer.class, propertyName, value, isAsc);
	}

	@Override
	public Integer doExceuteBySql(String sql, Object... param)
	{
		return super.executeSql(sql, param);
	}

	@Override
	public Integer doExceuteBySql(String sql, Map<String, Object> param)
	{
		return super.executeSql(sql, param);
	}

	@Override
	public Object doExceuteBySqlReturnKey(String sql, Map<String, Object> param)
	{
		return super.executeSqlReturnKey(sql, param);
	}

	@Override
	public List<Map<String, Object>> doGetListForJdbc(String sql, Object... objs)
	{
		return super.findForJdbc(sql, objs);
	}

	@Override
	public Map<String, Object> doGetListOneForJdbc(String sql, Object... objs)
	{
		return super.findOneForJdbc(sql, objs);
	}

	@Override
	public List<Map<String, Object>> doGetPageListForJdbc(String sql, int page, int rows)
	{
		return super.findForJdbc(sql, page, rows);
	}

	@Override
	public List<CmsCustomer> doGetPageListForJdbcList(String sql, int page, int rows)
	{
		return super.findObjForJdbc(sql, page, rows, CmsCustomer.class);
	}

	@Override
	public List<CmsCustomer> doGetListByClass()
	{
		return super.getList(CmsCustomer.class);
	}

	@Override
	public void doPassCustomer(String ids, String pass)
	{
		String idsSql = "";
		String[] id = ids.split(",");
		for (String string : id)
		{
			if(idsSql != null && !"".equals(idsSql))
			{
				idsSql = idsSql + "," + string;
			}
			else
			{
				idsSql = string;
			}
		}
		String sql = "update cms_customer set pass='" + pass + "' where id in (" + idsSql + ")";
		super.updateBySqlString(sql);
	}

	@Override
	public Long isExistTel(String tel, Long id)
	{

		String sql = "";
		if(id == null)
		{

			sql = "select count(*) from cms_customer where (cus_tel_1='" + tel + "' or cus_tel_2='" + tel + "')";
		}
		else
		{
			sql = "select count(*) from cms_customer where (cus_tel_1='" + tel + "' or cus_tel_2='" + tel
					+ "') and id <>" + id;
		}
		return super.getCountForJdbc(sql);
	}

	@Override
	public int updateRemarkSave(String cusRemarkEdit, Long id)
	{
		String dataOrgCode = "";
		String sqlQuery = "";
		CmsCustomer entity = super.getEntity(CmsCustomer.class, id);
		if(StringUtil.isNotEmpty(ResourceUtil.getSessionUserName().getDataOrgCode()))
		{
			dataOrgCode = ResourceUtil.getSessionUserName().getDataOrgCode();
			sqlQuery = "select id from cms_customer  where cus_remark='" + entity.getCusRemark()
					+ "' and data_Org_Code='" + dataOrgCode + "'";
		}
		else
		{
			sqlQuery = "select id from cms_customer  where cus_remark='" + entity.getCusRemark() + "'";
		}
		String ids = "";
		List<Map<String, Object>> list = super.findForJdbc(sqlQuery);
		for (Map<String, Object> map : list)
		{
			if(StringUtil.isEmpty(ids))
			{
				ids = map.get("id").toString();
			}
			else
			{
				ids = ids + "," + map.get("id").toString();
			}
		}

		String sql = "update cms_customer set cus_remark='" + cusRemarkEdit + "' where id in (" + ids + ")";
		return super.updateBySqlString(sql);
	}

	@Override
	public void batchSaveCustomer(List<CmsCustomer> customerList)
	{
		super.batchSave(customerList);
	}

	@Override
	public void updateisExport(String ids)
	{
		String sql = "update cms_customer set isExport='Y' where id in(" + ids + ")";
		super.updateBySqlString(sql);

	}

}