package by.epam.ts.controller.command.util.parse;

import java.time.LocalDate;

public class DateParser {
	private static final String DATE_PATTERN = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
	
	public LocalDate parseDate(String date) {
		LocalDate localDate = null;
		if (date == null || date.isEmpty() || !date.matches(DATE_PATTERN)) {
			return localDate;
		}
		localDate = LocalDate.parse(date);
		return localDate;
	}
	

}
