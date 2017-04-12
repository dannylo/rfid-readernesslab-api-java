package utils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import exceptions.SessionFullException;

public class CaptureTagsRepresentation {

	private static List<TagAntenna> tags = new ArrayList<>();
	private static final String CODE_SUCESS_INVENTORY = "9C01";
	private static String jsonRepresentation = ""; 
	
	public static void getStringRepresentation(String response) throws UnknownHostException, IOException, 
	SessionFullException{
		TagAntenna tmp;
		String responseHost = response;
		if(responseHost.equals(CODE_SUCESS_INVENTORY)){
			throw new SessionFullException("Session memory is full.");
		} else {
			tmp = new TagAntenna(responseHost);			
			if (tags.contains(tmp)) {
				int index = tags.indexOf(tmp);
				tmp = tags.get(index);
				tmp.setCountReader(tmp.getCountReader()+ 1L);
			} else {
				tags.add(tmp);
				System.out.println("Antenna: "+ tmp.getAntenna() + " TAG: " + tmp.getTagRFID());
			}
		} 
	}

	public static void getObjectRepresentation(String response) throws UnknownHostException, IOException, 
	SessionFullException{
		TagAntenna tmp;
		String responseHost = response;
		tmp = new TagAntenna(responseHost);
		if(response.equals(CODE_SUCESS_INVENTORY)){
			throw new SessionFullException("Session memory is full.");
		} else {
			if (tags.contains(tmp)) {
				int index = tags.indexOf(tmp);
				tmp = tags.get(index);
				tmp.setCountReader(tmp.getCountReader()+ 1L);
			} else {
				tags.add(tmp);
				jsonRepresentation = ExportJsonFormat.export(tags);
			}
		}
		
	}
	
	public static String getJsonRepresentation(){
		return jsonRepresentation;
	}
	
	public static List<TagAntenna> getTags(){
		return tags;
	}

}
