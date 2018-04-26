package DAO;

public class CrossDAO {
	/*
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();
	
	public static Set<Course> getCoursesOfProgram(String pID) {
		Set<String> IDs=mapper.load(Program.class, pID).getCourseIDs();
		Set<Course> courses=new HashSet<>();
		for (String i: IDs) {
			courses.add(CourseDAO.getCourseByID(i));
		}
		return courses;
	}
	*/
	public static void studentEnrollCourse(String sID, String cID) {
		
		StudentDAO.getStudentByID(sID).enrollCourse(cID);
		CourseDAO.getCourseByID(cID).enrollStudent(sID);
		
		
	}
	
	public static void studentDropCourse(String sID, String cID) {
		
		StudentDAO.getStudentByID(sID).dropCourse(cID);
		CourseDAO.getCourseByID(cID).dropStudent(sID);
		
		
	}

}
