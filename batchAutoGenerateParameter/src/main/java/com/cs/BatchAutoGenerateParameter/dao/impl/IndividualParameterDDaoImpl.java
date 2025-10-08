package com.cs.BatchAutoGenerateParameter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.BatchAutoGenerateParameter.dao.IIndividualParameterDDao;
import com.cs.BatchAutoGenerateParameter.dto.IndividualGenerateDetailInfo;
import com.cs.BatchAutoGenerateParameter.dto.IndividualGenerateInfo;
import com.cs.BatchAutoGenerateParameter.repositories.IIndividualParameterDJPARepository;
import com.cs.BatchAutoGenerateParameter.utils.DateUtils;

@Service
public class IndividualParameterDDaoImpl implements IIndividualParameterDDao {

	@Autowired
	private IIndividualParameterDJPARepository repo;

	@Override
	public List<IndividualGenerateDetailInfo> searchIndividualDetailByTerminalId(Integer terminalId) {
		List<Object[]> objectsItem = repo.searchDetailByTerminalId(terminalId);
		List<IndividualGenerateDetailInfo> list = new ArrayList<IndividualGenerateDetailInfo>();

		if (objectsItem != null) {
			for (Object[] row : objectsItem) {
				IndividualGenerateDetailInfo dto = new IndividualGenerateDetailInfo();
				dto.setIndividualDId(row[0] != null ? (int) row[0] : null);
				dto.setIndividualParameterHId(row[1] != null ? (int) row[1] : null);
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
	public List<IndividualGenerateInfo> searchGenerateIndividualHByTerminalId(Integer terminalId) throws Exception {
		List<IndividualGenerateInfo> infoList = new ArrayList<IndividualGenerateInfo>();
		List<Object[]> objectsItems = repo.searchGenerateIndividualHByTerminalId(terminalId);
		if (objectsItems != null) {
			for (Object[] row : objectsItems) {
				IndividualGenerateInfo info = new IndividualGenerateInfo();
				info.setIndividualParameterHId(row[0] != null ? (int) row[0] : null);
				info.setParameterVersionId(row[1] != null ? (int) row[1] : null);
				info.setParameterVersion(row[2] != null ? (String) row[2] : null);
				info.setEnterpirseIdPk(row[3] != null ? (int) row[3] : null);
				info.setMerchantIdPk(row[4] != null ? (int) row[4] : null);
				info.setTerminalIdPk(row[5] != null ? (int) row[5] : null);
				info.setBatchFlag(row[6] != null ? (Boolean) row[6] : null);
				infoList.add(info);
			}
		}
		return infoList;
	}

	@Override
	public List<IndividualGenerateDetailInfo> searchGeneratIndividualDetail(int parameterHId, int parameterVersionId)
			throws Exception {
		List<IndividualGenerateDetailInfo> infoList = new ArrayList<IndividualGenerateDetailInfo>();
		List<Object[]> objectsItem = repo.searchGenerateIndividualDetail(parameterHId, parameterVersionId);
		if (objectsItem != null) {
			for (Object[] row : objectsItem) {
				IndividualGenerateDetailInfo d = new IndividualGenerateDetailInfo();
				d.setIndividualDId(row[0] != null ? (int) row[0] : null);
				d.setIndividualParameterHId(row[1] != null ? (int) row[1] : null);
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

}
