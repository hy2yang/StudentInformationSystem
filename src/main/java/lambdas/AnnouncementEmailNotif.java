package lambdas;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class AnnouncementEmailNotif implements RequestHandler<DynamodbEvent, String> {
	
	private static AmazonDynamoDB DDB_CLIENT=AmazonDynamoDBClientBuilder
			.standard()
			.withCredentials(new EnvironmentVariableCredentialsProvider())
			.withRegion("us-west-2")
			.build();
	
	private static AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	
	private static DynamoDB ddb = new DynamoDB(DDB_CLIENT);
	
	@Override
    public String handleRequest(DynamodbEvent input, Context context) {    	
		
    	for (DynamodbStreamRecord record : input.getRecords()) {
    		if (record == null) continue; 
    		
    		String notif= emailBodyFromRecord(record);    		
            context.getLogger().log(notif+System.lineSeparator()); 
            
            String topicARN = createTopic(record);            
            context.getLogger().log(topicARN+System.lineSeparator());
            
            Set<String> recipients = getRecipients(record);
            subscribe(recipients, topicARN);            
            context.getLogger().log(recipients.toString()+System.lineSeparator());
            
            sendEmailNotif(topicARN, notif);
    	}    	
    	
        return "1";
    } 
	
	private void subscribe(Set<String> recipients, String topicARN) {		
		for (String address : recipients) {
			SNS_CLIENT.subscribe(new SubscribeRequest(topicARN, "email", address));
		}
	}

	private String createTopic(DynamodbStreamRecord record) {		
		String topicName = record.getDynamodb().getNewImage().get("courseID").getS()+"CourseNotification";
		
		CreateTopicRequest request = new CreateTopicRequest(topicName);
		CreateTopicResult res = SNS_CLIENT.createTopic(request);
		return res.getTopicArn();
	}

	private Set<String> getRecipients(DynamodbStreamRecord record) {		
		
		String cid = record.getDynamodb().getNewImage().get("courseID").getS();
		Set<String> sids = getByID("Courses","courseID",cid).getStringSet("studentIDs");
		
		TableKeysAndAttributes spec = new TableKeysAndAttributes("Students")
				.addHashOnlyPrimaryKeys("studentID", sids.toArray(new Object[sids.size()]));
		
		Set<String> emails = new HashSet<>();
		
		for (Item i : ddb.batchGetItem(spec).getTableItems().get("Students")) {			
			emails.add(i.getString("email"));
		}
		
		return emails;		
	}

	private String emailBodyFromRecord (DynamodbStreamRecord record) {
		String pid=record.getDynamodb().getNewImage().get("professorID").getS();
		String cid=record.getDynamodb().getNewImage().get("courseID").getS();
		String header = record.getDynamodb().getNewImage().get("header").getS();
		String body = record.getDynamodb().getNewImage().get("body").getS();
		String time = record.getDynamodb().getNewImage().get("time").getS();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Professor ");
		sb.append(getByID("Professors","professorID",pid).getString("name"));		
		sb.append(" of course ");
		sb.append(cid);
		sb.append("-");
		sb.append(getByID("Courses","courseID",cid).getString("name"));
		sb.append(" just posted a new announcement at ");
		sb.append(time);
		sb.append(System.lineSeparator());
		sb.append("Announcement header: ");
		sb.append(header);
		sb.append(System.lineSeparator());
		sb.append("Announcement body: ");
		sb.append(body);
		sb.append(System.lineSeparator());
		
		return sb.toString();
	}
	
	private Item getByID(String tableName, String tableKey, String keyValue) {
		return ddb.getTable(tableName).getItem(tableKey, keyValue);
	}
	
	private void sendEmailNotif(String arn, String message) {		
    	PublishRequest pr = new PublishRequest(arn, message);
    	SNS_CLIENT.publish(pr);
    }
}
