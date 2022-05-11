package cloud.huel.crm.commons.utils;

import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 张晓华
 * @version 1.0
 */
public enum DateUtils {
	MEDIUM("yyyy-MM-dd HH:mm:ss"),
	ITALIC_NO_TIME("yyyy/MM/dd"),
	MEDIUM_NO_TIME("yyyy-MM-dd");

	private static SimpleDateFormat dateFormat;
	private String pattern;

	DateUtils(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public static final String formatDate(@Nullable Date date, DateUtils pattern){
		if (date == null) {
			date = new Date();
		}
		dateFormat = new SimpleDateFormat(getFormatPattern(pattern));
		return dateFormat.format(date);
	}


	private static String getFormatPattern(DateUtils dateUtils){
		return dateUtils.getPattern();
	}





}
