package com.cs.BatchAutoGenerateParameter.constants;


public class Constants {
	public static final String dd_MM_yyyy_HHmmss = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_yyyyMMddHHmmss_ = "yyyy-MM-dd_HH-mm-ss";
	public static final String DATE_FORMAT_yyyyMMdd = "yyyy-MM-dd";
	public static final String DATE_FORMAT_yyyyMMddHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String ON = "on";
	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";
	public static final String SUCCESS = "Success";
	public static final String FAIL = "Fail";
	
	public static class CONFIG {
		public static final String BACKUP = "BACKUP";
		public static final String BACKUP_TERMINAL_MASTER = "BackupTerminalMaster";
		

		// Prevent instantiation
		private CONFIG() {
		}
	}
	
	public static final Integer APPLICATION_STATUS_1 = 1;
	public static final Integer APPLICATION_STATUS_2 = 2;
	public static final Integer APPLICATION_STATUS_3 = 3;
	
	public static final String MESSAGE_TITLE = "( รายการนี้อยู่ระหว่างรออนุมัติ )";
	public static final Integer PAIR_STATUS_1=1;
	public static final Integer PAIR_STATUS_2=2;
	public static final Integer IMPORT_TYPE_1=1;
	public static final Integer IMPORT_TYPE_2=2;
	
	public static final String INSERT = "Insert";
	public static final String UPDATE = "Update";
	
	public static final boolean Active = true;
	public static final boolean NotActive = true;
	
	public static final int  SHORT_DISPLAY_20 = 20;
	
	public static final boolean BATCH_FLAG_FALSE = false;
	public static class TSCF {
		public static final String PROTOCAL_VERSION = "02 00"; //2byte;
		public static final String TYPENAME = "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";//16 byte
		public static final String CRC = "30 30 30 30";//4 byte
		public static final String SESSION_ID = "00 30 30 30 30 30 30 30";// 8 byte
		public static final String TRIP_COUNT = "00";// 1 byte
		public static final String STATUS_COUNT = "00";// 1 byte
		public static final String APDU_COUNT = "00";// 1 byte
		
	}

}
