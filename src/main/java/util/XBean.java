package util;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import jakarta.servlet.http.HttpServletRequest;


public class XBean {
	public static <T> T readbean(Class<T> beanClass,HttpServletRequest req) {
		try {
			DateTimeConverter converter = new DateConverter(new Date());
			converter.setPatterns(new String[] {"dd-MM-yyyy","MM/dd/yyyy","yyyy-MM-dd"});
			ConvertUtils.register(converter, Date.class);
			T bean = beanClass.getDeclaredConstructor().newInstance();
			BeanUtils.populate(bean, req.getParameterMap());
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}