package socket.woo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		Logger.info("ServerMain", "server startup.  version: %s  port: %s", App.VERSION, App.SERVER_PORT );
		long serverWorkerSequence = 0;
	    ServerSocket ssock = null;
		try{
			ssock = new ServerSocket(App.SERVER_PORT, 20 /* backlog */);
			while(true){
				Socket sock = ssock.accept();
				ServerWorker worker = new ServerWorker(sock, ++serverWorkerSequence);
				Thread thread = new Thread(worker, String.format("ServerWorker-%03d", serverWorkerSequence));
				thread.setDaemon(true);
				thread.start();
				Logger.info("ServerMain", "serverWorker start.  threadName: %s", thread.getName());
			}
		}catch(IOException ioe){
			Logger.error("ServerMain", "server fail.  err: %s", ioe.toString());
//			ioe.printStackTrace();
		}finally{
			try{if(ssock!=null){ssock.close();ssock=null;}}catch(Throwable ig){}
		}
		Logger.info("ServerMain", "server shutdown.", "");
	}
}
