package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lecture {

	//private Course source;
	private List<String> notes;
	private Map<String, Object> materials;
	
	public Lecture() {
		this.notes = new ArrayList<>();
		this.materials = new HashMap<>();
	}
	
	/*public Lecture(Course c) {
		this.source = c;
		this.notes = new ArrayList<>();
		this.materials = new HashMap<>();
	}*/
	
	public List<String> getNotes() {
		return notes;
	}
	
	public boolean addNote( String n) {
		return notes.add(n);
	}
	
	public String getNote (int index) {
		if (index<0 || index>=notes.size()) return null;
		return notes.get(index);
	}
	
	public void deleteNote(int index) {
		notes.remove(index);
	}	
	
	public void updateNote (int index, String n) {
		notes.set(index, n);
	}
	
	
	public void postMaterial(String name, Object m) {
		materials.put(name,m);
	}
	
	public void deleteMaterial(String name) {
		materials.remove(name);
	}
	
	public void getMaterialByName(String name) {
		materials.get(name);
	}
	
}
