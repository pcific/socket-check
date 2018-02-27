package socket.woo;


public class Client {

	public static void main(String[] args) {
		if(!(args.length > 0)){
			System.err.println("Usage java socket.woo.Client <server_ip>");
			System.exit(1);
		}
		
		String serverIp = args[0];
		long clientWorkerSequence = 0;
		Logger.info("ClientMain", "client startup.  version: %s  threadCount: %s ",App.VERSION,  App.CLIENT_THREAD_COUNT );
		
		ThreadGroup threadGroup = new ThreadGroup("workers");
		
		for(int i=0;i<App.CLIENT_THREAD_COUNT;i++){
			ClientWorker worker = new ClientWorker(serverIp, App.SERVER_PORT, ++clientWorkerSequence);
			Thread thread = new Thread(threadGroup, worker, String.format("ClientWorker-%03d", clientWorkerSequence));
			thread.setDaemon(true);
			thread.start();
		}
		
		final Object lock = new Object(); 
		while(true){
			synchronized (lock) {
				try{lock.wait(10000);}catch(Throwable ig){};
			}
			int activeCount = threadGroup.activeCount();
			Logger.info("ClientMain", "active thread count: %d", activeCount);
			if(activeCount < App.CLIENT_THREAD_COUNT){
				Logger.info("ClientMain", "create new thread", "");
				ClientWorker worker = new ClientWorker(serverIp, App.SERVER_PORT, ++clientWorkerSequence);
				Thread thread = new Thread(threadGroup, worker, String.format("ClientWorker-%03d", clientWorkerSequence));
				thread.setDaemon(true);
				thread.start();
			}
		}
		//Logger.info("ClientMain", "client shutdown.", "");
	}
}
