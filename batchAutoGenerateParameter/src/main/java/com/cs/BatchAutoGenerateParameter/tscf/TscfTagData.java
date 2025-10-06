package com.cs.BatchAutoGenerateParameter.tscf;


import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TscfTagData {

	private String headerTotalLength;
	private String headerProtocalVersion;
	private String headerTypeName;
	private String headerCrc;
	private String headerSessionId;
	private String headerTripCount;
	private String statusCount;
	private String APDUCount;
	private String dataCount;
	private List<TscfTagDetail> tags;

	public String toTscf() {
		StringBuffer append = new StringBuffer();
		if (headerTotalLength != null) {
			append.append(headerTotalLength);
		} else {
			System.out.println("headerTotalLength is null");
			return null;
		}
		if (headerProtocalVersion != null) {
			append.append(" ");
			append.append(headerProtocalVersion);
		} else {
			System.out.println("headerProtocalVersion is null");
			return null;
		}
		if (headerTypeName != null) {
			append.append(" ");
			append.append(headerTypeName);
		} else {
			System.out.println("headerTypeName is null");
			return null;
		}
		if (headerCrc != null) {
			append.append(" ");
			append.append(headerCrc);
		} else {
			System.out.println("headerCrc is null");
			return null;
		}
		if (headerSessionId != null) {
			append.append(" ");
			append.append(headerSessionId);
		} else {
			System.out.println("headerSessionId is null");
			return null;
		}
		if (headerTripCount != null) {
			append.append(" ");
			append.append(headerTripCount);
		} else {
			System.out.println("headerTripCount is null");
			return null;
		}
		if (statusCount != null) {
			append.append(" ");
			append.append(statusCount);
		} else {
			System.out.println("statusCount is null");
			return null;
		}
		if (APDUCount != null) {
			append.append(" ");
			append.append(APDUCount);
		} else {
			System.out.println("APDUCount is null");
			return null;
		}
		if (dataCount != null) {
			append.append(" ");
			append.append(dataCount);
		} else {
			System.out.println("dataCount is null");
			return null;
		}

		if (tags != null && tags.size() > 0) {
			for (TscfTagDetail t : tags) {
				append.append(" ");
				append.append(t.toTscf());
			}
		} else {
			System.out.println("tags is null");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		String text = append.toString();
		for (int i = 0; i < text.length(); i += 33) {
            int end = Math.min(i + 33, text.length());
            sb.append(text, i, end);
            if (end < text.length()) { // ยังไม่จบ string
                sb.append("\n");
            }
        }

		return sb.toString();
	}

}
