package edu.handong.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file. 결과 파일에 각 학생당 한 학기의 과목수를 저장하는 로직입니다.
	 * Run method must not be changed!!
	 * @param args
	 * @throws IOException 
	 */
	public void run(String[] args) throws IOException {
		
		/*try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you. CLI를 실행하여서 입력 값이 원하는 입력값이 아닐 경우 오류 메세지를 따로 너가 정의해서 출력해야 한다.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}*/
		
		//String dataPath = args[0]; // csv file to be analyzed 입력 받은 파일의 장소를 저장하는 스트링
		ArrayList<String> lines = Utils.getLines(args, true); //파일을 읽어들여서 리스트에 저장하는 코드(단, true일때 첫번째 라인은 저장하지 않음)
		String resultPath = Utils.setOutput(); // the file path where the results are saved. 결과 파일을 저장할 위치를 저장한 스트링
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order. 항목을 학번의 오름차순으로 정렬하는 것
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		int start = Utils.setStartyear();
		int end = Utils.setEndyear();
		String analysis = Utils.setterAnalysis();
		
		ArrayList<String> linesToBeSaved;
		if(analysis.equals("1")) {
			// Generate result lines to be saved.
			 linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents,start,end);
		}
		else{
			linesToBeSaved = countNumberOfCoursesByStudentpersent(sortedStudents,start,end);
		}
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		//키로 학생들의 학번을 value로는 student객체를 형성하는 메소드 아다.
		// TODO: Implement this method
		HashMap<String,Student>save = new HashMap<String,Student>();
		ArrayList<Course>cour = new ArrayList<Course>();
			
		int listSize = lines.size();
		for(int position = 0; position < listSize; position++) {
			Course course = new Course(lines.get(position));
			cour.add(course);	
		}
				
		int i = 0;
		while(true) {
			//Course c1 = cour.get(i);
			Student student = new Student(cour.get(i).setterId());
			ArrayList<Course>taken = new ArrayList<Course>();
			student.gettercourse(taken);
			if(i == 0) {
				student.addCourse(cour.get(i));
				i++;
			}
			if(!cour.get(i-1).setterId().equals(cour.get(i).setterId())) {
				student.addCourse(cour.get(i));
				i++;
			}
			while(true) {
				Course c2 = cour.get(i-1);
				Course c3 = cour.get(i);
				if(c2.setterId().equals(c3.setterId())) {
					student.addCourse(c3);
					i++;
					if(i>=cour.size()) break;
				}
				else break;				
			}
			Course c4 = cour.get(i-1);
			save.put(c4.setterId(),student);
			if(i>=cour.size()) break;
		}
		
		
		return save; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents,int start,int end) {
		//hashmap 데이터를 읽어 들어서 다시 라인으로 변환시키는 메소드
		
		ArrayList<String>out = new ArrayList<String>();
		 HashMap<String,Integer>Semester;
		
		out.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		Iterator<String> keySetIterator = sortedStudents.keySet().iterator();
		while (keySetIterator.hasNext()) {
		    String key = keySetIterator.next();
		    
		    Student student = sortedStudents.get(key);
		    Semester = student.getSemestersByYearAndSemester(start,end);
		    
		    for(int i = 1; i < Semester.size()+1; i++) {
		    	out.add(key + "," + Semester.size() + "," + i + "," + student.getNumCourseInNthSemester(i,start,end));
		    }
		}
		return out; // do not forget to return a proper variable.
	}
	
	private ArrayList<String> countNumberOfCoursesByStudentpersent(Map<String, Student> sortedStudents,int start, int end) {
		ArrayList<String>out = new ArrayList<String>();
		ArrayList<Course>save = new ArrayList<Course>();
		ArrayList<Integer>TotalStudent = new ArrayList<Integer>();
		ArrayList<Integer>count = new ArrayList<Integer>();
		ArrayList<String>name = new ArrayList<String>();
		String code = Utils.setCoursecode();
		
		out.add("Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate");
		
		Iterator<String> keySetIterator = sortedStudents.keySet().iterator();
		while (keySetIterator.hasNext()) {
		    String key = keySetIterator.next();
		    
		    Student student = sortedStudents.get(key);
		   
		    for(Course course:student.settercourse()) {
		    	int year = course.setteryearTaken();
		    	if(year >= start && year <= end) {
		    		save.add(course);
		    	}
		    }
		}//전체 cousre의 모임이 된다
		int s = start;
		while(s <= end) {
			int Take1 = 0;
			int Take2 = 0;
			int Take3 = 0;
			int Take4 = 0;
			int codeCount1 = 0;
			int codeCount2 = 0;
			int codeCount3 = 0;
			int codeCount4 = 0;
			
			for(Course course:save) {
				int year = course.setteryearTaken();
				if(year == s) {
					if(course.settersemesterCourseTaken() == 1) {
						Take1++;
						if(code.equals(course.settercourseCode())) codeCount1++;
					}
					else if(course.settersemesterCourseTaken() == 2) {
						Take2++;
						if(code.equals(course.settercourseCode())) codeCount2++;
					}
					else if(course.settersemesterCourseTaken() == 3) {
						Take3++;
						if(code.equals(course.settercourseCode())) codeCount3++;
					}
					else if(course.settersemesterCourseTaken() == 4) {
						Take4++;
						if(code.equals(course.settercourseCode())) codeCount4++;
					}
				}
				if(code.equals(course.settercourseCode())){
					name.add(course.settercourseName());
				}
			}
			TotalStudent.add(Take1);
			TotalStudent.add(Take2);
			TotalStudent.add(Take3);
			TotalStudent.add(Take4);
			count.add(codeCount1);
			count.add(codeCount2);
			count.add(codeCount3);
			count.add(codeCount4);
			//s++;
		}
		
		 for(int i = 0; i < TotalStudent.size()/4; i++) {		 
			 double num1 = count.get(4*i)/(double)TotalStudent.get(4*i)*100;
			 double num2 = count.get(1+4*i)/(double)TotalStudent.get(1+4*i)*100;
			 double num3 = count.get(2+4*i)/(double)TotalStudent.get(2+4*i)*100;
			 double num4 = count.get(3+4*i)/(double)TotalStudent.get(3+4*i);
			 String n1 = String.format("%.1f", num1)+ "%";
			 String n2 = String.format("%.1f", num2)+ "%";
			 String n3 = String.format("%.1f", num3)+ "%";
			 String n4 = String.format("%.1f", num4)+ "%";
			 
			 out.add(String.valueOf(start)+","+String.valueOf(1)+","+code+","+name.get(0)+ ","+TotalStudent.get(4*i)+","+count.get(4*i)+","+n1);
			 out.add(String.valueOf(start)+","+String.valueOf(2)+","+code+","+name.get(0)+ ","+TotalStudent.get(1+4*i)+","+count.get(1+4*i)+","+n2);
			 out.add(String.valueOf(start)+","+String.valueOf(3)+","+code+","+name.get(0)+ ","+TotalStudent.get(2+4*i)+","+count.get(2+4*i)+","+n3);
			 out.add(String.valueOf(start)+","+String.valueOf(4)+","+code+","+name.get(0)+ ","+TotalStudent.get(3+4*i)+","+count.get(3+4*i)+","+n4);
		    	start++;
		    }
		
		
		return out;
	}

}
