package entity;

import java.util.HashSet;
import java.util.Set;

public class Program {
	
	private final String programName;
	private Set<String> courseIDs;
	
	public Program(String name) {
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

}
