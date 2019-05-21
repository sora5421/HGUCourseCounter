package edu.handong.analysis.datamodel;

public class Course {
	
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String line) {
		studentId = line.split(",")[0].trim();
		yearMonthGraduated = line.split(",")[1].trim();
		firstMajor = line.split(",")[2].trim();
		secondMajor = line.split(",")[3].trim();
		courseCode = line.split(",")[4].trim();
		courseName = line.split(",")[5].trim();
		courseCredit = line.split(",")[6].trim();
		yearTaken = Integer.parseInt(line.split(",")[7].trim());
		semesterCourseTaken = Integer.parseInt(line.split(",")[8].trim());
	}
	
	public String setterId(){
		return studentId;
	}
	
	public String setteryearMonthGraduated(){
		return yearMonthGraduated;
	}
	
	public String setterfirstMajor(){
		return firstMajor;
	}
	
	public String settersecondMajor(){
		return secondMajor;
	}
	
	public String settercourseCode(){
		return courseCode;
	}
	
	public String settercourseName(){
		return courseName;
	}
	
	public String settercourseCredit(){
		return courseCredit;
	}
	
	public int setteryearTaken(){
		return yearTaken;
	}
	
	public int settersemesterCourseTaken(){
		return semesterCourseTaken;
	}
}
