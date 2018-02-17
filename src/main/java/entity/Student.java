package entity;

import java.util.HashSet;
import java.util.Set;

public class Student {

	private final int ID;
	private String name;
	private Set<String> courseIDs;
	private int programID;
	
	public Student(int sID, String sName, int pID) {
		ID=sID;
		name=sName;
		programID=pID;
		courseIDs = new HashSet<>();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProgramID() {
		return programID;
	}

	public void setProgramID(int pID) {
		this.programID = pID;
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

	public int getID() {
		return ID;
	}
	
}
