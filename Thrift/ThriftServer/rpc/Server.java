package ThriftServer.rpc;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
 
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;



public class Server implements RpcService.Iface{

	public static void main(String[] as) {
//		TNonblockingServerTransport serverTransport = null;
//		try {
//			serverTransport = new TNonblockingServerSocket(11000);
//		} catch (TTransportException e) {
//			e.printStackTrace();
//		}
// 
//		RpcService.Processor<RpcService.Iface> processor = new RpcService.Processor<RpcService.Iface>(
//				new Server());
//		Factory protFactory = new TBinaryProtocol.Factory(true, true);
// 
//		TNonblockingServer.Args args = new TNonblockingServer.Args(
//				serverTransport);
//		args.processor(processor);
//		args.protocolFactory(protFactory);
//		TServer server = new TNonblockingServer(args);
//		System.out.println("Start server on port 19090 ...");
//		server.serve();
		
		TNonblockingServerTransport serverTransport = null;
		try {
			serverTransport = new TNonblockingServerSocket(11000);
		} catch (TTransportException e) {
			e.printStackTrace();
		}
 
		RpcService.Processor<RpcService.Iface> processor = new RpcService.Processor<RpcService.Iface>(
				new Server());
 
		Factory protFactory = new TBinaryProtocol.Factory(true, true);
		//TCompactProtocol.Factory protFactory = new TCompactProtocol.Factory();
 
		TNonblockingServer.Args args = new TNonblockingServer.Args(
				serverTransport);
		args.processor(processor);
		args.protocolFactory(protFactory);
		TServer server = new TNonblockingServer(args);
		System.out.println("Start server on port 19090 ...");
		server.serve();

	}
	
 
	@Override
	public List<String> funCall(long callTime, String funCode,
			Map<String, String> paramMap) throws TException {
		System.out.println("-->FunCall:" + callTime + " " + funCode + " "
				+ paramMap);
		List<String> retList = new ArrayList<>();
 
		for (Entry<String, String> entry : paramMap.entrySet()) {
			retList.add(entry.getKey() + entry.getValue());
		}
 
		return retList;
	}
	public String testOne(String msg) throws TException{
		return msg;
	}

	@Override
	public String atxServer() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String adbServer() throws TException {
		// TODO Auto-generated method stub
		return "AdbServer start success";
	}

	@Override
	public String actServer() throws TException {
		// TODO Auto-generated method stub
		return null;
	}
}
