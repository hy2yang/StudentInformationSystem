package utilitises;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import DAO.CourseDAO;
import DAO.CrossDAO;
import DAO.ProfessorDAO;
import DAO.ProgramDAO;
import DAO.StudentDAO;
import entity.Course;
import entity.Lecture;
import entity.Program;
import entity.Student;

@WebListener
public class TestInitializer implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("adding test values"); 
		String sid0= StudentDAO.addStudent(new Student("student0","email0"));
		String sid1= StudentDAO.addStudent(new Student("student1", "email1"));
		String sid2= StudentDAO.addStudent(new Student("student2", "email2"));
		
		Lecture[] samplesL = new Lecture[3];
		for (int i=0;i<samplesL.length;++i) {
			samplesL[i]=new Lecture("l"+i);
			samplesL[i].putMaterial("some picture", "picture URL");
			samplesL[i].putMaterial("some video", "video URL");
			for (int j=0;j<2;++j) {
				samplesL[i].addNote("note No."+j);
			}
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
			CourseDAO.addCourse(samplesC[i]);
		}
		
		Program s0= new Program("0","ptest0");
		s0.addCourseByID("this0");
		ProgramDAO.addProgram(s0);
		
		
		Program s1= new Program("1","ptest1");
		s1.addCourseByID("is1");
		ProgramDAO.addProgram(s1);
		
		Program s2= new Program("2","ptest2");
		s2.addCourseByID("test2");
		ProgramDAO.addProgram(s2);
		
		CrossDAO.studentEnrollCourse(sid0,"this0");
		CrossDAO.studentEnrollCourse(sid1,"is1");
		CrossDAO.studentEnrollCourse(sid2,"test2");		
		System.out.println("test value added"); 
	}

}
