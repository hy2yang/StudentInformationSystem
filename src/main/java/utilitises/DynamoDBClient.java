package utilitises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

@SuppressWarnings("unused")
public class DynamoDBClient {
	
	private static AmazonDynamoDB ddbClient=AmazonDynamoDBClientBuilder
			.standard()
			.withCredentials(new EnvironmentVariableCredentialsProvider())
			.withRegion("us-west-2")
			.build();
	
	private static DynamoDB ddb = new DynamoDB(ddbClient);
	
	public static DynamoDBMapper getMapper() {
		return new DynamoDBMapper(ddbClient);
	}
	
	public static Table getTableByName(String tName) {
		return ddb.getTable(tName);
	}
	
	
    public static void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
        String partitionKeyName, String partitionKeyType) {

        createTable(tableName, readCapacityUnits, writeCapacityUnits, partitionKeyName, partitionKeyType, null, null);
    }

    public static void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
        String partitionKeyName, String partitionKeyType, String sortKeyName, String sortKeyType) {

        try {

            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
            keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
                                                                                                                 // key

            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
            attributeDefinitions
                .add(new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));

            if (sortKeyName != null) {
                keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort
                                                                                                                 // key
                attributeDefinitions
                    .add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));
            }

            CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
                    .withWriteCapacityUnits(writeCapacityUnits));
            

            request.setAttributeDefinitions(attributeDefinitions);

            System.out.println("Issuing CreateTable request for " + tableName);
            Table table = ddb.createTable(request);
            System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
            table.waitForActive();

        }
        catch (Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }
	
	
	public static void main(String[] args) throws Exception {
		
		String tablename = "game-table";
		CreateTableRequest ctr = new CreateTableRequest()
				.withTableName(tablename)
				.withKeySchema( new KeySchemaElement()
						.withAttributeName("name")
						.withKeyType(KeyType.HASH)
						)
				.withAttributeDefinitions( new AttributeDefinition().
						withAttributeName("name")
						.withAttributeType("S")
						)
				.withProvisionedThroughput(new ProvisionedThroughput()
						.withReadCapacityUnits(3L)
						.withWriteCapacityUnits(3L)
						);
		
		TableUtils.createTableIfNotExists(ddbClient, ctr);
		TableUtils.waitUntilActive(ddbClient, tablename);
				
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("name", new AttributeValue().withS("WOW"));
		item.put("genre", new AttributeValue().withS("mmorpg"));
		PutItemRequest addThis = new PutItemRequest(tablename, item);
		System.out.println("PutItemRequest "+ addThis);
		PutItemResult putRes = ddbClient.putItem(addThis);
		System.out.println("PutItemResult "+ putRes);
		
	}

}
