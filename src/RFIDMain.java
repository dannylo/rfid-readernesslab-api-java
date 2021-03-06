	
import java.io.IOException;
import java.net.UnknownHostException;

import api.reader.nesslab.commands.CloseConnection;
import api.reader.nesslab.commands.ReaderTags;
import api.reader.nesslab.commands.ReaderTagsReset;
import api.reader.nesslab.exceptions.SessionFullException;
import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import api.reader.nesslab.utils.*;
public class RFIDMain {
	
	public static void main(String[] args) {		
		try {
			
			/* The class ApiReaderNesslab to be instantiated, a 
			 * new connection with the Nesslab is opened. */
			ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");
			api.defaultConfiguration();
			api.clearTemporaryMemory(200);//Clean memory of 2 in 2 minutes.
			
			
			api.executeAction(new ReaderTags());
			
			while (api.hasResponse()) {
				try {
					api.captureTagsObject();
					if(api.hasNewTag()){
						System.out.println(api.getTagUniqueJsonRepresentation());
					}
					
				} catch (SessionFullException e) {
					api.executeAction(new ReaderTagsReset());
				}
			}
			
			api.executeAction(new CloseConnection());
		} catch (UnknownHostException e) {
			System.err.println("Host not found: " + OperationUtil.getIpReaderNesslab());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Don't possible the conection: "+ OperationUtil.getIpReaderNesslab());
			System.exit(1);
		}
	}
}