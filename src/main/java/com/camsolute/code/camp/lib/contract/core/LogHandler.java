package com.camsolute.code.camp.lib.contract.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface LogHandler {

	public void log(String msg, String methodName, Class<?> clazz);
	public void logEnter(String msg, String methodName, Class<?> clazz);
	public void logExit(String msg, String methodName, Class<?> clazz);
	
	public class DefaultLogHandler implements LogHandler {

		private static String fmt = "[%15s] [%s]";

		public void log(String msg, String methodName, Class<?> clazz) {
			LogManager.getLogger(clazz).info(String.format(fmt, "["+methodName+"]","----["+msg+"]----"));			
		}

		public void logEnter(String msg, String methodName, Class<?> clazz) {
			 LogManager.getLogger(clazz).info(String.format(fmt,("["+methodName+"]"+">>>>>>>>>").toUpperCase(),"====[ "+msg+" ]===="));
		}

		public void logExit(String msg, String methodName, Class<?> clazz) {
			LogManager.getLogger(clazz).info(String.format(fmt,("<<<<<<<<<"+"["+methodName+"]").toUpperCase(),"====[ "+methodName+" ]===="));
		}
	}

	public class ExceptionLogHandler implements LogHandler {

		private static String fmt = "[%15s] [%s]";

		public void log(String msg, String methodName, Class<?> clazz) {
			LogManager.getLogger(clazz).info(String.format(fmt, "!!!!!!["+methodName+"]!!!!!!","----["+msg+"]----"));			
		}

		public void logEnter(String msg, String methodName, Class<?> clazz) {
			 LogManager.getLogger(clazz).info(String.format(fmt,("!!!!!!["+methodName+"]!!!!!!"+">>>>>>>>>").toUpperCase(),"====[ "+msg+" ]===="));
		}

		public void logExit(String msg, String methodName, Class<?> clazz) {
			LogManager.getLogger(clazz).info(String.format(fmt,("<<<<<<<<<"+"!!!!!!["+methodName+"]!!!!!!").toUpperCase(),"====[ "+methodName+" ]===="));
		}
	}
	

	public class SimpleProfilingLogHandler implements LogHandler {

		private static String fmt = "[%15s] [%s]";

		public void log(String msg, String methodName, Class<?> clazz) {
			LogManager.getLogger(clazz).info(String.format(fmt, "["+methodName+"]","[ExecutionTime:"+System.currentTimeMillis()+")]----["+msg+"]----"));			
		}

		public void logEnter(String msg, String methodName, Class<?> clazz) {
			 LogManager.getLogger(clazz).info(String.format(fmt,("["+methodName+"]"+">>>>>>>>>").toUpperCase(),"[ExecutionTime:"+System.currentTimeMillis()+")]====[ "+msg+" ]===="));
		}

		public void logExit(String msg, String methodName, Class<?> clazz) {
			LogManager.getLogger(clazz).info(String.format(fmt,("<<<<<<<<<"+"["+methodName+"]").toUpperCase(),"[ExecutionTime:"+System.currentTimeMillis()+")]====[ "+methodName+" ]===="));
		}
	}

}
