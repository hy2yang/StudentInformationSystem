package entity;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Students")
public class Student {

	@DynamoDBHashKey
	private String studentID;
	
	@DynamoDBAttribute
	private String name;
	
	@DynamoDBAttribute
	private String email;
	
	@DynamoDBAttribute
	private Set<String> courseIDs;
	
	@DynamoDBAttribute
	private String programID;
	
	@DynamoDBAttribute
	private int active = 0;
	
	public Student() {}
	
	public Student(String sName, String email) {
		this.email= email;
		name=sName;			
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
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
		if (courseIDs==null) courseIDs = new HashSet<String>();
		return courseIDs.add(courseID);
	}
	
	public boolean dropCourse (String courseID) {
		return courseIDs.remove(courseID);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	
}
