<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
 <head>
  <title>test</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cmsCustomerController.do?updateRemarkSave" >
		<input id="id" name="id" type="hidden" value="${cmsCustomer.id}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								原<t:mutiLang langKey="cmsCustomer.cusRemark"/>:
							</label>
						</td>
						<td class="value">
						<input id="cusRemark" name="cusRemark" type="text" style="width: 150px" readonly="readonly" class="inputxt"  maxlength="20" value='${cmsCustomer.cusRemark}'>
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
						<input id="cusRemarkEdit" name="cusRemarkEdit" type="text" style="width: 150px" class="inputxt"  maxlength="20" value='${cmsCustomer.cusRemark}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
					</tr>
					
			</table>
</t:formvalid>
 </body>
