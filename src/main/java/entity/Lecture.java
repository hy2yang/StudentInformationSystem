package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "Lectures")
public class Lecture {
	
	@DynamoDBHashKey
	private String lectureID;
	
	@DynamoDBAttribute
	private List<String> notes;
	
	@DynamoDBAttribute
	private Map<String, String> materials;
	
	public Lecture() {}
	
	public Lecture(String id) {
		lectureID = id;		
		
	}
	
	
	public String getLectureID() {
		return lectureID;
	}

	public void setLectureID(String lectureID) {
		this.lectureID = lectureID;
	}

	public Map<String, String> getMaterials() {
		return materials;
	}

	public void setMaterials(Map<String, String> materials) {
		this.materials = materials;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public List<String> getNotes() {
		return notes;
	}
	
	public boolean addNote( String n) {
		if (notes==null) notes = new ArrayList<>();
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
	
	public void putMaterial(String name, String URL) {
		if (materials==null) this.materials = new HashMap<>();
		materials.put(name,URL);
	}
	
	public void deleteMaterial(String name) {
		materials.remove(name);
	}
	
	public String getMaterialByName(String name) {
		return materials.get(name);
	}

	
}
