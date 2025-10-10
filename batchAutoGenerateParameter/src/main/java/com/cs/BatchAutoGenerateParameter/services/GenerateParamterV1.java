package com.cs.BatchAutoGenerateParameter.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.BatchAutoGenerateParameter.dto.GenerateParameter;
import com.cs.BatchAutoGenerateParameter.dto.GenerateParameterInput;
import com.cs.BatchAutoGenerateParameter.dto.TscfEnterpriseGenereate;
import com.cs.BatchAutoGenerateParameter.entities.Enterprise;
import com.cs.BatchAutoGenerateParameter.entities.Terminal;
import com.cs.BatchAutoGenerateParameter.repositories.IEnterpriseJPARepository;
import com.cs.BatchAutoGenerateParameter.repositories.ITerminalJPARepository;

@Service
public class GenerateParamterV1 {

	private static final Logger log = LogManager.getLogger(GenerateParamterV1.class);

	@Autowired
	private GenerateParameterService generateParameterService;

	@Autowired
	private ITerminalJPARepository terminalRepo;

	@Autowired
	private IEnterpriseJPARepository enterpriseRepo;
	
	private int updatedById = 1;

	public void AutoGenerateParameter(GenerateParameterInput input) {
		GenerateParameter parameter = new GenerateParameter();
		parameter.setUpdateById(updatedById);
		
		if(input.getEnterpriseId()==null && input.getTerminalId() == null) {
			try {
				log.info(">> Generate All.");
				List<TscfEnterpriseGenereate> gen = generateParameterService.generateAll(parameter);
			} catch (Exception e) {
				e.printStackTrace();
				return ;
			}
		}else {
			//------------------------------------------
			Enterprise en = enterpriseRepo.findByEnterpriseId(input.getEnterpriseId());
			if(en != null) {
//				log.info(en.toString());
//				parameter.setEnterpriseIdPk(en.getId());
				parameter.setEnterprise(en);
			}else {
				log.info("Not Found EnterpriseId : "+input.getEnterpriseId());
//				log.info("Not Found EnterpriseId or TerminalId");
				return ;
			}
			//------------------------------------------
			if(input.getEnterpriseId()!=null && input.getTerminalId() != null) {
				Terminal tm = terminalRepo.findByTerminalId(input.getTerminalId());
				if(tm!=null) {
//					log.info(tm.toString());
//					parameter.setTerminalIdPk(tm.getId());
					parameter.setTerminal(tm);
					try {
						log.info(">> Generate By Enterprise ID and Terminal ID.");
						TscfEnterpriseGenereate gen = generateParameterService.generateByEnterpriseIdAndTerminalId(parameter);
					} catch (Exception e) {
						e.printStackTrace();
						return ;
					}
					
				}else {
					log.info("Not found Terminal ID : "+input.getTerminalId());
//					log.info("Not Found EnterpriseId or TerminalId");
					return ;
					
				}
			}else if(input.getEnterpriseId()!=null && input.getTerminalId() == null){
				try {
					log.info(">> Generate By Enterprise ID.");
					TscfEnterpriseGenereate gen = generateParameterService.generateByEnterpriseId(parameter);
				} catch (Exception e) {
					e.printStackTrace();
					return ;
				}
			}
			
		}
		
	}
}
