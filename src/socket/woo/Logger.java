package socket.woo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger{
	
	public static void info(String moduleNameString, String msgFormat, Object ... args ){
		String levelString = "[I] ";
		log(moduleNameString, levelString, msgFormat, args);
	}

	public static void warn(String moduleName, String msgFormat, Object ... args ){
		String levelString = "[W] ";
		log(moduleName, levelString, msgFormat, args);
	}

	public static void error(String moduleName, String msgFormat, Object ... args ){
		String levelString = "[E] ";
		log(moduleName, levelString, msgFormat, args);
	}

	public static void debug(String msgFormat, Object ... args ){
		StringBuilder buff = new StringBuilder();
		buff.append(" [D] ");
		buff.append(String.format(msgFormat, args));
		System.out.println(buff.toString());
	}

	private static void log(String moduleName, String levelString, String msgFormat, Object ... args ){
		StringBuilder buff = new StringBuilder();
		String timeString = new SimpleDateFormat("yy/MM/dd HH:mm:ss ").format(new Date(System.currentTimeMillis()));
		buff.append(timeString);
		buff.append(levelString);
		if(moduleName!=null){
			buff.append(String.format("@%-16s ", moduleName));
		}
		buff.append(String.format(msgFormat, args));
		if(App.LOG_STDOUT){
			System.out.println(buff.toString());
		}
		if(App.LOG_FILE){
			PrintWriter writer = null;
			try{
				writer = new PrintWriter(new FileWriter(new File(moduleName + ".log"), true), true);
				writer.println(buff.toString());
				writer.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}finally{
				try{if(writer!=null){writer.close();writer=null;}}catch(Throwable ig){}
			}
		}
	}
	
}

