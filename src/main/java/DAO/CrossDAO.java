package DAO;

public class CrossDAO {
	
	static {
		studentEnrollCourse(0,"this0");
		studentEnrollCourse(1,"is1");
		studentEnrollCourse(2,"test2");
	}
	
	public static void studentEnrollCourse(int sID, String cID) {
		StudentDAO.getStudentByID(sID).enrollCourse(cID);
		CourseDAO.getCourseByID(cID).enrollStudent(sID);
	}
	
	public static void studentDropCourse(int sID, String cID) {
		StudentDAO.getStudentByID(sID).dropCourse(cID);
		CourseDAO.getCourseByID(cID).dropStudent(sID);
	}

}
