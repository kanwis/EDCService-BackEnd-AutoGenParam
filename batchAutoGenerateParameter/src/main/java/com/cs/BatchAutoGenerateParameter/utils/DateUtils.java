package com.cs.BatchAutoGenerateParameter.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import com.cs.BatchAutoGenerateParameter.constants.Constants;


public class DateUtils {

	/*
	 * public static String convertISODateTimeToString(String date) { String
	 * dateReturn = ""; SimpleDateFormat sdf = new
	 * SimpleDateFormat(Constants.DATE_FORMAT_yyyyMMddHHmmss); try {
	 * DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	 * 
	 * // Parse the string to an OffsetDateTime object OffsetDateTime offsetDateTime
	 * = OffsetDateTime.parse(date, formatter);
	 * 
	 * // Convert OffsetDateTime to Date Date dateFormat =
	 * Date.from(offsetDateTime.toInstant()); // Date dateFormat =
	 * sdf.parse(date.replace("Z", "+00:00")); // Create a Calendar instance and set
	 * the date Calendar calendar = Calendar.getInstance();
	 * calendar.setTime(dateFormat);
	 * 
	 * // Add one day to the date // calendar.add(Calendar.DAY_OF_MONTH, 1);
	 * 
	 * // Get the new date // dateReturn = calendar.getTime().toString();
	 * 
	 * dateReturn = sdf.format(calendar.getTime());
	 * 
	 * System.out.println(dateReturn); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return dateReturn; }
	 */

	static boolean isValidDateFormat(String dateString, String dateFormat) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		try {
			LocalDate date = LocalDate.parse(dateString, formatter);
			return true; // Date is valid
		} catch (DateTimeParseException e) {
			return false; // Date is not valid
		}
	}

	public static Date dateFormat(String dateString, String format) throws ParseException {// Constants.DATE_FORMAT_yyyyMMdd
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);// yyyy-MM-dd
																					// HH:mm:ss
		Date date = formatter.parse(dateString);
		return date;
	}

	public static String dateFormatLocal(String inputDate) {

		if (!StringEDCUtils.isNullOrEmpty(inputDate)) {
			if (!isValidDateFormat(inputDate, Constants.DATE_FORMAT_yyyyMMddHHmmssSSSZ)) {
				// Parse the input date
				LocalDate localDate = LocalDate.parse(inputDate);

				// Create a LocalDateTime with the desired time (e.g., 17:00:00)
				LocalDateTime dateTime = localDate.atTime(17, 0);

				// Convert to UTC (Z)
				String outputDate = dateTime.atOffset(ZoneOffset.UTC)
						.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_yyyyMMddHHmmssSSSZ));
				return outputDate;
			} else {
				return inputDate;
			}
		} else {
			return inputDate;
		}
	}

	public static Date convertToDate(Object obj) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		if (obj == null) {
			return null;
		}

		if (obj instanceof Timestamp) {
			Timestamp tmp = (Timestamp) obj;
			Date date = new Date(tmp.getTime());
			String dateStr = formatter.format(date);
			try {
				date = formatter.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		} 
//		else {
//			throw new IllegalArgumentException("Cannot convert type: " + obj.getClass().getName());
//		}
		if (obj instanceof String) {
	        String str = ((String) obj).trim();
	        System.out.println("length = "+str.length());
	        if (str.isEmpty()) {
	            return null; // ป้องกัน Incorrect TIMESTAMP value: ''
	        }
	        try {
	        	 // เลือก format ตามความยาวของ string
	            if (str.length() == 10) { // yyyy-MM-dd
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                return sdf.parse(str);
	            } else if (str.length() == 19) { // yyyy-MM-dd HH:mm:ss
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                return sdf.parse(str);
	            } else if (str.length() > 19) { // yyyy-MM-dd HH:mm:ss.SSSSSS
	                DateTimeFormatter formatterx = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	                LocalDateTime ldt = LocalDateTime.parse(str, formatterx);
	                return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	            } else {
	                throw new IllegalArgumentException("Unsupported date format: " + str);
	            }
	        } catch (ParseException e) {
	            throw new IllegalArgumentException("Invalid date format: " + str, e);
	        }
	    }
		throw new IllegalArgumentException("Cannot convert type: " + obj.getClass().getName());
	}
	
    public static String appendTimestampToFilename(String filename) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());

        // ตรวจว่านามสกุลเป็น .xlsx หรือ .xls
        if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
            int dotIndex = filename.lastIndexOf('.');
            String namePart = filename.substring(0, dotIndex);
            String extension = filename.substring(dotIndex); // รวมจุด
            return namePart + "_" + timestamp + extension;
        }

        // ถ้าไม่ตรง .xlsx หรือ .xls คืนค่าเดิม
        return filename;
    }

    public static String currentDate() throws ParseException {// Constants.DATE_FORMAT_yyyyMMdd
    	 Date now = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm"); 
         String formatted = sdf.format(now);
//         System.out.println(formatted);
		return formatted;
	}

}
