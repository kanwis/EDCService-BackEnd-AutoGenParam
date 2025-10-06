package com.cs.BatchAutoGenerateParameter.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.cs.BatchAutoGenerateParameter.dao.ICommonParameterDDao;
import com.cs.BatchAutoGenerateParameter.dao.IIndividualParameterDDao;
import com.cs.BatchAutoGenerateParameter.dao.ITscfDDao;
import com.cs.BatchAutoGenerateParameter.dao.ITscfHDao;
import com.cs.BatchAutoGenerateParameter.dto.CommonGenerateInfo;
import com.cs.BatchAutoGenerateParameter.dto.CommonGenereateDetailInfo;
import com.cs.BatchAutoGenerateParameter.dto.GenerateParameter;
import com.cs.BatchAutoGenerateParameter.dto.IndividualGenerateDetailInfo;
import com.cs.BatchAutoGenerateParameter.dto.TscfDDto;
import com.cs.BatchAutoGenerateParameter.dto.TscfEnterpriseGenereate;
import com.cs.BatchAutoGenerateParameter.dto.TscfHDto;
import com.cs.BatchAutoGenerateParameter.dto.TscfTerminalGenereate;
import com.cs.BatchAutoGenerateParameter.entities.Enterprise;
import com.cs.BatchAutoGenerateParameter.entities.Terminal;
import com.cs.BatchAutoGenerateParameter.tscf.TscfBuild;
import com.cs.BatchAutoGenerateParameter.tscf.TscfTagData;
import com.cs.BatchAutoGenerateParameter.utils.DateUtils;

@Controller
public class GenerateParameterService {
	private static final Logger log = LogManager.getLogger(GenerateParameterService.class);

	@Autowired
	private ICommonParameterDDao comDDao;

	@Autowired
	private IIndividualParameterDDao inDDao;

	@Autowired
	private ITscfHDao tscfHDao;

	@Autowired
	private ITscfDDao tscfDDao;

	public List<CommonGenereateDetailInfo> searchCommonDetailByTerminalId(Integer terminalId) throws Exception {
		return comDDao.searchCommonDetailByTerminalId(terminalId, null, null);
	}

	public List<IndividualGenerateDetailInfo> searchIndividualDetailByTerminalId(Integer terminalId) throws Exception {
		return inDDao.searchIndividualDetailByTerminalId(terminalId);
	}

	/*
	 * public List<TscfEnterpriseGenereate> previewGenerateAll(GenerateParameter
	 * param) throws Exception {
	 * 
	 * List<TscfEnterpriseGenereate> getTscfList = new
	 * ArrayList<TscfEnterpriseGenereate>(); List<TscfTerminalGenereate>
	 * tscfTerminalList = null; TscfEnterpriseGenereate genTscf = null;
	 * 
	 * List<CommonGenerateInfo> enterpriseList = comDDao.searchGenerateCommonHAll();
	 * String message = ""; if(enterpriseList != null && enterpriseList.size() >0) {
	 * for(CommonGenerateInfo e : enterpriseList) { message = ""; genTscf = new
	 * TscfEnterpriseGenereate(); genTscf.setEnterpriseIdPk(e.getEnterpriseIdPk());
	 * genTscf.setEnterpriseId(e.getEnterpriseId());
	 * genTscf.setEnterpriseNameTh(e.getEnterpriseNameTh());
	 * genTscf.setMerchantIdPk(e.getMerchantIdPk());
	 * genTscf.setMerchantId(e.getMerchantId()); TscfTerminalGenereate tscfTerminal
	 * = null; List<CommonGenerateInfo> terminalList =
	 * comDDao.searchGenerateCommonH(e.getEnterpriseIdPk(),null,null);
	 * if(terminalList !=null && terminalList.size()>0) { tscfTerminalList = new
	 * ArrayList<TscfTerminalGenereate>(); tscfTerminal = null;
	 * for(CommonGenerateInfo t : terminalList) {
	 * 
	 * tscfTerminal = new TscfTerminalGenereate();
	 * tscfTerminal.setTerminalIdPk(t.getTerminalIdPk());
	 * tscfTerminal.setTerminalId(t.getTerminalId());
	 * tscfTerminalList.add(tscfTerminal); // List<CommonGenereateDetailInfo>
	 * commonDList = comDDao.searchGenerateCommonDetail(t.getCommonParameterHId(),
	 * t.getParameterVersionId()); } genTscf.setTerminalIdList(tscfTerminalList);
	 * 
	 * }else { message = "ไม่พบ Terminal ID"; log.info("Not found Terminal ID."); }
	 * if(tscfTerminalList!=null) {
	 * 
	 * }
	 * 
	 * genTscf.setMessage(message); getTscfList.add(genTscf); } }else { // message =
	 * "ไม่พบ ข้อมูล สำหรับ Generate";
	 * log.info("Not found common parameter in system"); }
	 * 
	 * return getTscfList;
	 * 
	 * }
	 */

	public List<TscfEnterpriseGenereate> generateAll(GenerateParameter param) throws Exception {
		List<TscfEnterpriseGenereate> getTscfList = new ArrayList<TscfEnterpriseGenereate>();
		List<TscfTerminalGenereate> tscfTerminalList = null;
		TscfEnterpriseGenereate genTscf = null;

		List<CommonGenerateInfo> enterpriseList = comDDao.searchGenerateCommonHAll();
		String message = "";
		int i=0;int sizeI=0;int j=0,sizeJ=0;
		if (enterpriseList != null && enterpriseList.size() > 0) {
			sizeI = enterpriseList.size();
			log.info("Enterprise Size : "+sizeI);
			for (CommonGenerateInfo e : enterpriseList) {i++;
				log.info("("+i+"/"+sizeI+") Enterprise ID : " + e.getEnterpriseId() + "[" + e.getEnterpriseIdPk() + "]");
				TscfHDto saveH = createTscfHDto(param.getUpdateById());

				message = "";
				genTscf = new TscfEnterpriseGenereate();
				genTscf.setEnterpriseIdPk(e.getEnterpriseIdPk());
				genTscf.setEnterpriseId(e.getEnterpriseId());
				genTscf.setEnterpriseNameTh(e.getEnterpriseNameTh());
				genTscf.setMerchantIdPk(e.getMerchantIdPk());
				genTscf.setMerchantId(e.getMerchantId());
				TscfTerminalGenereate tscfTerminal = null;
				j=0;sizeJ=0;
				
				List<CommonGenerateInfo> terminalList = comDDao.searchGenerateCommonH(e.getEnterpriseIdPk(), null, null);
				if (terminalList != null && terminalList.size() > 0) {
					tscfTerminalList = new ArrayList<TscfTerminalGenereate>();
					tscfTerminal = null;
					List<TscfDDto> saveDList = new ArrayList<TscfDDto>();
					sizeJ = terminalList.size();
					log.info("Terminal Size : "+sizeJ);
					for (CommonGenerateInfo t : terminalList) {j++;
						
						log.info("("+j+") Terminal ID : " + t.getTerminalId() + "[" + t.getTerminalIdPk() + "]");
						log.info("---------------------------------");
						tscfTerminal = new TscfTerminalGenereate();
						tscfTerminal.setTerminalIdPk(t.getTerminalIdPk());
						tscfTerminal.setTerminalId(t.getTerminalId());
						tscfTerminalList.add(tscfTerminal);

						List<CommonGenereateDetailInfo> comDList = comDDao
								.searchGenerateCommonDetail(t.getCommonParameterHId(), t.getParameterVersionId());
						List<IndividualGenerateDetailInfo> inDList = inDDao
								.searchIndividualDetailByTerminalId(t.getTerminalIdPk());

						if (comDList.size() > 0 && inDList.size() > 0) {
							List<String> parameterCodes = inDList.stream()
									.map(IndividualGenerateDetailInfo::getParameterCode).collect(Collectors.toList());

							comDList.removeIf(item -> parameterCodes.contains(item.getParameterCode()));
						}

//						log.info("----------------");
//						log.info("Common size : "+comDList.size());
//						log.info("Common {} : ",comDList);
//						log.info("----------------");
//						log.info("Individual size : "+inDList.size());
//						log.info("Individual {} : ",inDList);

						// Gen tscf
						TscfBuild gen = new TscfBuild(comDList, inDList);
						TscfTagData tags = gen.convertToTscfObject();
//						System.err.println(">>"+tags.toTscf());
						// -------------------------------

						byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
						String tscfJson = new String(bytes, StandardCharsets.UTF_8);
						// --------------------------------------
						log.info(tscfJson);
						// -------------------------------
						TscfDDto saveD = createTscfDDto(null, param.getUpdateById(), t.getTerminalIdPk(), bytes, tscfJson);
						saveDList.add(saveD);
						// --------------------------------------
					}
					genTscf.setTerminalIdList(tscfTerminalList);

					if (saveDList.size() > 0) {
						// --------------------------------------
						insertTscfByEnterprise(genTscf, saveH, saveDList);
						// --------------------------------------
						getTscfList.add(genTscf);
					} else {
						log.info("Tscf Dto is null.");
					}
				} else {
					message = "ไม่พบ Terminal ID";
					log.info("Not found Terminal ID [" + e.getEnterpriseId() + "].");
					log.info("---------------------------------");
				}

				genTscf.setMessage(message);
				getTscfList.add(genTscf);
			}
		} else {
//			message = "ไม่พบ ข้อมูล สำหรับ Generate";
			log.info("Not found common parameter in system.");
		}

		return getTscfList;

	}

	public TscfEnterpriseGenereate generateByEnterpriseId(GenerateParameter param) throws Exception {
//		List<TscfEnterpriseGenereate> getTscfList = new ArrayList<TscfEnterpriseGenereate>();
		List<TscfTerminalGenereate> tscfTerminalList = null;
		TscfEnterpriseGenereate genTscf = null;

//		List<CommonGenerateInfo> enterpriseList = comDDao.searchGenerateCommonHAll();
		String message = "";
		if (param.getEnterprise() != null) {
			Enterprise e = param.getEnterprise();
//			for(CommonGenerateInfo e : enterpriseList) {
			log.info("Enterprise ID : " + e.getEnterpriseId() + "[" + e.getId() + "]");
			TscfHDto saveH = createTscfHDto(param.getUpdateById());

			message = "";
			genTscf = new TscfEnterpriseGenereate();
			genTscf.setEnterpriseIdPk(e.getId());
			genTscf.setEnterpriseId(e.getEnterpriseId());
			genTscf.setEnterpriseNameTh(e.getEnterpriseNameTh());
//				genTscf.setMerchantIdPk(e.getMerchantIdPk());
//				genTscf.setMerchantId(e.getMerchantId());
			TscfTerminalGenereate tscfTerminal = null;
			List<CommonGenerateInfo> terminalList = comDDao.searchGenerateCommonH(e.getId(), null, null);
			if (terminalList != null && terminalList.size() > 0) {
				tscfTerminalList = new ArrayList<TscfTerminalGenereate>();
				tscfTerminal = null;
				List<TscfDDto> saveDList = new ArrayList<TscfDDto>();
				for (CommonGenerateInfo t : terminalList) {
					log.info("Terminal ID : " + t.getTerminalId() + "[" + t.getTerminalIdPk() + "]");
					log.info("---------------------------------");
					tscfTerminal = new TscfTerminalGenereate();
					tscfTerminal.setTerminalIdPk(t.getTerminalIdPk());
					tscfTerminal.setTerminalId(t.getTerminalId());
					tscfTerminalList.add(tscfTerminal);

					List<CommonGenereateDetailInfo> comDList = comDDao
							.searchGenerateCommonDetail(t.getCommonParameterHId(), t.getParameterVersionId());
					List<IndividualGenerateDetailInfo> inDList = inDDao
							.searchIndividualDetailByTerminalId(t.getTerminalIdPk());

					if (comDList.size() > 0 && inDList.size() > 0) {
						List<String> parameterCodes = inDList.stream()
								.map(IndividualGenerateDetailInfo::getParameterCode).collect(Collectors.toList());

						comDList.removeIf(item -> parameterCodes.contains(item.getParameterCode()));
					}

					// Gen tscf
					TscfBuild gen = new TscfBuild(comDList, inDList);
					TscfTagData tags = gen.convertToTscfObject();
//						System.err.println(">>"+tags.toTscf());
					// -------------------------------

					byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
					String tscfJson = new String(bytes, StandardCharsets.UTF_8);
					// --------------------------------------
					log.info(tscfJson);
					// -------------------------------
					TscfDDto saveD = createTscfDDto(null, param.getUpdateById(), t.getTerminalIdPk(), bytes, tscfJson);
					saveDList.add(saveD);
					// --------------------------------------
				}
				genTscf.setTerminalIdList(tscfTerminalList);

				if (saveDList.size() > 0) {
					// --------------------------------------
					insertTscfByEnterprise(genTscf, saveH, saveDList);
					// --------------------------------------
//						getTscfList.add(genTscf);
				} else {
					log.info("Tscf Dto is null.");
				}
			} else {
				message = "ไม่พบ Terminal ID";
				log.info("Not found Terminal ID [" + e.getEnterpriseId() + "].");
				log.info("---------------------------------");
			}

			genTscf.setMessage(message);
//				getTscfList.add(genTscf);
//			}
		} else {
//			message = "ไม่พบ ข้อมูล สำหรับ Generate";
			log.info("Not found common parameter in system.");
		}

		return genTscf;
	}

	public TscfEnterpriseGenereate generateByEnterpriseIdAndTerminalId(GenerateParameter param) throws Exception {

		TscfEnterpriseGenereate genTscf = null;
		Terminal tm = param.getTerminal();
		Enterprise en = param.getEnterprise();
		log.info("Enterprise ID : " + en.getEnterpriseId() + "[" + en.getId() + "]");
		log.info("Terminal ID : " + tm.getTerminalId() + "[" + tm.getId() + "]");
		log.info("---------------------------------");
		List<CommonGenereateDetailInfo> comDList = comDDao.searchCommonDetailByTerminalId(en.getId(), null, tm.getId());
		List<IndividualGenerateDetailInfo> inDList = inDDao.searchIndividualDetailByTerminalId(tm.getId());

		if (comDList.size() > 0 && inDList.size() > 0) {
			List<String> parameterCodes = inDList.stream().map(IndividualGenerateDetailInfo::getParameterCode)
					.collect(Collectors.toList());

			comDList.removeIf(item -> parameterCodes.contains(item.getParameterCode()));
		}
//		log.info("----------------");
//		log.info("Common size : " + comDList.size());
//		log.info("Common {} : ", comDList);
//		log.info("----------------");
//		log.info("Individual size : " + inDList.size());
//		log.info("Individual {} : ", inDList);

		if (comDList.size() > 0 || inDList.size() > 0) {
			Integer enterpriseId = null;
			Integer merchantId = null;
			if (comDList.size() > 0) {
				enterpriseId = comDList.get(0).getEnterpriseIdPk();
				merchantId = comDList.get(0).getMerchantIdPk();
			} else if (inDList.size() > 0) {
				enterpriseId = inDList.get(0).getEnterpriseIdPk();
				merchantId = inDList.get(0).getMerchantIdPk();
			}
			genTscf = new TscfEnterpriseGenereate();
			genTscf.setEnterpriseIdPk(enterpriseId);
			genTscf.setMerchantIdPk(merchantId);
			TscfBuild gen = new TscfBuild(comDList, inDList);
			TscfTagData tags = gen.convertToTscfObject();

			genTscf.setTscfHResult(null);

			byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
			String tscfJson = new String(bytes, StandardCharsets.UTF_8);
			// --------------------------------------
			log.info(tscfJson);
			// --------------------------------------
			insertTscf(genTscf, param.getUpdateById(), tm.getId(), bytes);
			// --------------------------------------

		} else {
			log.info("Parameter is null Terminal ID=" + tm.getId());
		}
		return genTscf;
	}

	private TscfHDto createTscfHDto(int updatedId) throws Exception {
		TscfHDto h = new TscfHDto();
		String date = DateUtils.currentDate();
		h.setTerminalParameterVersion(date);
		h.setCreatedById(updatedId);
		h.setCreatedDate(new Date());
		return h;
	}

	private TscfDDto createTscfDDto(Integer tscfHId, int updateById, int terminalIdPk, byte[] tscf, String tscfJson)
			throws Exception {
		TscfDDto d = new TscfDDto();
		d.setTscfHId(tscfHId);
		d.setTscfTag(tscf);
		d.setTerminalId(terminalIdPk);
		d.setIsRequest(false);
		d.setRequestDatetime(null);
		d.setIsSuccess(false);
		d.setCreatedById(updateById);
		d.setCreatedDate(new Date());
		d.setTscfJson(tscfJson);
		return d;
	}

	@Transactional(rollbackFor = Exception.class)
	private void insertTscfByEnterprise(TscfEnterpriseGenereate genTscf, TscfHDto saveH, List<TscfDDto> saveDList)
			throws Exception {
		try {
			// ---------------------------[save Tscf H]--------------------
			TscfHDto resultH = tscfHDao.saveTscfH(saveH);
			log.info("Insert Tscf H successfully.");
			// ---------------------------[save Tscf D]--------------------
			saveDList.forEach(item -> item.setTscfHId(resultH.getId()));

			List<TscfDDto> resultD = tscfDDao.saveAllTscfD(saveDList);
			log.info("Insert Tscf D successfully.");
			// ------------------------------------------------------------
//			genTscf.setTscfHResult(resultH);
//			genTscf.setTscfDResult(resultD);
//			log.info("------------------ [insert Tscf H] :success----------------");
//			log.info(resultH.toString());
//			log.info("------------------ [insert Tscf D] :success----------------");
//			log.info(resultD.toString());
		} catch (Exception e) {
//			log.error("ERROR insertTscfByEnterprise:" + e.getMessage());
//			log.error("------------------ [insert Tscf H] :error----------------");
//			log.error(saveH.toString());
//			log.error("------------------ [insert Tscf D] :error----------------");
//			log.error(saveDList.toString());
			e.printStackTrace();
			throw e;
		}
		log.info("---------------------------------");
	}

	@Transactional(rollbackFor = Exception.class)
	private void insertTscf(TscfEnterpriseGenereate genTscf, int updateById, int terminalIdPk, byte[] tscf)
			throws Exception {
		TscfHDto h = new TscfHDto();
		String date = DateUtils.currentDate();
		h.setTerminalParameterVersion(date);
		h.setCreatedById(updateById);
		h.setCreatedDate(new Date());
		TscfHDto dtoH = tscfHDao.saveTscfH(h);
		log.info("Insert Tscf H successfully.");

		genTscf.setTscfHResult(dtoH);

		TscfDDto d = new TscfDDto();
		d.setTscfHId(dtoH.getId());
		d.setTscfTag(tscf);
		d.setTerminalId(terminalIdPk);
		d.setIsRequest(false);
		d.setRequestDatetime(null);
		d.setIsSuccess(false);
		d.setCreatedById(updateById);
		d.setCreatedDate(new Date());
		TscfDDto resultD = tscfDDao.saveTscfD(d);
		log.info("Insert Tscf D successfully.");
		List<TscfDDto> list = new ArrayList<TscfDDto>();
		list.add(resultD);
		genTscf.setTscfDResult(list);
		log.info("---------------------------------");
	}

//	public boolean isChkCommonParameter(GenerateParameter param) throws Exception {
//		List<CommonGenerateInfo> comHTerminalList = comDDao.searchGenerateCommonH(param.getEnterpriseIdPk(),
//				param.getMerchantIdPk(), param.getTerminalIdPk());
//		log.info(" data {}", comHTerminalList);
//		if (comHTerminalList.size() > 0) {
//			return true;
//		}
//		return false;
//	}
}
