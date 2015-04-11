/**
 * PROJECT NAME:edrive-api_1.0
 * PACKAGE:so.glad.channel.edrive.utils
 */
package so.glad.channel.edrive.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Description: so.glad.channel.edrive.utils
 * @author Palmtale
 * @since 2015-03-26
 */
public interface DateUtil {

	String getFormatDateString(Date date, String format);

	Date getDateFromFormatString(String str, String format);

	Date now();

	DateUtil DEFAULT_IMPL = new DateUtil() {

		private Logger log = LoggerFactory.getLogger(DateUtil.class);

		public String getFormatDateString(Date date, String format) {

			String dateStr = "";

			if (date == null || format == null) {
				return dateStr;
			}

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				dateStr = sdf.format(date);
			} catch (Exception e) {
				log.debug("getFormatDateString() - format date[" + date
						+ "] formatStr[" + format + "] failed!!!", e);
				log.info("getFormatDateString() - format date[" + date
						+ "] formatStr[" + format + "] failed!!!");
			}
			return dateStr;
		}

		public Date getDateFromFormatString(String str, String format) {

			Date date = null;

			if (str == null || format == null) {
				return date;
			}

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				log.debug("getDateFromFormatString() - get date from str[" + str
						+ "] as format[" + format + "] failed!!!", e);
				log.info("getDateFromFormatString() - get date from str[" + str
						+ "] as format[" + format + "] failed!!!");
			}
			return date;
		}

		public Date now(){
			return new Date();
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	};

}
