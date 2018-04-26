package entity;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Programs")
public class Program {
	
	@DynamoDBHashKey
	private String programID;
	
	@DynamoDBAttribute
	private String programName;
	
	@DynamoDBAttribute
	private Set<String> courseIDs;
	
	public Program() {};
	
	public Program(String id, String name) {
		programID=id;
		programName=name;
		courseIDs= new HashSet<>();
	}	
	
	public String getProgramID() {
		return programID;
	}
	
	public void setProgramID(String programID) {
		this.programID = programID;
	}
	
	public Set<String> getCourseIDs(){
		return courseIDs;
	}
	
	public void setCourseIDs(Set<String> courseIDs) {
		this.courseIDs = courseIDs;
	}
	
	public String getProgramName() {
		return programName;
	}
	
	public void setProgramName(String programName) {
		this.programName = programName;
	}	

	public boolean addCourseByID (String courseID) {
		return courseIDs.add(courseID);
	}
	
	public boolean deleteCourseByID (String courseID) {
		return courseIDs.remove(courseID);
	}

}
