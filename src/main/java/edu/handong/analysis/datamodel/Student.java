package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentId;
	private ArrayList<Course>coursesTaken;
	private HashMap<String,Integer>semesterByYearAndSemester;
	
	public Student(String studentId) {
		this.studentId = studentId;
	}
	
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String,Integer>getSemestersByYearAndSemester(int start, int end){
		semesterByYearAndSemester = new HashMap<String,Integer>();
		int i = 1;
		for(Course course:coursesTaken) {
			String year = String.valueOf(course.setteryearTaken());
			int y = course.setteryearTaken();
			if(y >= start && y <= end){
				String semester = String.valueOf(course.settersemesterCourseTaken());
				String yearWithSemester = year + "-" + semester;
					
				if(!semesterByYearAndSemester.containsKey(yearWithSemester)){
					semesterByYearAndSemester.put(yearWithSemester, i);
					i++;
				}
			}
		}
		return semesterByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester,int start, int end) {
		HashMap<String,Integer>check;
		int count = 0;
		
		check = semesterByYearAndSemester;
		for(Course course:coursesTaken) {
			String year = String.valueOf(course.setteryearTaken());
			int y = course.setteryearTaken();
			if(y >= start && y <= end){
				String s = String.valueOf(course.settersemesterCourseTaken());
				String yearWithSemester = year + "-" + s;
			
				if(check.get(yearWithSemester) == semester) {
					count++;
				}
			}
		}
		return count;
	}
	
	public String setterStudentId(){
		return studentId;
	}
	
	public ArrayList<Course> settercourse(){
		return coursesTaken;
	}
	
	public void gettercourse(ArrayList<Course> taken){
		coursesTaken = taken;
	}
}
