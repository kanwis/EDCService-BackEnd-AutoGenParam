package com.cs.BatchAutoGenerateParameter.tscf;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.cs.BatchAutoGenerateParameter.constants.Constants;
import com.cs.BatchAutoGenerateParameter.dto.CommonGenereateDetailInfo;
import com.cs.BatchAutoGenerateParameter.dto.IndividualGenerateDetailInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;

public class TscfBuild {
	
	@Getter@Setter
	private List<CommonGenereateDetailInfo> commonList;
	@Getter@Setter
	private List<IndividualGenerateDetailInfo> indiviList;
	private TscfTagData tagData;
	
	private static final Charset TIS620 = Charset.forName("TIS-620");
	
	public TscfTagData convertToTscfObject() {
		System.out.println("-------------[convertToTscf]----------------");
		List<TscfTagDetail> tags = new ArrayList<TscfTagDetail>();
		int total = 36;
		if(commonList!=null) {
			List<TscfTagDetail> list = commonToTscf();
			tags.addAll(list);
		}
		if(indiviList!=null) {
			List<TscfTagDetail> list = individualToTscf();
			tags.addAll(list);
		}
		if(tagData==null) {
			tagData = new TscfTagData();
		}
		
		tagData.setHeaderProtocalVersion(Constants.TSCF.PROTOCAL_VERSION);//2
		tagData.setHeaderTypeName(Constants.TSCF.TYPENAME);//16
		tagData.setHeaderCrc(Constants.TSCF.CRC);//4
		tagData.setHeaderSessionId(Constants.TSCF.SESSION_ID);//8
		tagData.setHeaderTripCount(Constants.TSCF.TRIP_COUNT);//1
		tagData.setStatusCount(Constants.TSCF.STATUS_COUNT);//1
		tagData.setAPDUCount(Constants.TSCF.APDU_COUNT);//1
		tagData.setDataCount(toHexEncode(tags.size(),2));//1
//		System.out.println("data count >>"+toHexEncode(tags.size(),2));
//		
		for(int i=1;i<=tags.size();i++) {
			
			String index = String.valueOf(i)+"/"+tags.size();
			tags.get(i-1).setNum(index);
			total+=tags.get(i-1).getCount();
			System.out.println(tags.get(i-1).toString());
		}
//		System.out.println("length summary >>"+total);
		tagData.setTags(tags);
		tagData.setHeaderTotalLength(toHexEncode(total,4));
//		System.err.println("---------------------");
//		System.out.println(tagData.toTscf());
		return tagData;
	}
	
	public String convertToTscfFormat() {
		System.out.println(tagData.toString());
//		String xx = g.toJson(tagData.toString());
//		
		Gson gson = new GsonBuilder().serializeNulls().create();
		String text = gson.toJson(tagData);
		System.out.println(text);
//		System.out.println("--------------------");
//		Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
//        String prettyJson = gsonPretty.toJson(tagData);
//        System.out.println(prettyJson);
		return text;
	}
	
	private List<TscfTagDetail> individualToTscf() {
		List<TscfTagDetail> tags =  null;
		if(indiviList!=null) {
			tags = new ArrayList<TscfTagDetail>();
			
			for(int i=0;i<indiviList.size();i++) {
				TscfTagDetail detail = genIndividual(indiviList.get(i),i,indiviList.size());
				tags.add(detail);
			}
		}
		return tags;
	}
	
	private List<TscfTagDetail> commonToTscf() {
		List<TscfTagDetail> tags =  null;
		if(commonList!=null) {
			tags = new ArrayList<TscfTagDetail>();
			
			for(int i=0;i<commonList.size();i++) {
				TscfTagDetail detail = genCommon(commonList.get(i),i,commonList.size());
				tags.add(detail);
			}
		}
		return tags;
	}
	
	private static String toHexEncode(int input,int digit) {
		String format = "%0"+ String.valueOf(digit)+"x";
		String hex = String.format(format, input); 
	    String result = addSpace(hex);
		return result;
	}
	
	private static String addSpace(String input) {
		if(input==null) return input;
		
	  StringBuilder spacedHex = new StringBuilder();
	    for (int i = 0; i < input.length(); i += 2) {
	        if (i > 0) {
	            spacedHex.append(" ");
	        }
	        spacedHex.append(input.substring(i, i + 2));
	    }
	    String result = spacedHex.toString();
	    return result;
	}
	
	private TscfTagDetail genCommon(CommonGenereateDetailInfo info, int i, int size) {
		String index = String.valueOf(i)+"/"+size;
		byte[] tisBytes = encodeTIS620(info.getParameterValue());
		tisBytes = append00(tisBytes);
		
		String length = toHexEncode(tisBytes.length,4);
		String value = toHex(tisBytes);
		TscfTagDetail d = new TscfTagDetail(index, addSpace(info.getParameterCode()), length, value, tisBytes.length+4);
		
		return d;
	}
	private TscfTagDetail genIndividual(IndividualGenerateDetailInfo info, int i, int size) {
		String index = String.valueOf(i)+"/"+size;
		byte[] tisBytes = encodeTIS620(info.getParameterValue());
//		tisBytes = append00(tisBytes);
		
		String length = toHexEncode(tisBytes.length,4);
		String value = toHex(tisBytes);
		TscfTagDetail d = new TscfTagDetail(index, addSpace(info.getParameterCode()), length, value, tisBytes.length+4);
		
		return d;
	}
	
	private byte[] append00(byte[] tisBytes) {
		byte data = 0x00;
		byte[] newArray = new byte[tisBytes.length + 1];
		System.arraycopy(tisBytes, 0, newArray, 0, tisBytes.length);
		newArray[newArray.length - 1] = data;
		tisBytes = newArray;
//		for(byte b : tisBytes){
//		    System.out.print(b + " "); // output: 1 2 3 4
//		}
		return tisBytes;
	}
	
	public static String toHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes)
			sb.append(String.format("%02x", b & 0xFF)).append(' ');
		return sb.toString().trim();
	}
	
	public static byte[] encodeTIS620(String s) {
		return s.getBytes(TIS620);
	}

	public TscfBuild(List<CommonGenereateDetailInfo> commonList, List<IndividualGenerateDetailInfo> indiviList) {
		super();
		this.commonList = commonList;
		this.indiviList = indiviList;
	}

}
