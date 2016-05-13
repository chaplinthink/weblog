package com.weblog.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/*
 * 解析器
 * 
 * 对原始数据进行解析
 * @author zhang wei 
 * 
 * */

public class LogParser {

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"d/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
	public static final SimpleDateFormat dateformat1 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	
	/** 
	 * 解析英文时间字符串
	 * 
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	private Date parseDateFormat(String string) {
		Date parse = null;
		try {
			parse = FORMAT.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}

	/**
	 * 解析日志的行记录
	 * 
	 * @param line
	 * @return 数组含有5个元素，分别是ip、时间、url、状态、流量
	 */
	public String[] parse(String line) {
		String ip = parseIP(line);
		String time = parseTime(line);
		String url = parseURL(line);
		String status = parseStatus(line);
		String traffic = parseTraffic(line);

		return new String[] { ip, time, url, status, traffic };
	}

	private String parseTraffic(String line) {
		final String trim = line.substring(line.lastIndexOf("\"") + 1)
				.trim();
		String traffic = trim.split(" ")[1];
		return traffic;
	}

	private String parseStatus(String line) {
		final String trim = line.substring(line.lastIndexOf("\"") + 1)
				.trim();
		String status = trim.split(" ")[0];
		return status;
	}

	private String parseURL(String line) {
		final int first = line.indexOf("\"");
		final int last = line.lastIndexOf("\"");
		String url = line.substring(first + 1, last);
		return url;
	}

	private String parseTime(String line) {
		final int first = line.indexOf("[");
		final int last = line.indexOf("+0800]");
		String time = line.substring(first + 1, last).trim();
		Date date = parseDateFormat(time);
		return dateformat1.format(date);
	}

	private String parseIP(String line) {
		String ip = line.split("- -")[0].trim();
		return ip;
	}
}
