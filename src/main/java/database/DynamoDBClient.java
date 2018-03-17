package database;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

public class DynamoDBClient {
	
	static AmazonDynamoDB ddb;
	
	
	
	/**
	 * @throws Exception
	 */
	private static void init() throws Exception{
		
		ProfileCredentialsProvider credentialsProvider= new ProfileCredentialsProvider();
		ddb = AmazonDynamoDBClientBuilder
				.standard()
				.withCredentials(credentialsProvider)
				.withRegion("us-west-2")
				.build();
	}
	
	public static void main(String[] args) throws Exception {
		
		init();		
		String tablename = "game-table";
		CreateTableRequest ctr = new CreateTableRequest()
				.withTableName(tablename)
				.withKeySchema( new KeySchemaElement()
						.withAttributeName("name")
						.withKeyType(KeyType.HASH)
						)
				.withAttributeDefinitions( new AttributeDefinition().
						withAttributeName("genre")
						.withAttributeType(ScalarAttributeType.S)
						)
				.withProvisionedThroughput(new ProvisionedThroughput()
						.withReadCapacityUnits(3L)
						.withWriteCapacityUnits(3L)
						);
		
		TableUtils.createTableIfNotExists(ddb, ctr);
		TableUtils.waitUntilActive(ddb, tablename);
				
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("name", new AttributeValue().withS("WOW"));
		item.put("genre", new AttributeValue().withS("mmorpg"));
		PutItemRequest addThis = new PutItemRequest(tablename, item);
		System.out.println("PutItemRequest "+ addThis);
		PutItemResult putRes = ddb.putItem(addThis);
		System.out.println("PutItemResult "+ putRes);
		
	}

}
