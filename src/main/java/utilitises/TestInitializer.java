package utilitises;

import DAO.AnnouncementDAO;
import DAO.CourseDAO;
import DAO.CrossDAO;
import DAO.LectureDAO;
import DAO.ProfessorDAO;
import DAO.ProgramDAO;
import DAO.StudentDAO;
import entity.Announcement;
import entity.Course;
import entity.Lecture;
import entity.Program;
import entity.Student;

public class TestInitializer {
	
	public static void main(String[] args) {
		System.out.println("adding test values"); 
		String sid0= StudentDAO.addNewStudent(new Student("tsdasdw", "tsdasdw@cecef.com"));
		String sid1= StudentDAO.addNewStudent(new Student("asdwd", "asdwd@cecef.com"));
		String sid2= StudentDAO.addNewStudent(new Student("oiuhgtt", "oiuhgtt@cecef.com"));
		
		Lecture[] samplesL = new Lecture[3];
		for (int i=0;i<samplesL.length;++i) {
			samplesL[i]=new Lecture("l"+i);
			samplesL[i].putMaterial("some picture", "picture URL");
			samplesL[i].putMaterial("some video", "video URL");
			for (int j=0;j<2;++j) {
				samplesL[i].addNote("note No."+j);
			}
			LectureDAO.saveLecture(samplesL[i]);
		}
		
		String profid0 = ProfessorDAO.addProfessor("prof0", "email34");
		String profid1 = ProfessorDAO.addProfessor("prof1", "email56");
		String profid2 = ProfessorDAO.addProfessor("prof2", "email78");
		
		Course[] samplesC= new Course[3];
		samplesC[0]=new Course("this0", "course0", profid0);
		samplesC[1]=new Course("is1", "course1", profid1);
		samplesC[2]=new Course("test2", "course2", profid2);
		for (int i=0;i<samplesC.length;++i) {
			for (int j=0;j<samplesL.length;++j) samplesC[i].addLecture("l"+j);
			CourseDAO.saveCourse(samplesC[i]);
		}
		
		
		Program p1 = new Program("P4341", "testp1");
		Program p2 = new Program("P8375", "testp2");
		p1.addCourseByID("CH2312");
		p2.addCourseByID("LI6453");
		ProgramDAO.saveProgram(p1);
		ProgramDAO.saveProgram(p2);
		
		CrossDAO.studentEnrollCourse(sid0,"A1");
		CrossDAO.studentEnrollCourse(sid1,"A1");
		CrossDAO.studentEnrollCourse(sid2,"A1");	
		
		AnnouncementDAO.saveAnnouncement(new Announcement("A1", "fefe",
				"new test header", "this announcement has just been added to ddb"));
		System.out.println("test value added"); 
	}

}
