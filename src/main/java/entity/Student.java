package entity;

import java.util.HashSet;
import java.util.Set;

public class Student {

	private final int studentID;
	private String name;
	private Set<String> courseIDs;
	private String programName;
	
	public Student(int id, String sName, String pName) {
		studentID=id;
		name=sName;
		programName=pName;
		courseIDs = new HashSet<>();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public Set<String> getCourseIDs(){
		return courseIDs;
	}
	
	public boolean enrollCourse (String courseID) {
		return courseIDs.add(courseID);
	}
	
	public boolean dropCourse (String courseID) {
		if (!courseIDs.contains(courseID)) return false;
		return courseIDs.remove(courseID);
	}

	public int getStudentID() {
		return studentID;
	}
	
}
