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
	
	public static String saveProgram (Program p) {
		if ( mapper.load(Program.class, p.getProgramID())!=null ) return null;
		mapper.save(p);
		return p.getProgramID();
	}
	
	public static void deleteProgramByID (String pID) {
		Program temp = new Program(pID, "to-be-deleted");
		mapper.delete(temp);
	}	
	
}
