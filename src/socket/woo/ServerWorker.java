package socket.woo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerWorker implements Runnable {

	String threadName;
	final Socket sock;
	final long serverWorkerSequence;
	public ServerWorker(Socket sock, long serverWorkerSequence){
		super();
		this.sock = sock;
		this.serverWorkerSequence = serverWorkerSequence ;
		// log 
	}
	
	@Override
	public void run() {
		BufferedReader reader = null;
		PrintWriter    writer = null;
		try{
			this.threadName = Thread.currentThread().getName();
			sock.setSoTimeout(App.SERVER_SOTIMEOUT);
			InetSocketAddress client = (InetSocketAddress)sock.getRemoteSocketAddress();
			Logger.info(this.threadName, "client connected.  address: %s  port: %d", client.getAddress().getHostAddress(), client.getPort());
			reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream(), "UTF-8"));
			writer = new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream(), "UTF-8"), true);
			for(String l=null;(l=reader.readLine())!=null;){
				Logger.info(this.threadName, "client request.  %s", l);
				writer.println(l);
			}	
		}catch(IOException ioe){
			Logger.error(this.threadName, "connection fail.  err: %s", ioe.toString());
//			ioe.printStackTrace();
		}finally{
			try{if(writer!=null){writer.close();writer=null;}}catch(Throwable ig){}
			try{if(reader!=null){reader.close();reader=null;}}catch(Throwable ig){}
			try{if(sock!=null && !(sock.isClosed())){sock.close();}}catch(Throwable ig){}
		}
		Logger.info(this.threadName, "client disconnected.", "");
		
	}

}
