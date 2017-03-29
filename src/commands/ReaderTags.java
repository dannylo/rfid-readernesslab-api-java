package commands;

import java.io.IOException;
import java.net.UnknownHostException;

import interfaces.Command;
import utils.ConnectReader;
import utils.OperationUtil;

public class ReaderTags implements Command {

	private ConnectReader connectReader;
	
	@Override
	public void execute() throws UnknownHostException, IOException {
		connectReader = ConnectReader.getInstance(OperationUtil.IP_READER_NESSLAB, OperationUtil.PORT_READER_NESSLAB);
		this.connectReader.send(OperationUtil.READ_TAGS);
		this.connectReader.send(OperationUtil.READ_TAGS_END_MESSAGE);
		
	}

}
