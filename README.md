Sample code to manipulate Cosmos DB using Java SDK


1) Create a Cosmos DB for NoSQL from Azure portal.
2) Create a quick start maven project and update the pom.xml by comparing to this repo's pom.xml(dependency addition only)
3) Go to AccountSettings.Java and replace ACCOUNT_HOST, ACCOUNT_KEY with values from portal

4) Go to CosmosDB in portal, on left side menu click on keys--> 

Read-Write and Read only keys will be present

Under Read-write 
- URI value -- ACCOUNT_HOST
- PRIMARY KEY -- ACCOUNT_KEY

5) Run the main method and see the magic happen. We are populating some values using Hashmap else we can use custom model class as well.

We can see in portal the items are updated in the container as well as we print and fetch them in various ways.


This is just one of the ways to manipulate a CosmosDB.

Azure provides very easy quickstart projects as well.

1) Go to Cosmos DB on portal and on left menu click on 'Quick start'-- There will be various languages, we can click on Java
2) There will be 2 steps with 2 buttons, click on create item container and wait for a moment for it to be created by Azure
3) After it is completed, we can see step 2 to click on download java project button, which was disabled/greyed out earlier. Wait for the button to appear and click on download.
4) Sample java code with maven will be downloaded on local, we can extract and directly run it. All details provided by Azure along with keys.

I did face some trouble with the dependency(I guess) in the code which Azure downloaded for me, 
hence I did it in 1st way by manually creating a quick start Maven project and adding more updated version of the dependency and it worked!
