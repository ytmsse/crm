package com.cms.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cms.entity.CmsCustomer;
import com.cms.excelverify.CustomerExcelVerifyHandler;
import com.cms.service.ICmsCustomerService;
import com.cms.util.Constant;

/**
 * 
 * <p>
 * Description: 客户信息管理
 * </p>
 * 
 * @author <a href="mailto: xiebin@54604344@qq.com">xiebin</a>
 * @Date：2017-08-09
 * @version $Revision$
 */
@Controller
@RequestMapping("/cmsCustomerController")
public class CmsCustomerController extends BaseController
{
	@Resource private ICmsCustomerService cmsCustomerService;

	@Resource private SystemService systemService;

	/**
	 * 
	 * <p>
	 * 跳转到列表页面
	 * </p>
	 * 
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "list")
	public String doList(Model model)
	{
		List<TSDepart> tsList = new ArrayList<TSDepart>();
		if(ResourceUtil.getSessionUserName().getRoleCode().equals(Constant.USER_ROLE_TYPE_ADMIN))
		{
			List<Map<String, Object>> mapList = systemService.findForJdbc(
				"select departname,org_code from t_s_depart where org_type=2", null);
			for (Map<String, Object> map : mapList)
			{
				TSDepart domain = new TSDepart();
				domain.setDepartname(map.get("departname").toString());
				domain.setOrgCode(map.get("org_code").toString());
				tsList.add(domain);
			}
		}
		model.addAttribute("tsList", tsList);
		return "com/cms/cmsCustomer/cmsCustomerList";
	}

	/**
	 * 
	 * <p>
	 * 列表展示页面
	 * </p>
	 * 
	 * @param tBRoad
	 * @param dataGrid
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(CmsCustomer cmsCustomer, DataGrid dataGrid, HttpServletResponse response)
	{

		String sql = "";

		CriteriaQuery cq = new CriteriaQuery(CmsCustomer.class, dataGrid);
		if(!ResourceUtil.getSessionUserName().getRoleCode().equals(Constant.USER_ROLE_TYPE_ADMIN))
		{
			cq.eq("dataOrgCode", ResourceUtil.getSessionUserName().getDataOrgCode());
			sql = " and data_Org_Code='" + ResourceUtil.getSessionUserName().getDataOrgCode() + "'";
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cmsCustomer, getRequest()
				.getParameterMap());
		try
		{
			//自定义追加查询条件
		}
		catch (Exception e)
		{
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		DataGridReturn r = this.cmsCustomerService.getDataGridReturn(cq, true);
		List<CmsCustomer> cusList = r.getRows();
		for (CmsCustomer cmsCustomer2 : cusList)
		{
			TSDepart domain = systemService.findUniqueByProperty(TSDepart.class, "orgCode",
				cmsCustomer2.getDataOrgCode());
			if(domain != null)
			{
				cmsCustomer2.setDataOrgCodeName(domain.getDepartname());
			}
		}
		Long count = cmsCustomerService
				.getCountForJdbc("select count(*) from cms_customer where DATE_FORMAT(create_date,'%Y-%m-%d')='"
						+ DateUtils.formatDate(new Date()) + "' " + sql);
		dataGrid.setFooter("cusName:今日新增：" + count + "条");
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 
	 * <p>
	 * 批量删除
	 * </p>
	 * 
	 * @param ids
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids)
	{
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户信息管理删除成功";
		try
		{
			cmsCustomerService.doDeleteById(ids);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "客户信息管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 
	 * <p>
	 * 跳转到新增页面
	 * </p>
	 * 
	 * @param model
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "add")
	public String doAdd(Model model)
	{

		List<TSDepart> tsList = new ArrayList<TSDepart>();
		if(ResourceUtil.getSessionUserName().getRoleCode().equals(Constant.USER_ROLE_TYPE_ADMIN))
		{
			List<Map<String, Object>> mapList = systemService.findForJdbc(
				"select departname,org_code from t_s_depart where org_type=2", null);
			for (Map<String, Object> map : mapList)
			{
				TSDepart domain = new TSDepart();
				domain.setDepartname(map.get("departname").toString());
				domain.setOrgCode(map.get("org_code").toString());
				tsList.add(domain);
			}
		}
		else
		{
			List<Map<String, Object>> mapList = systemService.findForJdbc(
				"select departname,org_code from t_s_depart where org_type=2 and org_code='"
						+ ResourceUtil.getSessionUserName().getDataOrgCode() + "'", null);
			for (Map<String, Object> map : mapList)
			{
				TSDepart domain = new TSDepart();
				domain.setDepartname(map.get("departname").toString());
				domain.setOrgCode(map.get("org_code").toString());
				tsList.add(domain);
			}
		}
		model.addAttribute("tsList", tsList);
		return "com/cms/cmsCustomer/cmsCustomerAddEdit";
	}

	/**
	 * 
	 * <p>
	 * 单条数据保存
	 * </p>
	 * 
	 * @param cmsCustomer
	 * @param request
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson doSave(CmsCustomer cmsCustomer, HttpServletRequest request)
	{
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户信息管理操作成功";
		try
		{
			cmsCustomerService.doSave(cmsCustomer);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "客户信息管理操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 
	 * <p>
	 * 获取单条数据
	 * </p>
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "get")
	public String doGet(Model model, Long id)
	{
		if(StringUtil.isNotEmpty(id))
		{
			CmsCustomer cmsCustomer = cmsCustomerService.doGetById(id);
			model.addAttribute("cmsCustomer", cmsCustomer);
		}
		List<TSDepart> tsList = new ArrayList<TSDepart>();
		if(ResourceUtil.getSessionUserName().getRoleCode().equals(Constant.USER_ROLE_TYPE_ADMIN))
		{
			List<Map<String, Object>> mapList = systemService.findForJdbc(
				"select departname,org_code from t_s_depart where org_type=2", null);
			for (Map<String, Object> map : mapList)
			{
				TSDepart domain = new TSDepart();
				domain.setDepartname(map.get("departname").toString());
				domain.setOrgCode(map.get("org_code").toString());
				tsList.add(domain);
			}
		}
		else
		{
			List<Map<String, Object>> mapList = systemService.findForJdbc(
				"select departname,org_code from t_s_depart where org_type=2 and org_code='"
						+ ResourceUtil.getSessionUserName().getDataOrgCode() + "'", null);
			for (Map<String, Object> map : mapList)
			{
				TSDepart domain = new TSDepart();
				domain.setDepartname(map.get("departname").toString());
				domain.setOrgCode(map.get("org_code").toString());
				tsList.add(domain);
			}
		}
		model.addAttribute("tsList", tsList);
		return "com/cms/cmsCustomer/cmsCustomerAddEdit";
	}

	/**
	 * 
	 * <p>
	 * 根据ID pass数据
	 * </p>
	 * 
	 * @param model
	 * @param ids
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "passCustomer")
	@ResponseBody
	public AjaxJson doPassCustomer(Model model, String ids, String pass)
	{
		AjaxJson j = new AjaxJson();
		if(ids != null)
		{
			cmsCustomerService.doPassCustomer(ids, pass);
		}
		return j;
	}

	/**
	 * 
	 * <p>
	 * 判断电话是否存在
	 * </p>
	 * 
	 * @param model
	 * @param ids
	 * @param pass
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "isExistTel")
	@ResponseBody
	public AjaxJson doIsExistTel(Model model, String tel, Long id)
	{
		AjaxJson j = new AjaxJson();
		try
		{
			Long n = cmsCustomerService.isExistTel(tel, id);
			j.setObj(n);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			j.setSuccess(false);
			e.printStackTrace();
			return j;
		}
		return j;
	}

	/**
	 * 
	 * <p>
	 * 导出EXCEL
	 * </p>
	 * 
	 * @param cmsCustomer
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param modelMap
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(CmsCustomer cmsCustomer, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap)
	{
		CriteriaQuery cq = new CriteriaQuery(CmsCustomer.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cmsCustomer, request.getParameterMap());
		cq.eq("pass", Constant.CUSTOMER_PASS_N);
		if(!ResourceUtil.getSessionUserName().getRoleCode().equals(Constant.USER_ROLE_TYPE_ADMIN))
		{
			cq.eq("dataOrgCode", ResourceUtil.getSessionUserName().getDataOrgCode());
		}
		cq.add();
		List<CmsCustomer> custList = this.cmsCustomerService.getListByCriteriaQuery(cq, false);

		String ids = "";
		for (CmsCustomer cmsCustomer2 : custList)
		{
			if(StringUtil.isEmpty(ids))
			{
				ids = cmsCustomer2.getId().toString();
			}
			else
			{
				ids = ids + "," + cmsCustomer2.getId().toString();
			}
		}
		if(!StringUtil.isEmpty(ids))
		{
			systemService.updateBySqlString("update cms_customer set isExport='Y' where id in(" + ids + ")");
		}
		for (CmsCustomer cmsCustomer2 : custList)
		{
			if(cmsCustomer2.getCusSex().equals(Constant.SEX_NAN))
			{
				cmsCustomer2.setCusSex("男");
			}
			else
			{
				cmsCustomer2.setCusSex("女");
			}
			if(cmsCustomer2.getPass().equals(Constant.CUSTOMER_PASS_Y))
			{
				cmsCustomer2.setPass("是");
			}
			else
			{
				cmsCustomer2.setPass("否");
			}
			cmsCustomer2.setExportCreateDate(DateUtils.formatDate(cmsCustomer2.getCreateDate()));
		}
		modelMap.put(NormalExcelConstants.FILE_NAME, "客户信息");
		modelMap.put(NormalExcelConstants.CLASS, CmsCustomer.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("客户信息列表", "导出人:"
				+ ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, custList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 
	 * <p>
	 * 导入模板下载
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param modelMap
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "importXlsT")
	public String importXlsT(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			ModelMap modelMap)
	{
		modelMap.put(TemplateExcelConstants.FILE_NAME, "客户信息导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
		templateExportParams.setTemplateUrl("export/template/importTemp_customer.xls");
		modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
		modelMap.put(TemplateExcelConstants.MAP_DATA, new HashMap<String, Object>());
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req)
	{
		req.setAttribute("controller_name", "cmsCustomerController");
		req.setAttribute("importfun_name", "importExcelCustomer");
		return new ModelAndView("common/upload/pub_excel_import");
	}

	/**
	 * 
	 * <p>
	 * 导入客户信息
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author:xiebin 2017-8-10
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "importExcelCustomer", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcelCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		AjaxJson j = new AjaxJson();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet())
		{
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(false);
			//	params.setDataHanlder(new CustomerExcelVerifyHandler());
			params.setNeedVerfiy(true);
			params.setVerifyHanlder(new CustomerExcelVerifyHandler());
			try
			{
				ExcelImportResult<CmsCustomer> result = ExcelImportUtil.importExcelVerify(file.getInputStream(),
					CmsCustomer.class, params);
				if(result.isVerfiyFail())
				{
					String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
					String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/"
							+ uploadpathtemp + "/";// 文件的硬盘真实路径
					File fileTemp = new File(realPath);
					if(!fileTemp.exists())
					{
						fileTemp.mkdirs();// 创建根目录
					}
					String name = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + ".xls";
					realPath += name;
					FileOutputStream fos = new FileOutputStream(realPath);
					result.getWorkbook().write(fos);
					fos.close();
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("path", uploadpathtemp + "/" + name);
					j.setAttributes(attributes);
					j.setSuccess(false);
					j.setMsg("导入数据校验失败");
				}
				else
				{
					List<CmsCustomer> customerList = new ArrayList<CmsCustomer>();
					for (int i = 0; i < result.getList().size(); i++)
					{
						CmsCustomer tbLightEntity = (CmsCustomer) result.getList().get(i);
						tbLightEntity.setOrgCode(tbLightEntity.getDataOrgCode().substring(0, 3));
						tbLightEntity.setPass(Constant.CUSTOMER_PASS_N);
						tbLightEntity.setCreateDate(new Date());
						tbLightEntity.setDataOrgCode(tbLightEntity.getDataOrgCode().trim());
						tbLightEntity.setCusName(tbLightEntity.getCusName().trim());
						tbLightEntity.setCusSex(tbLightEntity.getCusSex().trim());
						tbLightEntity.setSellUserName(tbLightEntity.getSellUserName().trim());
						customerList.add(tbLightEntity);
						//cmsCustomerService.save(tbLightEntity);
					}
					cmsCustomerService.batchSaveCustomer(customerList);
					j.setMsg("文件导入成功！");
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
				j.setMsg("文件导入失败！");
				//logger.error(ExceptionUtil.getExceptionMessage(e));
			}
			finally
			{
				try
				{
					file.getInputStream().close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 
	 * <p>
	 * 获取单条数据
	 * </p>
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @author:xiebin 2017-4-26
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "updateRemark")
	public String doUpdateRemark(Model model, Long id)
	{
		if(StringUtil.isNotEmpty(id))
		{
			CmsCustomer cmsCustomer = cmsCustomerService.doGetById(id);
			model.addAttribute("cmsCustomer", cmsCustomer);
		}
		return "com/cms/cmsCustomer/updateRemark";
	}

	/**
	 * 
	 * <p>
	 * 批量修改备注
	 * </p>
	 * 
	 * @param model
	 * @param id
	 * @param cusRemarkEdit
	 * @return
	 * @author:xiebin 2017-8-12
	 * @update: [updatedate] [changer][change description]
	 */
	@RequestMapping(params = "updateRemarkSave")
	@ResponseBody
	public AjaxJson updateRemarkSave(Model model, Long id, String cusRemarkEdit)
	{
		AjaxJson j = new AjaxJson();
		try
		{
			cmsCustomerService.updateRemarkSave(cusRemarkEdit, id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}

}
