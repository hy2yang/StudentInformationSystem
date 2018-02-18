package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lecture {

	//private Course source;
	private final String name;
	private List<String> notes;
	private Map<String, String> materials;
	
	public Lecture(String lName) {
		name=lName;
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
	
	public Map<String, String> getAllMaterails(){
		return materials;
	}	
	
	public void putMaterial(String name, String URL) {
		materials.put(name,URL);
	}
	
	public void deleteMaterial(String name) {
		materials.remove(name);
	}
	
	public String getMaterialByName(String name) {
		return materials.get(name);
	}

	public String getLectureName() {
		return name;
	}
	
}
