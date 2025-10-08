package com.cs.BatchAutoGenerateParameter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.BatchAutoGenerateParameter.dao.ICommonParameterDDao;
import com.cs.BatchAutoGenerateParameter.dto.CommonGenerateInfo;
import com.cs.BatchAutoGenerateParameter.dto.CommonGenereateDetailInfo;
import com.cs.BatchAutoGenerateParameter.repositories.ICommonParameterDJPARepository;
import com.cs.BatchAutoGenerateParameter.utils.DateUtils;

@Service
public class CommonParameterDDaoImpl implements ICommonParameterDDao {
	Logger logger = LoggerFactory.getLogger(CommonParameterDDaoImpl.class);

	@Autowired
	private ICommonParameterDJPARepository repoD;

//	@Autowired
//	private ICommonParameterDMapper mapper;

//	@Override
//	public CommonParameterDDto save(CommonParameterDDto commonParameterDDto) throws Exception {
//		CommonParameterD save = mapper.toEntity(commonParameterDDto);
//		CommonParameterD result = repoD.save(save);
//		CommonParameterDDto resultReturn = mapper.toDto(result);
//
//		logger.info("save mst_commonParameterD successful.");
//		return resultReturn;
//	}

//	@Override
//	public List<CommonParameterData> findByVersionIdAndHId(Integer parameterVersionId, Integer commonParameterHId)
//			throws Exception {
//		List<Object[]> objectsItem = repoD.findByVersionIdAndHId(parameterVersionId, commonParameterHId);
//		List<CommonParameterData> listItemData = new ArrayList<CommonParameterData>();
//
//		if (objectsItem != null) {
//
//			for (Object[] rowItem : objectsItem) {
//
//				CommonParameterData cmpData = new CommonParameterData();
//				cmpData.setTerminalParameterVIdPk((int) rowItem[2]);
//				cmpData.setTerminalParameterHIdPk((int) rowItem[1]);
//				cmpData.setTerminalParameterDIdPk((int) rowItem[0]);
//				cmpData.setParameterCode(rowItem[15] != null ? rowItem[15].toString() : null);
//				cmpData.setPackTypeId((int) rowItem[16]);
//				cmpData.setParameterLength(rowItem[17] != null ? rowItem[17].toString() : null);
//				if (rowItem[4] != null) {
//					cmpData.setParameterValue(rowItem[4] != null ? rowItem[4].toString() : null);
//				} else {
//					cmpData.setParameterValue(rowItem[18] != null ? rowItem[18].toString() : null);
//				}
//				cmpData.setDescription(rowItem[19] != null ? rowItem[19].toString() : null);
//				cmpData.setPackTypeName(rowItem[14] != null ? rowItem[14].toString() : null);
//				cmpData.setApplicationStatus((int) rowItem[5]);
//				cmpData.setRejectReason(rowItem[6] != null ? rowItem[6].toString() : null);
//				cmpData.setApproveById((Integer) rowItem[7] != null ? (Integer) rowItem[7] : null);
//				cmpData.setApproveDate(rowItem[8] != null ? DateUtils.convertToDate(rowItem[8]) : null);
//				cmpData.setCreatedById((int) rowItem[10]);
//				cmpData.setCreatedDate(rowItem[11] != null ? DateUtils.convertToDate(rowItem[11]) : null);
//				cmpData.setUpdatedById((int) rowItem[12]);
//				cmpData.setUpdatedDate(rowItem[13] != null ? DateUtils.convertToDate(rowItem[13]) : null);
//				cmpData.setTerminalParameterIdPk((int) rowItem[3]);
//				listItemData.add(cmpData);
//			}
//
//		}
//		return listItemData;
//	}

	@Override
	public List<CommonGenereateDetailInfo> searchCommonDetailByTerminalId(Integer enterpriseId, Integer merchantId,
			Integer terminalId) {
		List<CommonGenereateDetailInfo> list = new ArrayList<CommonGenereateDetailInfo>();
		List<Object[]> objectsItem = repoD.searchDetailByTerminalId(enterpriseId, merchantId, terminalId);
		if (objectsItem != null) {

			for (Object[] row : objectsItem) {
				CommonGenereateDetailInfo dto = new CommonGenereateDetailInfo();
				dto.setCommonDId(row[0] != null ? (int) row[0] : null);
				dto.setCommonParameterHId(row[1] != null ? (int) row[1] : null);
				dto.setParameterVersionId(row[2] != null ? (int) row[2] : null);
				dto.setParameterId(row[3] != null ? (int) row[3] : null);
				dto.setParameterValue(row[4] != null ? (String) row[4] : null);
				dto.setApplicationStatus(row[5] != null ? (int) row[5] : null);
				dto.setRejectReason(row[6] != null ? (String) row[6] : null);
				dto.setApproveById(row[7] != null ? (int) row[7] : null);
				dto.setApproveDate(row[8] != null ? DateUtils.convertToDate(row[8]) : null);
				dto.setActionForm(row[9] != null ? (String) row[9] : null);
				dto.setCreatedById(row[10] != null ? (int) row[10] : null);
				dto.setCreatedDate(row[11] != null ? DateUtils.convertToDate(row[11]) : null);
				dto.setUpdatedById(row[12] != null ? (int) row[12] : null);
				dto.setUpdatedDate(row[13] != null ? DateUtils.convertToDate(row[13]) : null);
				dto.setParameterCode(row[14] != null ? (String) row[14] : null);
				dto.setEnterpriseIdPk(row[15] != null ? (int) row[15] : null);
				dto.setMerchantIdPk(row[16] != null ? (int) row[16] : null);
				dto.setBatchFlag(row[17] != null ? (Boolean) row[17] : null);
				list.add(dto);
			}

		}
		return list;
	}

	@Override
	public List<CommonGenerateInfo> searchGenerateCommonH(Integer enterpriseId, Integer merchantId, Integer terminalId)
			throws Exception {
		List<CommonGenerateInfo> infoList = new ArrayList<CommonGenerateInfo>();
		List<Object[]> objectsItems = repoD.searchGenerateCommonH(enterpriseId, merchantId, terminalId);
		if (objectsItems != null) {
			for (Object[] row : objectsItems) {
				CommonGenerateInfo info = new CommonGenerateInfo();
				info.setCommonParameterHId(row[0] != null ? (int) row[0] : null);
				info.setParameterVersionId(row[1] != null ? (int) row[1] : null);
				info.setParameterVersion(row[2] != null ? (int) row[2] : null);
				info.setEnterpriseIdPk(row[3] != null ? (int) row[3] : null);
				info.setMerchantIdPk(row[4] != null ? (int) row[4] : null);
				info.setTerminalIdPk(row[5] != null ? (int) row[5] : null);
				info.setBatchFlag(row[6] != null ? (Boolean) row[6] : null);
				info.setEnterpriseId(row[7] != null ? (String) row[7] : null);
				info.setMerchantId(row[8] != null ? (String) row[8] : null);
				info.setTerminalId(row[9] != null ? (String) row[9] : null);
				infoList.add(info);
			}
		}
		return infoList;
	}

	@Override
	public List<CommonGenereateDetailInfo> searchGenerateCommonDetail(Integer parameterHId, Integer parameterVersionId)
			throws Exception {

		List<CommonGenereateDetailInfo> infoList = new ArrayList<CommonGenereateDetailInfo>();
		List<Object[]> objectsItem = repoD.searchGenerateCommonDetail(parameterHId, parameterVersionId);
		if (objectsItem != null) {
			for (Object[] row : objectsItem) {
				CommonGenereateDetailInfo d = new CommonGenereateDetailInfo();
				d.setCommonDId(row[0] != null ? (int) row[0] : null);
				d.setCommonParameterHId(row[1] != null ? (int) row[1] : null);
				d.setParameterVersionId(row[2] != null ? (int) row[2] : null);
				d.setParameterId(row[3] != null ? (int) row[3] : null);
				d.setParameterValue(row[4] != null ? (String) row[4] : null);
				d.setApplicationStatus(row[5] != null ? (int) row[5] : null);
				d.setRejectReason(row[6] != null ? (String) row[6] : null);
				d.setApproveById(row[7] != null ? (int) row[7] : null);
				d.setApproveDate(row[8] != null ? DateUtils.convertToDate(row[8]) : null);
				d.setActionForm(row[9] != null ? (String) row[9] : null);
				d.setCreatedById(row[10] != null ? (int) row[10] : null);
				d.setCreatedDate(row[11] != null ? DateUtils.convertToDate(row[11]) : null);
				d.setUpdatedById(row[12] != null ? (int) row[12] : null);
				d.setUpdatedDate(row[13] != null ? DateUtils.convertToDate(row[13]) : null);
				d.setParameterCode(row[14] != null ? (String) row[14] : null);
				infoList.add(d);
			}
		}

		return infoList;
	}

//	@Override
//	public void saveList(List<CommonParameterDDto> comParameterDDtoList) throws Exception {
//
//		List<CommonParameterD> audits = new ArrayList<>();
//
//		for (CommonParameterDDto d : comParameterDDtoList) {
//			CommonParameterD tmpSave = mapper.toEntity(d);
//			System.err.println("--------------------------------");
//			System.out.println("save=" + tmpSave.toString());
//			audits.add(tmpSave);
//		}
//		repoD.saveAll(audits);
//		logger.info("save mst_commonParameterD successful.");
//
//	}

	@Override
	public List<CommonGenerateInfo> searchGenerateCommonHAll() throws Exception {

		List<CommonGenerateInfo> infoList = new ArrayList<CommonGenerateInfo>();
		List<Object[]> objectsItems = repoD.searchGenerateCommonHAll();
		if (objectsItems != null) {
			for (Object[] row : objectsItems) {
				CommonGenerateInfo info = new CommonGenerateInfo();
				info.setCommonParameterHId(row[0] != null ? (int) row[0] : null);
				info.setParameterVersionId(row[1] != null ? (int) row[1] : null);
				info.setParameterVersion(row[2] != null ? (int) row[2] : null);
				info.setEnterpriseIdPk(row[3] != null ? (int) row[3] : null);
				info.setMerchantIdPk(row[4] != null ? (int) row[4] : null);
				info.setBatchFlag(row[5] != null ? (Boolean) row[5] : null);
				info.setEnterpriseId(row[6] != null ? (String) row[6] : null);
				info.setMerchantId(row[7] != null ? (String) row[7] : null);
				info.setEnterpriseNameTh(row[8] != null ? (String) row[8] : null);
				infoList.add(info);
			}
		}
		return infoList;

	}

}
