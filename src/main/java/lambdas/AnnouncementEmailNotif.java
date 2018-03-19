package lambdas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;



public class AnnouncementEmailNotif implements RequestHandler<DynamodbEvent, String> {
	
	private AmazonDynamoDB DDB_CLIENT=AmazonDynamoDBClientBuilder
			.standard()
			.withRegion("us-west-2")
			.build();
	
	private AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	
	private static String ARN_TOPIC = "arn:aws:sns:us-west-2:056025374430:testNotif";
	
	@Override
    public String handleRequest(DynamodbEvent input, Context context) {
    	
		String output ="";
		
    	for (DynamodbStreamRecord record : input.getRecords()) {
    		if (record == null) continue;
    		
    		
    		context.getLogger().log(record.toString());
    		String body= emailBodyFromRecord(record);
            context.getLogger().log(body);
            /*
            output = "Hello, "+inputString+"!";
            String outputBody = output+" a new fruit you might like is published!";
            sendEmailNotif(output, outputBody);
            */
    	}    	
    	
        return output;
    } 
	
	private String emailBodyFromRecord (DynamodbStreamRecord record) {
		String pid=record.getDynamodb().getNewImage().get("professorID").getS();
		String cid=record.getDynamodb().getNewImage().get("courseID").getS();
		String header = record.getDynamodb().getNewImage().get("header").getS();
		String body = record.getDynamodb().getNewImage().get("body").getS();
		String time = record.getDynamodb().getNewImage().get("time").getS();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Professor ");
		sb.append(getByID("Professors",pid).get("name").getS());		
		sb.append("of course ");
		sb.append(cid);
		sb.append("--");
		sb.append(getByID("Courses",cid).get("name").getS());
		sb.append("just posted a new announcement at ");
		sb.append(time);
		sb.append(System.lineSeparator());
		sb.append("Announcement header: ");
		sb.append(header);
		sb.append(System.lineSeparator());
		sb.append("Announcement body: ");
		sb.append(body);
		
		return sb.toString();
	}
	
	private Map<String, AttributeValue> getByID(String tableName,String id) {
		
		Map<String,String> names = new HashMap<>();
	    names.put("#professorID", id);
	    Map<String,AttributeValue> values = new HashMap<>();
	    values.put(":p",new AttributeValue().withS(id));

		
		QueryRequest queryRequest = new QueryRequest()
		        .withTableName(tableName)
		        .withKeyConditionExpression("#professorID = :p")
		        .withExpressionAttributeNames(names)
		        .withExpressionAttributeValues(values);
		
		QueryResult queryResult = DDB_CLIENT.query(queryRequest);
	    List<Map<String,AttributeValue>> items = queryResult.getItems();
		if (items.size() > 0) {
			return items.get(0);
		} else {
			return null;
		}		
	}
    
    private void sendEmailNotif( final String subject, final String message) {
    	PublishRequest pr = new PublishRequest(ARN_TOPIC, message);
    	SNS_CLIENT.publish(pr);
    }
}
