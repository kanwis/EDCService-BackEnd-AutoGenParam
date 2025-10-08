package com.cs.BatchAutoGenerateParameter.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.cs.BatchAutoGenerateParameter.dao.ICommonParameterDDao;
import com.cs.BatchAutoGenerateParameter.dao.ICommonParameterHDao;
import com.cs.BatchAutoGenerateParameter.dao.IIndividualParameterDDao;
import com.cs.BatchAutoGenerateParameter.dao.IIndividualParameterHDao;
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

	@Autowired
	private IIndividualParameterHDao individualHDao;

	@Autowired
	private ICommonParameterHDao commonHDao;

	public List<TscfEnterpriseGenereate> generateAll(GenerateParameter param) throws Exception {
		List<TscfEnterpriseGenereate> getTscfList = new ArrayList<TscfEnterpriseGenereate>();
		List<TscfTerminalGenereate> tscfTerminalList = null;
		TscfEnterpriseGenereate genTscf = null;
		Integer individualHId = null;

		List<CommonGenerateInfo> enterpriseList = comDDao.searchGenerateCommonHAll();
		String message = "";
		int i = 0;
		int sizeI = 0;
		int j = 0, sizeJ = 0;
		if (enterpriseList != null && enterpriseList.size() > 0) {
			sizeI = enterpriseList.size();
			log.info("Enterprise Size : " + sizeI);
			log.info("=================================");
			for (CommonGenerateInfo e : enterpriseList) {
				i++;
				log.info("(" + i + "/" + sizeI + ") Enterprise ID : " + e.getEnterpriseId() + "["
						+ e.getEnterpriseIdPk() + "]");
				TscfHDto saveH = createTscfHDto(param.getUpdateById());

				message = "";
				genTscf = new TscfEnterpriseGenereate();
				genTscf.setEnterpriseIdPk(e.getEnterpriseIdPk());
				genTscf.setEnterpriseId(e.getEnterpriseId());
				genTscf.setEnterpriseNameTh(e.getEnterpriseNameTh());
				genTscf.setMerchantIdPk(e.getMerchantIdPk());
				genTscf.setMerchantId(e.getMerchantId());
				TscfTerminalGenereate tscfTerminal = null;
				individualHId = null;
				j = 0;
				sizeJ = 0;

				List<CommonGenerateInfo> terminalList = comDDao.searchGenerateCommonH(e.getEnterpriseIdPk(), null, null);
				if (terminalList != null && terminalList.size() > 0) {
					tscfTerminalList = new ArrayList<TscfTerminalGenereate>();
					tscfTerminal = null;
					List<TscfDDto> saveDList = new ArrayList<TscfDDto>();
					List<Integer> individualHIdList = new ArrayList<Integer>();
					List<Integer> commHIdList = new ArrayList<Integer>();

					sizeJ = terminalList.size();
					log.info("Terminal Size : " + sizeJ);
					if (e.getBatchFlag()) {
						log.info("> Enterprise batch flag : " + e.getBatchFlag());
					} else {
						log.info("Enterprise batch flag : " + e.getBatchFlag());
					}

					for (CommonGenerateInfo t : terminalList) {
						j++;
						if (e.getBatchFlag()) {
							log.info("> (" + j + ") Terminal ID : " + t.getTerminalId() + "[" + t.getTerminalIdPk()
									+ "]");
						} else {
							log.info(
									"(" + j + ") Terminal ID : " + t.getTerminalId() + "[" + t.getTerminalIdPk() + "]");
						}

						log.info("---------------------------------");
						tscfTerminal = new TscfTerminalGenereate();
						tscfTerminal.setTerminalIdPk(t.getTerminalIdPk());
						tscfTerminal.setTerminalId(t.getTerminalId());
						tscfTerminalList.add(tscfTerminal);

						List<CommonGenereateDetailInfo> comDList = comDDao.searchGenerateCommonDetail(t.getCommonParameterHId(), t.getParameterVersionId());
						List<IndividualGenerateDetailInfo> inDList = inDDao.searchIndividualDetailByTerminalId(t.getTerminalIdPk());

						if (comDList.size() > 0 && inDList.size() > 0) {
							List<String> parameterCodes = inDList.stream()
									.map(IndividualGenerateDetailInfo::getParameterCode).collect(Collectors.toList());
							comDList.removeIf(item -> parameterCodes.contains(item.getParameterCode()));
						}
						
						if(inDList!=null && inDList.size() >0) {
							individualHId = inDList.get(0).getIndividualParameterHId();
						}
						if (Boolean.TRUE.equals(e.getBatchFlag())) {
							List<IndividualGenerateDetailInfo> inDListFalseList = inDList.stream().filter(info -> !info.getBatchFlag()).collect(Collectors.toList());
							if (inDListFalseList.size() == 0) {
								log.info("> Skip, Not found Individual batch flag=False, Enterprise ID :"
										+ e.getEnterpriseId() + ", Terminal ID : " + t.getTerminalId());
								log.info("----------------------------------------");
								continue;
							} else {
								// ---------------------------------
								// ---- For update Individual H ----
								individualHId = inDListFalseList.get(0).getIndividualParameterHId();
								individualHIdList.add(individualHId);
								// --------------------------------
								// Gen tscf
								TscfBuild gen = new TscfBuild(null, inDListFalseList);
								TscfTagData tags = gen.convertToTscfObject();
//								System.err.println(">>"+tags.toTscf());
								// -------------------------------
								byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
								String tscfJson = new String(bytes, StandardCharsets.UTF_8);
								// --------------------------------------
								log.info("\n" + tscfJson);
								// -------------------------------
								TscfDDto saveD = createTscfDDto(null, param.getUpdateById(), t.getTerminalIdPk(), bytes,
										tscfJson);
								saveDList.add(saveD);
								// --------------------------------------
							}
						} else {
							// ---------------------------------
							// ---- For update Common H ----
							Integer commonHId = e.getCommonParameterHId();
							commHIdList.add(commonHId);
							// --------------------------------
							individualHIdList.add(individualHId);
							// --------------------------------

							// Gen tscf
							TscfBuild gen = new TscfBuild(comDList, inDList);
							TscfTagData tags = gen.convertToTscfObject();
//							System.err.println(">>"+tags.toTscf());
							// -------------------------------
							byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
							String tscfJson = new String(bytes, StandardCharsets.UTF_8);
							// --------------------------------------
							log.info("\n" + tscfJson);
							// -------------------------------
							TscfDDto saveD = createTscfDDto(null, param.getUpdateById(), t.getTerminalIdPk(), bytes,
									tscfJson);
							saveDList.add(saveD);
							// --------------------------------------
						}
					}

					genTscf.setTerminalIdList(tscfTerminalList);

					if (saveDList.size() > 0) {
						// --------------------------------------
						insertTscfByEnterprise(genTscf, saveH, saveDList);
						// --------------------------------------
						getTscfList.add(genTscf);
					}
//					else {
//						log.info("Tscf Dto is null.");
//					}
					// -------------------
					if (individualHIdList.size() > 0) {
						try {
							List<Integer> individualHIdList_new = individualHIdList.stream().distinct().collect(Collectors.toList());
							individualHDao.updateBatchFlag(individualHIdList_new, param.getUpdateById());
							log.info("Update Individual H successfully.");
							log.info("---------------------------------");
						} catch (Exception ex) {
							log.info("ERROR Update Individual H batch flag :" + ex.getMessage());
						}

					}
					if (commHIdList.size() > 0) {
						try {
							List<Integer> commHIdList_new = commHIdList.stream().distinct()
									.collect(Collectors.toList());
							commonHDao.updateBatchFlag(commHIdList_new, param.getUpdateById());
							log.info("Update Common H successfully.");
							log.info("---------------------------------");
						} catch (Exception ex) {
							log.info("ERROR Update Common H batch flag :" + ex.getMessage());
						}

					}
					// -------------------
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

		List<TscfTerminalGenereate> tscfTerminalList = null;
		TscfEnterpriseGenereate genTscf = null;

		String message = "";
		int j = 0, sizeJ = 0;
		if (param.getEnterprise() != null) {
			Enterprise e = param.getEnterprise();
			log.info("Enterprise ID : " + e.getEnterpriseId() + "[" + e.getId() + "]");
			log.info("=================================");
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
				List<Integer> individualHIdList = new ArrayList<Integer>();
				List<Integer> commHIdList = new ArrayList<Integer>();
				
				sizeJ = terminalList.size();
				log.info("Terminal Size : " + sizeJ);
				if (terminalList.get(0).getBatchFlag()) {
					log.info("> Enterprise batch flag : " + terminalList.get(0).getBatchFlag());
				} else {
					log.info("Enterprise batch flag : " + terminalList.get(0).getBatchFlag());
				}
				for (CommonGenerateInfo t : terminalList) {
					j++;
					if (t.getBatchFlag()) {
						log.info("> (" + j + ") Terminal ID : " + t.getTerminalId() + "[" + t.getTerminalIdPk() + "]");
					} else {
						log.info("(" + j + ") Terminal ID : " + t.getTerminalId() + "[" + t.getTerminalIdPk() + "]");
					}

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

					if (Boolean.TRUE.equals(t.getBatchFlag())) {
						List<IndividualGenerateDetailInfo> inDListFalseList = inDList.stream()
								.filter(info -> !info.getBatchFlag()).collect(Collectors.toList());
						if (inDListFalseList.size() == 0) {
							log.info("> Skip, Not found Individual batch flag=False, Enterprise ID :"
									+ e.getEnterpriseId() + ", Terminal ID : " + t.getTerminalId());
							log.info("----------------------------------------");
							continue;
						} else {
							// ---------------------------------
							// ---- For update Individual H ----
							Integer individualHId = inDListFalseList.get(0).getIndividualParameterHId();
							individualHIdList.add(individualHId);
							// --------------------------------
							// Gen tscf
							TscfBuild gen = new TscfBuild(null, inDListFalseList);
							TscfTagData tags = gen.convertToTscfObject();
//							System.err.println(">>"+tags.toTscf());
							// -------------------------------
							byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
							String tscfJson = new String(bytes, StandardCharsets.UTF_8);
							// --------------------------------------
							log.info("\n" + tscfJson);
							// -------------------------------
							TscfDDto saveD = createTscfDDto(null, param.getUpdateById(), t.getTerminalIdPk(), bytes,
									tscfJson);
							saveDList.add(saveD);
							// --------------------------------------
						}
					} else {
						// ---------------------------------
						// ---- For update Common H ----
						Integer commonHId = t.getCommonParameterHId();
						commHIdList.add(commonHId);
						// --------------------------------
						Integer individualHId = inDList.get(0).getIndividualParameterHId();
						individualHIdList.add(individualHId);
						// --------------------------------

						// Gen tscf
						TscfBuild gen = new TscfBuild(comDList, inDList);
						TscfTagData tags = gen.convertToTscfObject();
//						System.err.println(">>"+tags.toTscf());
						// -------------------------------
						byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
						String tscfJson = new String(bytes, StandardCharsets.UTF_8);
						// --------------------------------------
						log.info("\n" + tscfJson);
						// -------------------------------
						TscfDDto saveD = createTscfDDto(null, param.getUpdateById(), t.getTerminalIdPk(), bytes,
								tscfJson);
						saveDList.add(saveD);
						// --------------------------------------
					}
				}
				genTscf.setTerminalIdList(tscfTerminalList);

				if (saveDList.size() > 0) {
					// --------------------------------------
					insertTscfByEnterprise(genTscf, saveH, saveDList);
					// --------------------------------------
//						getTscfList.add(genTscf);
				}
//				else {
//					log.info("Tscf Dto is null.");
//				}
				// -------------------
				if (individualHIdList.size() > 0) {
					try {
						List<Integer> individualHIdList_new = individualHIdList.stream().distinct()
								.collect(Collectors.toList());
						individualHDao.updateBatchFlag(individualHIdList_new, param.getUpdateById());
						log.info("Update Individual H successfully.");
						log.info("---------------------------------");
					} catch (Exception ex) {
						log.info("ERROR Update Individual H batch flag :" + ex.getMessage());
					}

				}
				if (commHIdList.size() > 0) {
					try {
						List<Integer> commHIdList_new = commHIdList.stream().distinct().collect(Collectors.toList());
						commonHDao.updateBatchFlag(commHIdList_new, param.getUpdateById());
						log.info("Update Common H successfully.");
						log.info("---------------------------------");
					} catch (Exception ex) {
						log.info("ERROR Update Common H batch flag :" + ex.getMessage());
					}

				}
				// -------------------
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
		Boolean batchFlag = null;
		Integer commonHId = null;
		Integer individualHId = null;
		
		
		log.info("Enterprise ID : " + en.getEnterpriseId() + "[" + en.getId() + "], "+"Terminal ID : " + tm.getTerminalId() + "[" + tm.getId() + "]");
		log.info("=================================");
		
		List<CommonGenereateDetailInfo> comDList = comDDao.searchCommonDetailByTerminalId(en.getId(), null, tm.getId());
		if(comDList !=null && comDList.size() >0) {
	
			List<IndividualGenerateDetailInfo> inDList = inDDao.searchIndividualDetailByTerminalId(tm.getId());
	
			if (comDList.size() > 0 && inDList.size() > 0) {
				List<String> parameterCodes = inDList.stream().map(IndividualGenerateDetailInfo::getParameterCode).collect(Collectors.toList());
				comDList.removeIf(item -> parameterCodes.contains(item.getParameterCode()));
			}
	
			Integer enterpriseId = null;
			Integer merchantId = null;
			
			if (comDList.size() > 0) {
				enterpriseId = comDList.get(0).getEnterpriseIdPk();
				merchantId = comDList.get(0).getMerchantIdPk();
				batchFlag = comDList.get(0).getBatchFlag();
				commonHId = comDList.get(0).getCommonParameterHId();
			}
			if(inDList.size() > 0) {
				individualHId = inDList.get(0).getIndividualParameterHId();
			}
				
			if (batchFlag) {
				log.info("> Enterprise batch flag : " + batchFlag);
			} else {
				log.info("Enterprise batch flag : " + batchFlag);
			}
			
			log.info("---------------------------------");
			genTscf = new TscfEnterpriseGenereate();
			genTscf.setEnterpriseIdPk(enterpriseId);
			genTscf.setMerchantIdPk(merchantId);
			//-------------------------------------------------
			if (Boolean.TRUE.equals(batchFlag)) {
				List<IndividualGenerateDetailInfo> inDListFalseList = inDList.stream().filter(info -> !info.getBatchFlag()).collect(Collectors.toList());
				if (inDListFalseList.size() == 0) {
					log.info("> Skip, Not found Individual batch flag=False, Enterprise ID :"+  en.getEnterpriseId() + ", Terminal ID : " + tm.getTerminalId());
					log.info("----------------------------------------");
				} else {
					// Gen tscf
					TscfBuild gen = new TscfBuild(null, inDListFalseList);
					TscfTagData tags = gen.convertToTscfObject();
					// -------------------------------
					byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
					String tscfJson = new String(bytes, StandardCharsets.UTF_8);
					// --------------------------------------
					log.info("\n" + tscfJson);
					// --------------------------------------
					insertTscf(genTscf, param.getUpdateById(), tm.getId(), bytes);
					
					List<Integer> individualHIdList_new = new ArrayList<>(Arrays.asList(individualHId));
					individualHDao.updateBatchFlag(individualHIdList_new, param.getUpdateById());
					// --------------------------------------
				}
			} else {

				// Gen tscf
				TscfBuild gen = new TscfBuild(comDList, inDList);
				TscfTagData tags = gen.convertToTscfObject();
				// -------------------------------
				byte[] bytes = tags.toTscf().getBytes(StandardCharsets.UTF_8);
				String tscfJson = new String(bytes, StandardCharsets.UTF_8);
				// --------------------------------------
				log.info("\n" + tscfJson);
				// --------------------------------------
				insertTscf(genTscf, param.getUpdateById(), tm.getId(), bytes);
				
				List<Integer> individualHIdList_new = new ArrayList<>(Arrays.asList(individualHId));
				List<Integer> commHIdList_new = new ArrayList<>(Arrays.asList(commonHId));
				
				individualHDao.updateBatchFlag(individualHIdList_new, param.getUpdateById());
				
				commonHDao.updateBatchFlag(commHIdList_new, param.getUpdateById());
				// --------------------------------------
			}
			//-------------------------------------------------
		} else {
			log.info("Not found common parameter in system.");
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
