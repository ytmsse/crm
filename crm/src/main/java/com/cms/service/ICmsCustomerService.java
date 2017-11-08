package com.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.cms.entity.CmsCustomer;

/**
 * 
 * <p>
 * Description: 客户信息管理接口
 * </p>
 * 
 * @author <a href="mailto: xiebin@gaotai.cn">xiebin</a>
 * @Date：2017-04-25
 * @version $Revision$
 */

public interface ICmsCustomerService extends CommonService
{
	/**
	 * 
	 * <p>
	 * 保存方法
	 * </p>
	 * 
	 * @param entity
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public CmsCustomer doSave(CmsCustomer entity);

	/**
	 * 
	 * <p>
	 * 删除方法
	 * </p>
	 * 
	 * @param entity
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public void doDeleteById(String ids);

	/**
	 * 
	 * <p>
	 * 根据ID查询对象
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public CmsCustomer doGetById(Serializable id);

	/**
	 * 
	 * <p>
	 * 根据属性获取对象
	 * </p>
	 * 
	 * @param cls
	 * @param propertyName
	 * @param value
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public CmsCustomer doGetByPropertyName(String propertyName, String value);

	/**
	 * 
	 * <p>
	 * 根据SQL查询表的总数据条数
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public Long doGetCountBySql(String sql);

	/**
	 * 
	 * <p>
	 * 根据属性值返回排序的LIST集合
	 * </p>
	 * 
	 * @param propertyName
	 *            ---属性名
	 * @param value
	 *            ---值
	 * @param isAsc
	 *            -----是否升序
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public List<CmsCustomer> doGetListPropertyName(String propertyName, String value, boolean isAsc);

	/**
	 * 
	 * <p>
	 * 根据
	 * </p>
	 * 
	 * @param sql
	 * @param param
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public Integer doExceuteBySql(String sql, Object... param);

	/**
	 * 
	 * <p>
	 * 执行SQL 使用:name占位符
	 * </p>
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public Integer doExceuteBySql(String sql, Map<String, Object> param);

	/**
	 * 
	 * <p>
	 * 执行SQL 使用:name占位符,并返回插入的主键值
	 * </p>
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public Object doExceuteBySqlReturnKey(String sql, Map<String, Object> param);

	/**
	 * 
	 * <p>
	 * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
	 * </p>
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public List<Map<String, Object>> doGetListForJdbc(String sql, Object... objs);

	/**
	 * 
	 * <p>
	 * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
	 * </p>
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public Map<String, Object> doGetListOneForJdbc(String sql, Object... objs);

	/**
	 * 
	 * <p>
	 * 通过JDBC查找对象集合,带分页 使用指定的检索标准检索数据并分页返回数据
	 * </p>
	 * 
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public List<Map<String, Object>> doGetPageListForJdbc(String sql, int page, int rows);

	/**
	 * 
	 * <p>
	 * 通过JDBC查找对象集合,带分页 使用指定的检索标准检索数据并分页返回数据
	 * </p>
	 * 
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public List<CmsCustomer> doGetPageListForJdbcList(String sql, int page, int rows);

	/**
	 * 
	 * <p>
	 * 返回对象LIST
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	public List<CmsCustomer> doGetListByClass();

	/**
	 * <p>
	 * 根据ID pass数据
	 * </p>
	 * 
	 * @param ids
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	public void doPassCustomer(String ids, String pass);

	/**
	 * 
	 * <p>
	 * 判断电话是否存在
	 * </p>
	 * 
	 * @param tel
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	public Long isExistTel(String tel, Long id);

	/**
	 * 
	 * <p>
	 * 批量修改备注
	 * </p>
	 * 
	 * @param cusRemarkEdit
	 * @param id
	 * @return
	 * @author:xiebin 2017-8-12
	 * @update: [updatedate] [changer][change description]
	 */
	public int updateRemarkSave(String cusRemarkEdit, Long id);

	/**
	 * 
	 * <p>
	 * 批量保存
	 * </p>
	 * 
	 * @param customerList
	 * @author:Administrator 2017-8-13
	 * @update: [updatedate] [changer][change description]
	 */
	public void batchSaveCustomer(List<CmsCustomer> customerList);

	/**
	 * 
	 * <p>
	 * 修改导出状态
	 * </p>
	 * 
	 * @param ids
	 * @author:xiebin 2017-8-16
	 * @update: [updatedate] [changer][change description]
	 */
	public void updateisExport(String ids);

}