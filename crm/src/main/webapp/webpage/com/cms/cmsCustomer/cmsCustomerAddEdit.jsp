<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
 <head>
  <title>test</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  
  //判断电话是否存在
  function isExistTelSub(tel){
	  var flag=true;
	  var tel1=$("#cusTel1").val();
	  var tel2=$("#cusTel2").val();
	  var id=$("#id").val();
	  if((tel1==null||tel1=='')&&(tel2==null||tel2=='')){
		  $("#cusTel1_span").addClass("Validform_checktip Validform_wrong").html("电话不能同时为空");
		  flag=false;
	  }else{
		  $("#cusTel1_span").removeClass("Validform_checktip Validform_wrong");
		  $("#cusTel1_span").addClass("Validform_checktip Validform_right").html("通过验证");
	  }
	  
	  if(tel1!=null&&tel1!=''){
		  
		  if(tel1.length!=11){
			  $("#cusTel1_span").addClass("Validform_checktip Validform_wrong").html("电话1必须为11位");
			  flag=false;
			  return flag;
		  }else{
			  $("#cusTel1_span").removeClass("Validform_checktip Validform_wrong");
			  $("#cusTel1_span").addClass("Validform_checktip Validform_right").html("通过验证");
		  }
		  
		  $.ajax({
			  url:'cmsCustomerController.do?isExistTel&tel='+tel1,
			  async: false,
			  data:{id:id},
			  success:function(data){
				  	var d = $.parseJSON(data);
	 				if(d.success){
	 					if(d.obj>0){
	 						$("#cusTel1_span").addClass("Validform_checktip Validform_wrong").html("电话已存在");
	 						flag=false;
	 					}else{
	 						$("#cusTel1_span").addClass("Validform_checktip Validform_right").html("通过验证");
	 					}
	 				}else{
	 					tip("失败...");
	 				}
			  }
		  });
	  }
	  if(tel2!=null&&tel2!=''){
		  $.ajax({
			  url:'cmsCustomerController.do?isExistTel&tel='+tel2,
			  async: false,
			  data:{id:id},
			  success:function(data){
				  	var d = $.parseJSON(data);
	 				if(d.success){
	 					if(d.obj>0){
	 						$("#cusTel2_span").addClass("Validform_checktip Validform_wrong").html("电话已存在");
	 						flag=false;
	 					}else{
	 						$("#cusTel2_span").addClass("Validform_checktip Validform_right").html("通过验证");
	 					}
	 				}else{
	 					tip("失败...");
	 				}
			  }
		  });
	  }
	  return flag;
  }
  
  
  
  </script>
 </head>
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cmsCustomerController.do?save" beforeSubmit="isExistTelSub" >
		<input id="id" name="id" type="hidden" value="${cmsCustomer.id}">
		<input id="pass" name="pass" type="hidden" value="${cmsCustomer.pass==null||cmsCustomer.pass==''?'N':cmsCustomer.pass}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="20%" nowrap="nowrap" >
							<label class="Validform_label">
							<span style="color: red">*</span>
								所属部门:
							</label>
						</td>
						<td class="value">
						   <select id="dataOrgCode"  name="dataOrgCode" >
						   	<c:forEach items="${tsList}" var="c" >
						   		<option value="${c.orgCode}" ${cmsCustomer.dataOrgCode==c.orgCode?'selected':''} >${c.departname}</option>
						   	</c:forEach>
						   </select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
		
					<tr>
						<td align="right" width="20%" nowrap="nowrap" >
							<label class="Validform_label">
								<span style="color: red">*</span>
								<t:mutiLang langKey="cmsCustomer.cusName"/>:
							</label>
						</td>
						<td class="value">
						     <input id="cusName" name="cusName" type="text" style="width: 150px" class="inputxt" datatype="*" maxlength="32"  value='${cmsCustomer.cusName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
							<span style="color: red">*</span>
							<t:mutiLang langKey="cmsCustomer.cusSex"/>:
							</label>
						</td>
						<td class="value">
							 <t:dictSelect field="cusSex" type="radio" extendJson="{datatype:*}" typeGroupCode="sex" defaultVal="${cmsCustomer.cusSex==null||cmsCustomer.cusSex==''?'0':cmsCustomer.cusSex}" hasLabel="false" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.cusTel1"/>:
							</label>
						</td>
						<td class="value">
							<input id="cusTel1" name="cusTel1" type="text" style="width: 150px"   class="inputxt"  maxlength="11" value='${cmsCustomer.cusTel1}'>
							<span class="Validform_checktip" id="cusTel1_span" ></span>
							<label class="Validform_label"  style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.cusTel2"/>:
							</label>
						</td>
						<td class="value">
						     	 <input id="cusTel2" name="cusTel2" type="text" style="width: 150px" class="inputxt"  maxlength="16"   value='${cmsCustomer.cusTel2}'>
							<span class="Validform_checktip" id="cusTel2_span" ></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<span style="color: red">*</span>
								<t:mutiLang langKey="cmsCustomer.sellUserName"/>:
							</label>
						</td>
						<td class="value">
						     	 <input id="sellUserName" name="sellUserName" type="text" style="width: 150px" class="inputxt" datatype="*" maxlength="16" value='${cmsCustomer.sellUserName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.meetingTime"/>:
							</label>
						</td>
						<td class="value">
						     	 <input id="meetingTime" name="meetingTime" type="text" style="width: 300px" class="inputxt" maxlength="50"  value='${cmsCustomer.meetingTime}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.cusPrice"/>:
							</label>
						</td>
						<td class="value">
						     	 <input id="cusPrice" name="cusPrice" type="text" style="width: 150px"  maxlength="32" class="inputxt"  value='${cmsCustomer.cusPrice}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.cusSetNumber"/>:
							</label>
						</td>
						<td class="value">
						     	 <input id="cusSetNumber" name="cusSetNumber" type="text" style="width: 150px" class="inputxt" maxlength="32"  value='${cmsCustomer.cusSetNumber}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.cusTotalMeony"/>:
							</label>
						</td>
						<td class="value">
						     	 <input id="cusTotalMeony" name="cusTotalMeony" type="text" style="width: 150px" class="inputxt"  maxlength="32" value='${cmsCustomer.cusTotalMeony}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<t:mutiLang langKey="cmsCustomer.cusRemark"/>:
							</label>
						</td>
						<td class="value">
						<input id="cusRemark" name="cusRemark" type="text" style="width: 150px" class="inputxt"  maxlength="20" value='${cmsCustomer.cusRemark}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					
			</table>
</t:formvalid>
 </body>
