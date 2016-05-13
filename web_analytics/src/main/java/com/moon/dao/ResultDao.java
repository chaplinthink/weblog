package com.moon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import com.moon.model.WebLog;
/*
 * @author   zhangwei
 * @date     2016年5月4日
 * */

public class ResultDao extends BaseDao {
	
	public  List<WebLog> findResult(){
		String sql = "select * from weblog_logs_stat";
		Result rs =  super.query(sql, null);
		List<WebLog> list = r2o(rs);
		
		return list;
	}
	
	private  List<WebLog> r2o(Result r){
		
		if(r==null||r.getRowCount()==0){
			return null;
		}
		
		SortedMap[] sm = r.getRows();
        List<WebLog> list = new ArrayList<WebLog>();
		for(SortedMap sms : sm){
		   	WebLog log = r2o(sms);
		   	list.add(log);
		} 
		return list;
	} 

	private WebLog r2o(SortedMap sms){
        WebLog log = new WebLog();      	
		log.setLogdate(sms.get("logdate").toString());
		log.setIp(new Integer(sms.get("ip").toString()));
		log.setPv(new Integer(sms.get("pv").toString()));
		log.setReguser(new Integer(sms.get("reguser").toString()));
		log.setJumper(new Integer(sms.get("jumper").toString()));
		return log;  
	}
}
