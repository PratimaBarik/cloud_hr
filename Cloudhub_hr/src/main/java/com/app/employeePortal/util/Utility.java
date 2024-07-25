package com.app.employeePortal.util;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class Utility {

	public static String getISOFromDate(Date date) {

		String dateString = null;
		if (null != date) {

			dateString = date.toInstant().toString();
			return dateString;
		}

		return dateString;
	}

	public static Date getDateFromISOString(String dateInISOString) throws Exception {

		Date date = null;

		if (null != dateInISOString && !dateInISOString.isEmpty()) {
			date = Date.from(Instant.parse(dateInISOString));
			return date;
		}
		return date;
	}

	public static int getWeekNoFromDate(Date dateinput) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateinput);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		return week;

	}

	public static Date getEndDateFromWeekNo(int weekNo, int enterYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, weekNo);
		calendar.set(Calendar.YEAR, enterYear);
		calendar.add(Calendar.DATE, 6);
		Date date = calendar.getTime();

		return date;
	}
	
	public static Date getStartDateFromWeekNo(int weekNo, int enterYear) {
		 System.out.println("weekNo===" + weekNo);
		 System.out.println("enterYear===" + enterYear);
	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.setFirstDayOfWeek(Calendar.MONDAY); // Set first day of the week to Monday
	    calendar.set(Calendar.YEAR, enterYear);
	    calendar.set(Calendar.WEEK_OF_YEAR, weekNo);
	    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Set to Monday of the specified week
	    return calendar.getTime();
	}
	
//	public static Date getStartDateFromWeekNo(int weekNo, int enterYear) {
//	System.out.println("weekNo===" + weekNo);
//	 System.out.println("enterYear===" + enterYear);
//	Calendar calendar = Calendar.getInstance();
//	calendar.clear();
//	calendar.set(Calendar.WEEK_OF_YEAR, weekNo);
//	calendar.set(Calendar.YEAR, enterYear);
//	Date date = calendar.getTime();
//	return date;
//
//}


	public static Date getNextDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		return date;

	}

	public static int getMonthNoFromDate(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return month;

	}

	public static Date getFirstDateFromMonthNumber(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		Date startDate = calendar.getTime();
		return startDate;

	}

	public static Date getEndDateFromMonthNumber(int month, int year) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month, 1);
		int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
		Date endDate = calendar.getTime();

		return endDate;

	}

	public static int getYearFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;

	}

	public static Date removeTime(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static LocalDate getLocalDateByDate(Date date) {

		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;

	}

	public static Date getUtilDateByLocalDate(LocalDate date) {
		Date UtilDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return UtilDate;

	}

	public static LocalDate getStartDateOfMonth(LocalDate localDate) {

		LocalDate startDate = localDate.withDayOfMonth(1);

		return startDate;

	}

	public static LocalDate getEndDateOfMonth(LocalDate localDate) {

		LocalDate endDate = localDate.plusMonths(1).withDayOfMonth(1).minusDays(1);

		return endDate;

	}

	public static LocalDate getCurrentWeekStartDate(LocalDate localDate) {
		LocalDate monday = localDate;
		while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
			monday = monday.minusDays(1);
		}
		return monday;
	}

	public static LocalDate getCurrentWeekEndDate(LocalDate localDate) {
		LocalDate sunday = localDate;
		while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
			sunday = sunday.plusDays(1);
		}
		return sunday;

	}

	public static Date getDateAfterEndDate(Date date) {

		LocalDate endDate = getLocalDateByDate(date);
		LocalDate dateAfter = endDate.plusDays(1);

		return getUtilDateByLocalDate(dateAfter);
	}

	public static Date getBeforeDate(Date date) {

		LocalDate endDate = getLocalDateByDate(date);
		LocalDate dateAfter = endDate.minusDays(1);

		return getUtilDateByLocalDate(dateAfter);
	}

	public static Date getPlusDate(Date date, int days) {

		LocalDate endDate = getLocalDateByDate(date);
		LocalDate dateAfter = endDate.plusDays(days);

		return getUtilDateByLocalDate(dateAfter);
	}

	public static Date getDateWithTimeUsingCalendar(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 12);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		Date returnDate = gc.getTime();
		return returnDate;
	}

	public static String getMinusDate(Date date, int days) {
		String sdate = null;
		LocalDate endDate = getLocalDateByDate(date);
		LocalDate dateBefore = endDate.minusDays(days);

		if (null != date) {
			sdate = getISOFromDate(getUtilDateByLocalDate(dateBefore));
		}

		return sdate;
	}

	public static Date getBeforeDate(Date date, int days) {
		Date sdate = null;
		LocalDate endDate = getLocalDateByDate(date);
		LocalDate dateBefore = endDate.minusDays(days);

		if (null != date) {
			sdate = getUtilDateByLocalDate(dateBefore);
		}

		return sdate;
	}

	public static Date getPlusMonth(Date date, int month) throws Exception {
		LocalDate localDate = Utility.getLocalDateByDate(date).plusMonths(month);
		Date resultDate = Utility.getUtilDateByLocalDate(localDate);
		return resultDate;

	}

	public static Date get1stDateAndLastDateOfThisYear() throws Exception {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 31);

		Date lastDayOfYear = calendar.getTime();
		return lastDayOfYear;
	}

	public static Date getLastDateOfThisYear() throws Exception {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 31);

		Date lastDayOfYear = calendar.getTime();
		return lastDayOfYear;
	}

	public static Date get1stDateOfThisYear() throws Exception {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		Date firstDayOfYear = calendar.getTime();
		return firstDayOfYear;
	}

	public static String convertExcelDateToString(String excelDateNumber) {
		double excelDate = Double.parseDouble(excelDateNumber);
		long excelEpochMillis = -2209075200000L;
		long millisPerDay = 24 * 60 * 60 * 1000;

		long millis = (long) ((excelDate - 1) * millisPerDay) + excelEpochMillis;

		Date date = new Date(millis);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

	public static long getTime() {
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
		long time = Long.parseLong(now.format(formatter));
		return time;
	}

	public static String FullName(String firstName, String middleName, String lastName) {
		return (firstName != null && !firstName.isEmpty() ? firstName + " " : "")
				+ (middleName != null && !middleName.isEmpty() ? middleName + " " : "")
				+ (lastName != null && !lastName.isEmpty() ? lastName : "");
	}
	
	public static Date getQuarterStartDate(int year, int quarter) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (quarter - 1) * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getQuarterEndDate(int year, int quarter) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, quarter * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // Move to the last day of the previous month
        return calendar.getTime();
    }
    
    public static int getQuarterNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);

        // Quarters are usually defined as 1-3 for Q1, 4-6 for Q2, 7-9 for Q3, 10-12 for Q4
        // So we divide the month by 3 and add 1 to get the quarter number
        System.out.println("month "+month);
        System.out.println("(month / 3)"+(month / 3));
        System.out.println("(month / 3) + 1"+((month / 3) + 1));
        return (month / 3) + 1;
    }
    
    public static List<LocalDate> getDatesBetweenByLocalDateClass(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        
        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                dates.add(currentDate);
                currentDate = currentDate.plusDays(1);
            }
        }
        
        return dates;
    }
    
    public static List<Date> getDatesBetweenByDateClass(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        
        if (startDate != null && endDate != null && !startDate.after(endDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            
            while (!calendar.getTime().after(endDate)) {
                dates.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        
        return dates;
    }
    
    public static boolean containsIgnoreCase(String str, String searchStr) {
        final int length = searchStr.length();
        final int max = str.length() - length;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, length)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
