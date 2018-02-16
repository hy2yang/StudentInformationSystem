package entity;

import java.util.HashSet;
import java.util.Set;

public class Program {
	
	private final int programID;
	private final String programName;
	private Set<String> courseIDs;
	
	public Program(int id, String name) {
		programID=id;
		programName=name;
		courseIDs= new HashSet<>();
	}
	
	public Set<String> getCurriculum(){
		return courseIDs;
	}
	
	public boolean addCourse (String courseID) {
		return courseIDs.add(courseID);
	}
	
	public boolean deleteCourse (String courseID) {
		return courseIDs.remove(courseID);
	}

	public String getProgramName() {
		return programName;
	}

	public int getProgramID() {
		return programID;
	}

}
