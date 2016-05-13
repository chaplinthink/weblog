package com.moon.dao;

import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import com.moon.model.Admin;
import com.moon.util.GlobalData;

/*
 * @author zhangwei
 * @date 2016年5月4日
 * */
public class AdminDao extends BaseDao {
    //管理员登陆
	public Admin login(Admin admin) {
		String sql = "SELECT  userId ,userName, passWord  FROM admin " +
				"WHERE userName=? and passWord=?";
		String args[] = {admin.getUserName(),admin.getPassWord()};
		Result r =  query(sql, args);
		if(r==null||r.getRowCount()==0){
			return null;
		}else{
			SortedMap sm = r.getRows()[0];
			Admin  ad  = r2o(sm);
			return ad;
		}		
		
	}

    //管理员密码的修改
	public void  update(Admin admin) {
		String sql =" UPDATE `admin`.`dbo.t_admin` SET  `userPw`=?";
		String args[] = {admin.getAgain_pwd()};
	    update(sql, args);	   
	}
	
	
	private Admin r2o(SortedMap sm) {
		if(sm==null||sm.size()==0){
			return null;
		}
		
		Admin admin = new Admin();
		admin.setUserName(GlobalData.toString(sm, "userName"));
		admin.setPassWord(GlobalData.toString(sm, "passWord"));
		
		return admin ;	

	}

}
