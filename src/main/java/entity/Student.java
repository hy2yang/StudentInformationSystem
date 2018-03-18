package entity;

import java.util.HashSet;
import java.util.Set;

public class Student {

	private String ID;
	private String name;
	private String email;
	private Set<String> courseIDs;
	private int programID;
	
	public Student(String sName, String email) {
		this.email= email;
		name=sName;
		courseIDs = new HashSet<>();		
	}
	
	public void setSID(String sID) {
		this.ID=sID;
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

	public String getID() {
		return ID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
