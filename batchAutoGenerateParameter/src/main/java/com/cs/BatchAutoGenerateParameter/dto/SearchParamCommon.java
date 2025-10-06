package com.cs.BatchAutoGenerateParameter.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchParamCommon {
	private Integer tmpIdPk;
	private Integer enIdPk;
	private List<Integer> applicationStatusList;
	private Integer enterpriseId;
}
