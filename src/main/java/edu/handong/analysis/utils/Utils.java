package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class Utils {
	
	static String input;
	static String output;
	static String analysis;
	static String coursecode;
	static String startyear;
	static String endyear;
	static boolean help;	
	
	public static ArrayList<String>getLines(String[] args,boolean removeHeader) throws IOException{
		//파일을 1줄씩 읽어들어서 리스트에 저장하여 리턴하는 역할을 한다.
		//단, true일때는 첫줄은 리스트에 포함이 되지 않도록 한다.
		ArrayList<String>saveLine = new ArrayList<String>();
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				System.exit(0);
			}
			
			try {
				Reader reader = Files.newBufferedReader(Paths.get(input));
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
	            
	            for(CSVRecord csvRecord : csvParser) {
	            	String ID  = csvRecord.get(0);
	            	String Graduated  = csvRecord.get(1);
	            	String firstMajor  = csvRecord.get(2);
	            	String secondMajor  = csvRecord.get(3);
	            	String courseCode  = csvRecord.get(4);
	            	String courseName  = csvRecord.get(5);
	            	String courseCredit  = csvRecord.get(6);
	            	String yearTaken  = csvRecord.get(7);
	            	String semesterCourseTaken  = csvRecord.get(8);
	            	
	            	String line = ID+","+Graduated+","+firstMajor+","+secondMajor+","+courseCode+","+courseName+","+courseCredit+","+yearTaken+","+semesterCourseTaken; 
	            	saveLine.add(line);
	            }
			} catch (FileNotFoundException e) {
				System.out.println("The file path does not exist. Please check your CLI argument!");
				System.exit(0);
			}
		}
		
	
		return saveLine;
	}
	
	public static int setStartyear() {
		int start = Integer.parseInt(startyear);
		return start;
	}
	
	public static int setEndyear() {
		int end = Integer.parseInt(endyear);
		return end;
	}
	
	public static String setCoursecode() {
		return coursecode;
	}
	
	public static String setterAnalysis() {
		return analysis;
	}
	
	public static String setOutput() {
		return output;
	}
	
	private static boolean parseOptions(Options options, String[] file) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, file);
			
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			coursecode = cmd.getOptionValue("c");
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");
			
			
		} catch(Exception e) {
			printHelp(options);
			System.exit(0);
		}
		
		if(analysis == "2" && coursecode == null) {
			printHelp(options);
			System.exit(0);
		}
		if(analysis != "1" || analysis != "2") {
			printHelp(options);
			System.exit(0);
		}
		return true;
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
		
	}

	private static Options createOptions() {
		Options options = new Options();
		
		//add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				.required()
				.build());
				
		// add options by using OptionBuilder
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				//.hasArg()
				.argName("Help")
				//.required()
				.build());
		
		return options;
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
