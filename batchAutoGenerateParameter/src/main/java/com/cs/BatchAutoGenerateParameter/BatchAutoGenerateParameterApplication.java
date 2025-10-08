package com.cs.BatchAutoGenerateParameter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cs.BatchAutoGenerateParameter.constants.Constants;
import com.cs.BatchAutoGenerateParameter.constants.ResponseCode;
import com.cs.BatchAutoGenerateParameter.dto.GenerateParameterInput;
import com.cs.BatchAutoGenerateParameter.services.GenerateParamterV1;

@SpringBootApplication
public class BatchAutoGenerateParameterApplication implements CommandLineRunner {

	// mvn package -Dmaven.test.skip
	// java -jar target/batchAutoGenerateParameter-0.0.1-SNAPSHOT.jar [enterpriseId] [terminalId]
	
	private static final Logger log = LogManager.getLogger(BatchAutoGenerateParameterApplication.class);
	private static final Date currentDate = new Date();

	private final ConfigurableApplicationContext context;
	private static long TIME_LIMIT = 3600000; // 1 ชั่วโมง = 3600000 มิลลิวินาที
	private long startTime;
	
	@Value("${db.batchSize}")
	private String batchSize;

	@Autowired
	private GenerateParamterV1 v1;

	public static void main(String[] args) {
//		SpringApplication.run(BatchAutoGenerateParameterApplication.class, args);
		ConfigurableApplicationContext ctx = SpringApplication.run(BatchAutoGenerateParameterApplication.class, args);
		exitApplication(ctx);
	}

	public BatchAutoGenerateParameterApplication(ConfigurableApplicationContext context) {
		this.context = context;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("*************** START BatchAutoGenerateParameter ***************");

		String enterpriseId = null;
		String terminalId = null;
//		boolean generateAll = false;
		for (int i = 0; i < args.length; i++) {
			log.info("Parameter " + (i + 1) + ": " + args[i]);
		}
		if (args.length == 1) {
			try {
				enterpriseId = args[0];
				if(!isValidEnterpriseId(enterpriseId)) {
					return ;
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				return;
			}

		} else if (args.length == 2) {
			
			try {
				enterpriseId = args[0];
				terminalId = args[1];
				if(!isValidEnterpriseId(enterpriseId) || !isValidTerminalId(terminalId)) {
					return ;
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				return;
			}

		} else if (args.length > 2) {

			log.info("--- Not in condtion ---");
			return ;
		} 
//		else {
//			generateAll = true;
//			log.info("--- Generate All ---");
//		}
		log.info("-------------------------------------------------------------------");
		String currenDateStringDDMMYYYY = getDate(currentDate, new SimpleDateFormat(Constants.dd_MM_yyyy_HHmmss, Locale.US));
		log.info("Date : " + currenDateStringDDMMYYYY);
		log.info("Enterprise ID : " + enterpriseId);
		log.info("Terminal ID : " + terminalId);
//		log.info("Generate all flag : " + generateAll);
		log.info("-------------------------------------------------------------------");
		log.info("Batch size : " + batchSize);
		log.info("-------------------------------------------------------------------");
		GenerateParameterInput input = new GenerateParameterInput(enterpriseId, terminalId);
		v1.AutoGenerateParameter(input);

		log.info("**************** End BatchAutoGenerateParameter ****************");

	}

	private static void exitApplication(ConfigurableApplicationContext ctx) {
		int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
			@Override
			public int getExitCode() {
				// no errors
				return 0;
			}
		});
		System.exit(exitCode);
	}

	private String getDate(Date date, SimpleDateFormat format) {
		return format.format(date);
	}

	public static boolean isValidTerminalId(String terminalId) throws Exception {
		if (terminalId.isEmpty()) {
			log.info("Invalid Terminal ID : "+ResponseCode.VALIDATE_TERMINALID_REQUIRED.getMessage());
			return false;
		} else if (terminalId.length() != 8) {
			log.info("Invalid Terminal ID : "+ResponseCode.VALIDATE_TERMINALID_LENGTH.getMessage());
			return false;
		} else if (!terminalId.matches("\\d+")) {
			log.info("Invalid Terminal ID : "+ResponseCode.VALIDATE_TERMINALID_NUMBER.getMessage());
			return false;
		}
		return true;
	}

	public static boolean isValidEnterpriseId(String enterpriseId) throws Exception {
		if (enterpriseId.isEmpty()) {
			log.info("Invalid Enterprise ID : "+ResponseCode.VALIDATE_ENTERPRISEID_REQUIRED.getMessage());
			return false;
		} else if (enterpriseId.length() != 8) {
			log.info("Invalid Enterprise ID : "+ResponseCode.VALIDATE_ENTERPRISEID_LENGTH.getMessage());
			return false;
		} else if (!enterpriseId.matches("\\d+")) {
			log.info("Invalid Enterprise ID : "+ResponseCode.VALIDATE_ENTERPRISEID_NUMBER.getMessage());
			return false;
		}
		return true;
	}

}
