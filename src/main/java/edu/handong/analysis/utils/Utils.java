package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
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
		}catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument\n");
			System.exit(0);
		}
		
		if(removeHeader == true) {
			inputStream.hasNextLine();
			while(inputStream.hasNextLine()){
				String add = inputStream.nextLine();
				saveLine.add(add);
			}
		}
		else {
			while (inputStream.hasNextLine()) {
				String add = inputStream.nextLine();
				saveLine.add(add);
			}
		}
		
		inputStream.close();
		
		return saveLine;
		
	}
	
	public static void writeAFile(ArrayList<String>lines, String targetFileName) {
		
	}
}
