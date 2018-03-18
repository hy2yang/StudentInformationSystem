package entity;

public class Announcement {
	
	private String header;	
	private String body;
	private String courseID; 
	private int announcementID;
	
	public Announcement(String cid, String header, String body) {
		this.courseID = cid;
		this.header = header;
		this.body = body;
	}
	
	public int getAnnouncementID() {
		return announcementID;
	}

	public void setAnnouncementID(int announcementID) {
		this.announcementID = announcementID;
	}

	public String getCourseID() {
		return courseID;
	}

	public String getHeader() {
		return header;
	}
	
	public String getBody() {
		return body;
	}

}
