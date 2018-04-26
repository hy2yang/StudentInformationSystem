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
		String sid0= StudentDAO.saveStudent(new Student("tsdasdw", "tsdasdw@cecef.com"));
		String sid1= StudentDAO.saveStudent(new Student("asdwd", "asdwd@cecef.com"));
		String sid2= StudentDAO.saveStudent(new Student("oiuhgtt", "oiuhgtt@cecef.com"));
		
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
		
		Program s0= new Program("0","ptest0");
		s0.addCourseByID("this0");
		ProgramDAO.saveProgram(s0);
		
		
		Program s1= new Program("1","ptest1");
		s1.addCourseByID("is1");
		ProgramDAO.saveProgram(s1);
		
		Program s2= new Program("2","ptest2");
		s2.addCourseByID("test2");
		ProgramDAO.saveProgram(s2);
		
		
		Program p1 = new Program("P4341", "testp1");
		Program p2 = new Program("P8375", "testp2");
		p1.addCourseByID("CH2312");
		p2.addCourseByID("LI6453");
		ProgramDAO.saveProgram(p1);
		ProgramDAO.saveProgram(p2);
		
		CrossDAO.studentEnrollCourse(sid0,"this0");
		CrossDAO.studentEnrollCourse                                                                                                               (sid1,"is1");
		CrossDAO.studentEnrollCourse(sid2,"test2");	
		
		AnnouncementDAO.saveAnnouncement(new Announcement("A1", "fefe",
				"new test header", "this announcement has just been added to ddb"));
		System.out.println("test value added"); 
	}

}
