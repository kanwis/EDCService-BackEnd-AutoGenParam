package com.cs.BatchAutoGenerateParameter.tscf;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TscfTagDetail {
	
	private String num;
	private String tag;
	private String length;
	private String value;
	private int count;
	
	public String toTscf() {
		StringBuffer append = new StringBuffer();
		if(tag!=null) {
			append.append(tag);
		}else {
			System.out.println("tag is null");
			return null;
		}
		if(length!=null) {
			append.append(" ");
			append.append(length);
		}else {
			System.out.println("length is null");
			return null;
		}
		if(value!=null) {
			append.append(" ");
			append.append(value);
		}else {
			System.out.println("value is null");
			return null;
		}
		return append.toString();
	}

}
