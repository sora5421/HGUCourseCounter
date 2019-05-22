package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	
	public static ArrayList<String>getLines(String file,boolean removeHeader){
		//파일을 1줄씩 읽어들어서 리스트에 저장하여 리턴하는 역할을 한다.
		//단, true일때는 첫줄은 리스트에 포함이 되지 않도록 한다.
		ArrayList<String>saveLine = new ArrayList<String>();
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new File(file));
		
			if(removeHeader == true) {
				String line = inputStream.nextLine();
				while(inputStream.hasNextLine()) {
					line = inputStream.nextLine();
					saveLine.add(line);
				}
			}
			else {
				String line = inputStream.nextLine();
				saveLine.add(line);
				while(inputStream.hasNextLine()) {
					line = inputStream.nextLine();
					saveLine.add(line);
				}
			}
			
			inputStream.close();
		}catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
			
		return saveLine;
	}
	
	public static void writeAFile(ArrayList<String>lines, String targetFileName){
		PrintWriter outputStream = null;
		
		try {
			outputStream = new PrintWriter(targetFileName);
		}catch(FileNotFoundException e){
			File tmp = new File(targetFileName);
			tmp.getParentFile().mkdirs();
			try {
				tmp.createNewFile();
				outputStream = new PrintWriter(targetFileName);
			} catch (IOException e1) {
				
			}
		}
		for(String out:lines) {
			outputStream.println(out);
		}
		outputStream.close();
	}
}
