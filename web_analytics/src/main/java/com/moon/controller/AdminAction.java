package com.moon.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.moon.dao.AdminDao;
import com.moon.dao.ResultDao;
import com.moon.model.Admin;
import com.moon.model.WebLog;
import com.moon.util.GlobalData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * Created by wei on 2016/5/4
 *  用户相关操作
 * */

public class AdminAction extends ActionSupport implements ModelDriven<Admin>{
	  
	  Admin admin = new Admin();
	  ResultDao result = new ResultDao();
	  List<WebLog> list = result.findResult();
	 
	  //登陆功能的实现
	  public String  login() throws Exception {
		 AdminDao gd = new AdminDao();
		 Admin g = gd.login(admin);

		 if(null == g){
			 return GlobalData.REDO;
		 }        			
		 ActionContext ict = ActionContext.getContext();
		 ict.put("result", list);
		
		 
		 //session 设置
		 Map session = ActionContext.getContext().getSession();
	         session.put(GlobalData.SessionGuesst,admin);
		
		 return "index";      
	}
	  
	  //管理员登出
	 
	public String logout() throws Exception {
		
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session =  request.getSession();
			session.invalidate();
		    return GlobalData.REDO;
	}
	  
	  
	//修改管理员密码
	public String update() throws Exception {
		  AdminDao gd = new AdminDao();
		  gd.update(admin);	 
		 
		  return SUCCESS;
	}

	public Admin getModel() {
		if(this.admin == null){
			this.admin = new Admin();
		}
		return this.admin;
	}
	
}
