package DAO;

public class CrossDAO {
	
	
	public static void studentEnrollCourse(int sID, String cID) {
		StudentDAO.getStudentByID(sID).enrollCourse(cID);
		CourseDAO.getCourseByID(cID).enrollStudent(sID);
	}
	
	public static void studentDropCourse(int sID, String cID) {
		StudentDAO.getStudentByID(sID).dropCourse(cID);
		CourseDAO.getCourseByID(cID).dropStudent(sID);
	}

}
