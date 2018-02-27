package socket.woo;

public class App {
	final public static String VERSION = "1.0.2";
	//final public static boolean LOG_STDOUT             = (System.getProperty("socket.woo.logstdout")!=null);
	final public static boolean LOG_STDOUT             = Boolean.parseBoolean(System.getProperty("socket.woo.logstdout", "false"));
	final public static boolean LOG_FILE               = Boolean.parseBoolean(System.getProperty("socket.woo.logfile",   "true"));
	final public static int     SERVER_PORT            = Integer.parseInt(System.getProperty("socket.woo.server.port", "55555"));
	final public static int     SERVER_SOTIMEOUT       = Integer.parseInt(System.getProperty("socket.woo.server.sotimeout", "90000"));
	final public static int     CLIENT_THREAD_COUNT    = Integer.parseInt(System.getProperty("socket.woo.client.thread.count", "5"));
	final public static int     CLIENT_INTERVAL        = Integer.parseInt(System.getProperty("socket.woo.client.interval", "60000"));
	final public static int     CLIENT_SOTIMEOUT       = Integer.parseInt(System.getProperty("socket.woo.client.sotimeout", "90000"));
	
}
