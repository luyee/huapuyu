package unique;

import java.util.Calendar;

public class Tester {

	public static void main(String[] args) {
		System.out.println(getInvalidate());

	}

	private static java.sql.Date getInvalidate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -75);
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		java.sql.Date invalidateDate = new java.sql.Date(calendar.getTimeInMillis());
		return invalidateDate;
	}
}
