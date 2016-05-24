package com.mohammad.cyphers.des;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DebugLogger {
	File logFile;
	FileWriter fw;

	public DebugLogger() {
		this.logFile = new File("log.txt");
		try {
			fw = new FileWriter(logFile);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DebugLogger(File logFile) {
		this.logFile = logFile;
		try {
			fw = new FileWriter(logFile);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void log(String s){
		try {
			fw.append(s+"\n\n>>\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void log(double[] arr){
		System.out.println("Writing...");
		try {
			fw = new FileWriter(logFile);
		
		
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<arr.length;i++){
				sb.append(arr[i]+"\n");
			}
			
			fw.append(sb.toString()+"\n\n>>\n");
			fw.close();
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void log(double[][] arr){
		System.out.println("Writing...");

		try {
			fw = new FileWriter(logFile);
			
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<arr.length;i++){
				for(int j=0;j<arr[0].length;j++)
					sb.append(arr[i][j]+"\t");
				sb.append("\n");
			}
			fw.append(sb.toString()+"\n\n>>\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(int[] arr){
		System.out.println("Writing...");

		try {
			String s = "";
			for(int i=0;i<arr.length;i++){
				s += arr[i]+"\n";
			}
			fw.append(s+"\n\n>>\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void log(int[][] arr){
		System.out.println("Writing...");

		try {
			String s = "";
			for(int i=0;i<arr.length;i++){
				for(int j=0;j<arr[0].length;j++)
					s += arr[i][j]+"\t";
				s+="\n";
			}
			fw.append(s+"\n\n>>\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
