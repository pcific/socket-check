package socket.woo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientWorker implements Runnable {

	String threadName;
	Socket sock;
	final long clientWorkerSequence;
	long requestCount = 0;
	final String serverIp;
	final int serverPort;
	public ClientWorker(String serverIp, int serverPort, long clientWorkerSequence){
		super();
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.clientWorkerSequence = clientWorkerSequence ;
	}
	final private Object lock = new Object(); 
	
	@Override
	public void run() {
		BufferedReader reader = null;
		PrintWriter    writer = null;
		try{
			this.threadName = Thread.currentThread().getName();
			Logger.info(this.threadName, "client connect.  serverIp: %s  port: %s", serverIp, App.SERVER_PORT);
			this.sock = new Socket(serverIp, serverPort);
			sock.setSoTimeout(App.CLIENT_SOTIMEOUT);
			reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream(), "UTF-8"));
			writer = new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream(), "UTF-8"), true);

			while(true){
				if(!sock.isConnected())throw new IOException("socket is not connected");
				long t1 = System.currentTimeMillis();
				String timeString = new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(new Date(t1));
				String message = String.format("%s %03d %09d", timeString, clientWorkerSequence, ++requestCount);
				writer.println(message);
				String l = reader.readLine();
				if(l==null || l.length()<10){
					throw new IOException("fail to read inputstream");
				}
				long t2 = System.currentTimeMillis();
				long responseTime = t2 - t1;
				Logger.info(this.threadName, "server response.  %s  responseTime: %d", l, responseTime);
				synchronized (lock) {
					try{lock.wait(App.CLIENT_INTERVAL);}catch(Throwable ig){};
				}
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
