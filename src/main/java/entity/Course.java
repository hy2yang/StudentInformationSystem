package entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Course {
	
	private final String courseID;
	private List<Lecture> lectures;
	private Object board;
	private Object roster;
	private Set<Integer> studentIDs;

	public Course(String id) {
		courseID=id;
		lectures=new LinkedList<>();
		studentIDs= new HashSet<>();
	}
	
	public List<Lecture> getAllLectures(){
		return lectures;
	}
	
	public boolean addLecture (Lecture l) {
		return lectures.add(l);
	}
	
	public void deleteLecture (int i) {
		lectures.remove(i);
	}
	
	public Lecture getLacture (int index) {
		if (index<0 || index>=lectures.size()) return null;
		return lectures.get(index);
	}
	
	public void updateLecture (int index, Lecture l) {
		lectures.set(index, l);
	}
	
	public Set<Integer> getAllStudentIDs(){
		return studentIDs;
	}
	
	public boolean enrollStudent (int id) {
		return studentIDs.add(id);
	}
	
	public void dropStudent (int id) {
		studentIDs.remove(id);
	}
	
	public Object getBoard() {
		return board;
	}

	public void setBoard(Object board) {
		this.board = board;
	}

	public Object getRoster() {
		return roster;
	}

	public void setRoster(Object roster) {
		this.roster = roster;
	}	
	
	
}
