package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Courses")
public class Course {
	
	@DynamoDBHashKey
	private String courseID;
	
	@DynamoDBAttribute
	private String name;
	
	@DynamoDBAttribute
	private String professorID;
	
	@DynamoDBAttribute
	private Set<String> studentIDs;
	
	@DynamoDBAttribute
	private Set<String> announcementIDs;	
	
	@DynamoDBAttribute
	private List<String> lectureIDs;
	
	@DynamoDBAttribute	
	private String board;
	

	public Course() {}
	
	public Course(String id, String name, String profId) {
		this.courseID=id;
		this.name=name;
		this.professorID=profId;
		lectureIDs=new ArrayList<>();
		studentIDs= new HashSet<>();
		announcementIDs = new HashSet<>();
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public List<String> getLectureIDs() {
		return lectureIDs;
	}

	public void setLectureIDs(List<String> lectureIDs) {
		this.lectureIDs = lectureIDs;
	}

	public String getLectureIDByIndex(int index) {
		return lectureIDs.get(index);
	}
	
	public boolean addLecture (String lid) {
		return lectureIDs.add(lid);
	}
	
	public void deleteLectureByIndex (int index) {		
		lectureIDs.remove(index);
	}
	
	public Set<String> getStudentIDs() {
		return studentIDs;
	}

	public void setStudentIDs(Set<String> studentIDs) {
		this.studentIDs = studentIDs;
	}

	public Set<String> getAnnouncementIDs() {
		return announcementIDs;
	}

	public void setAnnouncementIDs(Set<String> announcementIDs) {
		this.announcementIDs = announcementIDs;
	}

	
	public boolean enrollStudent (String sID) {
		return studentIDs.add(sID);
	}
	
	public void dropStudent (String sID) {
		studentIDs.remove(sID);
	}
	
	public String getBoard() {
		return board;
	}

	public void setBoard(String URL) {
		this.board = URL;
	}	
	
	public boolean addAnnouncement(String aid) {
		return announcementIDs.add(aid);
	}
		

	public String getProfessorID() {
		return professorID;
	}

	public void setProfessorID(String professorID) {
		this.professorID = professorID;
	}


}
