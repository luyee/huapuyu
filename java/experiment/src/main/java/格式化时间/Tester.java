package 格式化时间;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class Tester {
	public static void main(String[] args) throws Exception {
		Date dateNow = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(dateNow));

		Calendar nowDate = Calendar.getInstance();
		nowDate.add(Calendar.DATE, -2);
		System.out.println(dateFormat.format(nowDate.getTime()));

		String s1 = "2009-12-03 00:00:00";
		String s2 = "2009-12-04 00:00:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		Date d1 = sdf.parse(s1);
		Date d2 = sdf.parse(s2);

		System.out.println((d2.getTime() - d1.getTime()) / (60 * 1000 * 60 * 24));
		System.out.println(d2.getTime() - d1.getTime());
		System.out.println(d1.compareTo(d2));

		System.out.println(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		System.out.println(DateUtils.addDays(new Date(), 1));
		System.out.println(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));
		System.out.println(DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));
		System.out.println(DateFormatUtils.format(new Date(), "szfgasdfa"));
		System.out.println(String.format("%tc", new Date()));
		System.out.println(String.format("%1$s %2$tF %2$tT", "hello world", new Date()));
		System.out.println(String.format("%s %tF %<tT", "hello world", new Date()));
	}
}
