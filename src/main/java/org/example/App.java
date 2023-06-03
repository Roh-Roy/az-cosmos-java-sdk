package org.example;

import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        ArrayList<String> preferredRegions = new ArrayList<String>();
        preferredRegions.add("West US");

        CosmosClient client = new CosmosClientBuilder()
                .endpoint(AccountSettings.ACCOUNT_HOST)
                .key(AccountSettings.ACCOUNT_KEY)
                .preferredRegions(preferredRegions)
                .userAgentSuffix("CosmosDBJavaQuickstart")
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();


        //Create database
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists("first-database");
        CosmosDatabase database = client.getDatabase(databaseResponse.getProperties().getId());

        //Create Container
        CosmosContainerProperties containerProperties =
                new CosmosContainerProperties("first-container", "/country");
        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        CosmosContainer container = database.getContainer(containerResponse.getProperties().getId());

        //Insert rows
        insertItems(container);

        //Way to read all items or 1 particular item inside a partition key
        CosmosPagedIterable<HashMap> cosmosPagedIterable = container.readAllItems(new PartitionKey("Sri Lanka"), HashMap.class);
        cosmosPagedIterable.iterableByPage(10).forEach(cosmosItemPropertiesFeedResponse -> System.out.println(cosmosItemPropertiesFeedResponse.getResults()));
        CosmosItemResponse<HashMap> item = container.readItem("2", new PartitionKey("India"), HashMap.class);
        System.out.println(item.getItem());



        //Way to read all items using SQL Query

        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        //  Set populate query metrics to get metrics around query executions
        queryOptions.setQueryMetricsEnabled(true);

        CosmosPagedIterable<HashMap> itemsIterable = container.queryItems(
                "SELECT * FROM c WHERE c.country IN ('India', 'Sri Lanka')", queryOptions, HashMap.class);
        itemsIterable.iterableByPage(3).forEach(cosmosItemPropertiesFeedResponse -> System.out.println(cosmosItemPropertiesFeedResponse.getResults()));


        //Good habit to close resource
        client.close();

    }

    public static void insertItems(CosmosContainer container) {
        //Insert a row/item
        HashMap<Object, Object> data = new HashMap<>();
        data.put("roll", "1");
        data.put("id", "1");
        data.put("name", "Hey");
        data.put("country", "India");
        CosmosItemResponse itemResponse = container.createItem(data);

        HashMap<Object, Object> data1 = new HashMap<>();
        data1.put("roll", "2");
        data1.put("id", "2");

        data1.put("name", "Hi");
        data1.put("country", "India");
        CosmosItemResponse itemResponse1 = container.createItem(data1);

        HashMap<Object, Object> data2 = new HashMap<>();
        data2.put("roll", "2");
        data2.put("id", "2");

        data2.put("name", "Bye");
        data2.put("country", "Sri Lanka");
        CosmosItemResponse itemResponse2 = container.createItem(data2);
        HashMap<Object, Object> data3 = new HashMap<>();

        data3.put("roll", "2");
        data3.put("id", "4");

        data3.put("name", "Hello");
        data3.put("country", "Sri Lanka");
        CosmosItemResponse itemResponse3 = container.createItem(data3);
    }
}
