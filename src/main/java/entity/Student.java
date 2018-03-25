package entity;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Students")
public class Student {

	@DynamoDBHashKey
	private String ID;
	
	@DynamoDBAttribute
	private String name;
	
	@DynamoDBAttribute
	private String email;
	
	@DynamoDBAttribute
	private Set<String> courseIDs;
	
	@DynamoDBAttribute
	private String programID;
	
	public Student() {}
	
	public Student(String sName, String email) {
		this.email= email;
		name=sName;
		courseIDs = new HashSet<>();		
	}
	
	public void setID(String id) {
		ID = id;
	}

	public void setCourseIDs(Set<String> courseIDs) {
		this.courseIDs = courseIDs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgramID() {
		return programID;
	}

	public void setProgramID(String pID) {
		this.programID = pID;
	}
	
	public Set<String> getCourseIDs(){
		return courseIDs;
	}
	
	public boolean enrollCourse (String courseID) {
		return courseIDs.add(courseID);
	}
	
	public boolean dropCourse (String courseID) {
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
