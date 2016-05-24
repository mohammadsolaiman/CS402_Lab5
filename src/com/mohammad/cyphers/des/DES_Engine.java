package com.mohammad.cyphers.des;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DES_Engine {

	private String key;
	private DES_Core DES;

	private File conv_out_file;
	public DES_Engine(String key) {
		this.key = key;
		this.DES = new DES_Core(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
		this.DES = new DES_Core(key);
	}

	public DES_Core getDES() {
		return DES;
	}


	public List<String> getAllSubKeys(){
		return DES.getSubkeys();
	}
	
	
	public StringBuilder Encrypt(StringBuilder input, String outputfilename, String conversionfilename) throws IOException{
		Tools tool = new Tools();
		StringBuilder 
				bin_out=new StringBuilder(),
				hex_out=new StringBuilder(),
				ascii=new StringBuilder();
		tool.TextConversion(input, bin_out, hex_out, ascii);
		conv_out_file = new File(conversionfilename);
		FileWriter fw = new FileWriter(conv_out_file);

		String bin2file = tool._64bitdistriputeString(bin_out.toString()),
				hex2file = tool._64bitdistriputeString(hex_out.toString()),
				ascii2file = tool._64bitdistriputeString(ascii.toString()),
				input2file =input.toString();// tool._64bitdistriputeString(input_.toString());
		fw.write("INPUT:\n"+input2file+"\n\n\nBINARY REPRESENTATION:\n"+bin2file+
				"\n\n\nHEX REPRESENTATION:\n"+hex2file+"\n\n\nASCII REPRESENTATION:\n"+ascii2file+"\n\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				);
		fw.close();
		
		Scanner binary_stream = new Scanner(bin2file);
		StringBuilder output = new StringBuilder();
		
		if(key == null)
		{
			System.err.println("ERROR: DES_Engine.key is null");
			binary_stream.close();
			return null;
		}
		while(binary_stream.hasNextLine()){
			String next = binary_stream.nextLine();
			next = next.replaceAll(" ", "");
			next = next.replaceAll("\n", "");
			if(next.length() != 64){
				System.err.println("Warning: DES_Engine bit string length = "+next.length());
				continue;
			}
			output.append( DES.Encrypt(next));
		}
		
		binary_stream.close();
		
		
		StringBuilder outText = new StringBuilder();
		hex_out = new StringBuilder("");; ascii = new StringBuilder("");
		tool.BinaryConversion(output, outText, hex_out, ascii);
		conv_out_file = new File(outputfilename);
		 fw = new FileWriter(conv_out_file);

		 bin2file = "";
		 for(int i = 0 ;i<output.length();i+=8){
			 if(i%64 == 0 && i>0)
				 bin2file += "\n";
			 bin2file += output.substring(i, i+8)+" ";
			 
		 }
				hex2file = tool._64bitdistriputeString(hex_out.toString());
				ascii2file = tool._64bitdistriputeString(ascii.toString());
		fw.write("ENCREPTED TEXT:\n"+outText+"\n\n\nBINARY REPRESENTATION:\n"+bin2file+
				"\n\n\nHEX REPRESENTATION:\n"+hex2file+"\n\n\nASCII REPRESENTATION:\n"+ascii2file+"\n\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				);
		fw.close();
		
		FileWriter LR_debug = new FileWriter(new File("LR_DEBUG.txt"));
		StringBuilder LR_sb = new StringBuilder();
		for(String s : DES.LR_)
			LR_sb.append(s+"\n\n");
		
		LR_debug.write("Li , Ri   Results :\n\n\n"+LR_sb.toString());
		
		LR_debug.close();
		
		
		return new StringBuilder( hex2file);
	}
	
	public StringBuilder Decrypt(StringBuilder input, String outputfilename, String conversionfilename) throws IOException{
		Tools tool = new Tools();
		StringBuilder 
				bin_out=new StringBuilder(),
				hex_out=new StringBuilder(),
				ascii=new StringBuilder();
		tool.TextConversion(input, bin_out, hex_out, ascii);
		conv_out_file = new File(conversionfilename);
		FileWriter fw = new FileWriter(conv_out_file);

		String bin2file = tool._64bitdistriputeString(bin_out.toString()),
				hex2file = tool._64bitdistriputeString(hex_out.toString()),
				ascii2file = tool._64bitdistriputeString(ascii.toString()),
				input2file =input.toString();// tool._64bitdistriputeString(input_.toString());
		fw.write("INPUT:\n"+input2file+"\n\n\nBINARY REPRESENTATION:\n"+bin2file+
				"\n\n\nHEX REPRESENTATION:\n"+hex2file+"\n\n\nASCII REPRESENTATION:\n"+ascii2file+"\n\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				);
		fw.close();
		
		Scanner binary_stream = new Scanner(bin2file);
		StringBuilder output = new StringBuilder();
		
		if(key == null)
		{
			System.err.println("ERROR: DES_Engine.key is null");
			binary_stream.close();
			return null;
		}
		while(binary_stream.hasNextLine()){
			String next = binary_stream.nextLine();
			next = next.replaceAll(" ", "");
			next = next.replaceAll("\n", "");
			if(next.length() != 64){
				System.err.println("Warning: DES_Engine bit string length = "+next.length());
				continue;
			}
			//System.out.println(next);
			output.append( DES.Decrypt(next));
		}
		
		binary_stream.close();
		
		
		StringBuilder outText = new StringBuilder();
		hex_out = new StringBuilder("");; ascii = new StringBuilder("");
		tool.BinaryConversion(output, outText, hex_out, ascii);
		conv_out_file = new File(outputfilename);
		 fw = new FileWriter(conv_out_file);

		 bin2file = "";
		 for(int i = 0 ;i<output.length();i+=8){
			 if(i%64 == 0 && i>0)
				 bin2file += "\n";
			 bin2file += output.substring(i, i+8)+" ";
			 
		 }
				hex2file = tool._64bitdistriputeString(hex_out.toString());
				ascii2file = tool._64bitdistriputeString(ascii.toString());
		fw.write("DECREPTED TEXT:\n"+outText+"\n\n\nBINARY REPRESENTATION:\n"+bin2file+
				"\n\n\nHEX REPRESENTATION:\n"+hex2file+"\n\n\nASCII REPRESENTATION:\n"+ascii2file+"\n\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				);
		fw.close();
		
		FileWriter LR_debug = new FileWriter(new File("LR_DEBUG.txt"));
		StringBuilder LR_sb = new StringBuilder();
		for(String s : DES.LR_)
			LR_sb.append(s+"\n\n");
		
		LR_debug.write("Li , Ri   Results :\n\n\n"+LR_sb.toString());
		LR_debug.close();

		return outText;
	}
	
}
