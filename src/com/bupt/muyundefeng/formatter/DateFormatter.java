package com.bupt.muyundefeng.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

/*
 * This class about some formatter.
 * covert string type to date type
 * @author muyundefeng
 */
public class DateFormatter implements Formatter<Date> {

	private String datePattern;
	private SimpleDateFormat simpleDateFormat;
	public DateFormatter(String datePattern) {
		// TODO Auto-generated constructor stub
		this.datePattern=datePattern;
		simpleDateFormat=new SimpleDateFormat(datePattern);
		simpleDateFormat.setLenient(false);
	}
	
	@Override
	public String print(Date arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return simpleDateFormat.format(arg0);
	}

	@Override
	public Date parse(String s, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		try{
			return simpleDateFormat.parse(s);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("ivalid date foemat.Please use this parttern "+datePattern);
		}
		//return null;
	}

}
