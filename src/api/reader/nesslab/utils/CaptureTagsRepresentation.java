package api.reader.nesslab.utils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import api.reader.nesslab.exceptions.SessionFullException;
/**
 * This class centralizes all functions for to encapsulate 
 * tags RFID in Objects or JSON representations. 
 * */
public class CaptureTagsRepresentation {

	private static List<TagAntenna> tags = new ArrayList<>();
	private static final String CODE_SUCESS_INVENTORY = "9C01";
	private static final String CODE_ERRO_DUPLICATION_EXECUTION = "9C91";
	private static final String CODE_ERRO_STOP_FORCE = "9S00";
	
	
	private static String jsonRepresentation = "";
	private static String jsonTagUnique = "";

	public static void getStringRepresentation(String response)
			throws UnknownHostException, IOException, SessionFullException {
		TagAntenna tmp;
		String responseHost = response;
		if (responseHost.equals(CODE_SUCESS_INVENTORY) 
				|| responseHost.equals(CODE_ERRO_DUPLICATION_EXECUTION)
				|| responseHost.equals(CODE_ERRO_STOP_FORCE)) {
			throw new SessionFullException("Session memory is full.");
		} else {
			tmp = new TagAntenna(responseHost);
			if (tags.contains(tmp)) {
				int index = tags.indexOf(tmp);
				tmp = tags.get(index);
				tmp.setCountReader(tmp.getCountReader() + 1L);
			} else {
				tags.add(tmp);
				System.out.println("Antenna: " + tmp.getAntenna() + " TAG: " + tmp.getTagRFID());
			}
		}
	}

	public static void getObjectRepresentation(String response)
			throws UnknownHostException, IOException, SessionFullException {
		TagAntenna tmp;
		String responseHost = response;
		tmp = new TagAntenna(responseHost);
		if (responseHost.equals(CODE_SUCESS_INVENTORY) 
				|| responseHost.equals(CODE_ERRO_DUPLICATION_EXECUTION)
				|| responseHost.equals(CODE_ERRO_STOP_FORCE)) {
			throw new SessionFullException("Session memory is full.");
		} else {
			if (tags.contains(tmp)) {
				int index = tags.indexOf(tmp);
				tmp = tags.get(index);
				tmp.setCountReader(tmp.getCountReader() + 1L);
				jsonTagUnique= "";
			} else {
				tags.add(tmp);
				jsonTagUnique = ExportJsonFormat.exportTag(tmp);
				jsonRepresentation = ExportJsonFormat.exportListTags(tags);
			}
		}

	}
	
	public static void clearMemory(int timeSecondsRange){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				tags.clear();
				
			}
		}, 0, timeSecondsRange*1000L);
	}

	public static String getJsonRepresentation() {
		return jsonRepresentation;
	}

	public static List<TagAntenna> getTags() {
		return tags;
	}
	
	public static boolean haveNewTag(){
		return !jsonTagUnique.equals("");
	}

	public static String getJsonTagUnique() {
		return jsonTagUnique;
	}

	public static void setJsonTagUnique(String jsonTagUnique) {
		CaptureTagsRepresentation.jsonTagUnique = jsonTagUnique;
	}
	
	

}
