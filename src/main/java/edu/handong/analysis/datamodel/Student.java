package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentId;
	private ArrayList<Course>coursesTaken;
	private HashMap<String,Integer>semesterByYearAndSemester;
	
	public Student(String studentId) {
		
	}
	
	public void addCourse(Course newRecord) {
		
	}
	
	public HashMap<String,Integer>getSemestersByYearAndSemester(){
		return semesterByYearAndSemester;
		
	}
	
	public int getNumCourseInNthSemester(int semester) {
		
		return 0;
	}
}
