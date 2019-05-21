package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
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
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you. CLI를 실행하여서 입력 값이 원하는 입력값이 아닐 경우 오류 메세지를 따로 너가 정의해서 출력해야 한다.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed 입력 받은 파일의 장소를 저장하는 스트링
		String resultPath = args[1]; // the file path where the results are saved. 결과 파일을 저장할 위치를 저장한 스트링
		ArrayList<String> lines = Utils.getLines(dataPath, true); //파일을 읽어들여서 리스트에 저장하는 코드(단, true일때 첫번째 라인은 저장하지 않음)
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order. 항목을 학번의 오름차순으로 정렬하는 것
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
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
		
		int listSize = lines.size();
		for(int position = 0; position < listSize; position++) {
			Course course = new Course(lines.get(position));
			Student student = new Student(course.setterId());
			student.addCourse(course);
		}
		
		
		return null; // do not forget to return a proper variable.
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
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
		
		return null; // do not forget to return a proper variable.
	}
}
