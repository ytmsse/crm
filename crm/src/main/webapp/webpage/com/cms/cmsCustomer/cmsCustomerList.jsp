<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<%
 String userRole=ResourceUtil.getSessionUserName().getRoleCode();
 request.setAttribute("userRole", userRole);
 %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    $(function() {
        var datagrid = $("#cmsCustomerListtb");
		datagrid.find("div[name='searchColums']").find("form#cmsCustomerListForm").append($("#realNameSearchColums div[name='searchColumsRealName']").html());
		$("#realNameSearchColums").html('');
	});
</script>
<div id="realNameSearchColums" style="display: none;style="margin-left: 20px;"">
	<div name="searchColumsRealName"  >
	<c:if test="${userRole==1}">
		部门： 
		<select id="dataOrgCode"  name="dataOrgCode" >
			<option value="" >--请选择--</option>
		   	<c:forEach items="${tsList}" var="c" >
		   		<option value="${c.orgCode}" >${c.departname}</option>
		   	</c:forEach>
		</select>
	</c:if>
	</div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="cmsCustomerList" checkbox="true" fitColumns="false" title="客户信息管理" actionUrl="cmsCustomerController.do?datagrid" idField="id" pageSize="20" fit="true" queryMode="group">
   <t:dgCol title="PK"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.createDate"  field="createDate"  query="true"    formatter="yyyy-MM-dd" queryMode="group"  width="80"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusName"  field="cusName"  query="true"   queryMode="single"  width="150"></t:dgCol>
  		 <t:dgCol title="分店"  field="dataOrgCodeName"    queryMode="single"  width="100"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusSex"  field="cusSex"  dictionary="sex"  queryMode="single"  width="50"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusTel1"  field="cusTel1" query="true"   queryMode="single"  width="100"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusTel2"  field="cusTel2"  query="true"  queryMode="single"  width="100"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.sellUserName"  field="sellUserName" query="true"   queryMode="single"  width="80"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusRemark"  field="cusRemark" query="true"   queryMode="single"  width="80"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.pass"  field="pass"    queryMode="single" dictionary="sf_yn"  width="50"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.meetingTime"  field="meetingTime"    queryMode="single"  width="200"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusPrice"  field="cusPrice"    queryMode="single"  width="120"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusSetNumber"  field="cusSetNumber"    queryMode="single"  width="50"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.cusTotalMeony"  field="cusTotalMeony"    queryMode="single"  width="120"></t:dgCol>
  		 <t:dgCol title="cmsCustomer.createName"  field="createName"    queryMode="single"  width="80"></t:dgCol>
  		 <t:dgCol title="是否已导出"  field="isExport"    queryMode="single" hidden="true"  dictionary="sf_yn"  width="70"></t:dgCol>
		 <t:dgCol title="common.operation" field="opt" width="70"></t:dgCol>
		 <t:dgDelOpt title="common.delete" urlclass="ace_button" urlStyle="background-color:#ec4758;" urlfont="fa-trash-o" url="cmsCustomerController.do?doBatchDel&ids={id}" />
		 <t:dgToolBar title="common.add" icon="icon-add" url="cmsCustomerController.do?add" funname="add"></t:dgToolBar>
		 <t:dgToolBar title="common.edit" icon="icon-edit" url="cmsCustomerController.do?get" funname="update"></t:dgToolBar>
		 <t:dgToolBar title="common.batch.delete"  icon="icon-remove" url="cmsCustomerController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
		 <t:dgToolBar title="common.view" icon="icon-search" url="cmsCustomerController.do?get" funname="detail"></t:dgToolBar>
		 <t:dgToolBar title="PASS数据" icon="icon-edit" url="cmsCustomerController.do?passCustomer&pass=Y" funname="pass"></t:dgToolBar>
		 <t:dgToolBar title="取消PASS数据" icon="icon-edit" url="cmsCustomerController.do?passCustomer&pass=N" funname="pass"></t:dgToolBar>
		 <t:dgToolBar title="统一修改备注" icon="icon-edit" url="cmsCustomerController.do?updateRemark" funname="update"></t:dgToolBar>
		 <t:dgToolBar title="common.export" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		 <t:dgToolBar title="common.import" icon="icon-put" funname="ImportXls"></t:dgToolBar>
		 <t:dgToolBar title="模板下载" icon="icon-putout" funname="ImportXlsT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 function pass(title,url){
    	var ids = [];
        var rows = $("#cmsCustomerList").datagrid('getSelections');
        if (rows.length > 0) {
        	$.dialog.confirm('你确定要PASS选中的数据吗?', function(r) {
    		   if (r) {
    		   		for (var i = 0; i < rows.length; i++) {
    					ids.push(rows[i].id);
    				}
    				$.ajax({
    					url:url,
    					data:{ids:ids.join(',')},
    					cache : false,
    					success : function(data) {
    						var d = $.parseJSON(data);
    						if (d.success) {
    							var msg = d.msg;
    							tip(msg);
    							$("#cmsCustomerList").datagrid('reload');
    						}
    					}
    				});
    			}
    		});
    	} else {
    		tip("请选择需要PASS的数据");
    	}
	        
 }

//导出
function ExportXls() {
	JeecgExcelExport("cmsCustomerController.do?exportXls","cmsCustomerList");
}
//模板下载
function ImportXlsT() {
	JeecgExcelExport("cmsCustomerController.do?importXlsT","cmsCustomerList");
}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'cmsCustomerController.do?upload', "cmsCustomerList");
}
 </script>