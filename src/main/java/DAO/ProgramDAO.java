package DAO;

import java.util.Collection;
import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import entity.Program;
import utilitises.DynamoDBClient;

public class ProgramDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();
	
	public static Collection<Program> getAllPrograms () {
		List<Program> res = mapper.scan(Program.class, new DynamoDBScanExpression());
		return res;
	}
	
	public static Program getProgramByID (String pID) {		
		return mapper.load(Program.class, pID);
	}
	
	public static boolean addProgram (Program p) {
		if ( mapper.load(Program.class, p.getProgramID())!=null ) return false;
		mapper.save(p);
		return true;
	}
	
	public static void deleteProgramByID (String pID) {
		Program temp = new Program(pID, "to-be-deleted");
		mapper.delete(temp);
	}
	
	public static void main(String[] args) {
		Program p1 = new Program("P4341", "testp1");
		Program p2 = new Program("P8375", "testp2");
		p1.addCourseByID("CH2312");
		p2.addCourseByID("LI6453");
		System.out.println(addProgram(p1));
		System.out.println(addProgram(p2));
		System.out.println(getAllPrograms());
		System.out.println(getProgramByID("P8375"));
		deleteProgramByID("P4341");
		System.out.println(getAllPrograms());
	}
	
	
}
