package entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Course {
	
	private final String courseID;
	private List<Lecture> lectures;
	private String board;
	private String roster;
	private Set<Integer> studentIDs;

	public Course(String id) {
		courseID=id;
		lectures=new LinkedList<>();
		studentIDs= new HashSet<>();
	}
	
	public List<Lecture> getAllLectures(){
		return lectures;
	}
	
	public Lecture getLecture(int index) {
		return lectures.get(index);
	}
	
	public boolean addLecture (Lecture l) {
		return lectures.add(l);
	}
	
	public void deleteLecture (int index) {		
		lectures.remove(index);
	}
	
	public Set<Integer> getAllStudentIDs(){
		return studentIDs;
	}
	
	public boolean enrollStudent (int sID) {
		return studentIDs.add(sID);
	}
	
	public void dropStudent (int sID) {
		studentIDs.remove(sID);
	}
	
	public Object getBoard() {
		return board;
	}

	public void setBoard(String URL) {
		this.board = URL;
	}
	
	public void deleteBoard() {
		this.board = null;
	}

	public Object getRoster() {
		return roster;
	}

	public void setRoster(String URL) {
		this.roster = URL;
	}

	public void deleteRoster() {
		this.roster = null;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
}
