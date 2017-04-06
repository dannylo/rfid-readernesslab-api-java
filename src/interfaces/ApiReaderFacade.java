package interfaces;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import exceptions.SessionFullException;
import utils.TagAntenna;

public interface ApiReaderFacade {

	void executeAction(Command command) throws UnknownHostException, IOException;
	
	String getResponse() throws UnknownHostException, IOException;
	
	boolean hasResponse() throws UnknownHostException, IOException;
	
	void getTagStringRepresentation() throws UnknownHostException, IOException, SessionFullException;
	
	List<TagAntenna> getTagsList();
	
	void captureTagsObject() throws UnknownHostException, IOException, SessionFullException;
	
	String getTranslatedResponse() throws UnknownHostException, IOException;
}
