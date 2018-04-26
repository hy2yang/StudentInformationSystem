package entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Announcements")
public class Announcement {
	
	@DynamoDBHashKey
	private String announcementID;
	
	@DynamoDBAttribute
	private String header;
	
	@DynamoDBAttribute
	private String body;
	
	@DynamoDBAttribute
	private String courseID;
	
	@DynamoDBAttribute
	private String professorID;
	
	@DynamoDBAttribute
	private String time;
	
	public Announcement() {}
	
	public Announcement(String cid, String pid, String header, String body) {
		this.courseID = cid;
		this.professorID = pid;
		this.header = header;
		this.body = body;
	}

	public String getAnnouncementID() {
		return announcementID;
	}

	public void setAnnouncementID(String announcementID) {
		this.announcementID = announcementID;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getProfessorID() {
		return professorID;
	}

	public void setProfessorID(String professorID) {
		this.professorID = professorID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

}
