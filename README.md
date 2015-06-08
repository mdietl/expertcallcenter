
# Expert Call Center
===========

A project for WMPM TU Wien

### Information

## Version : v1.0.0.0


##Running Apps:

0. Import maven project in eclipse or intellij

1. Build projects
mvn clean install

2. Run space server:
Install RabbitMQ Server https://www.rabbitmq.com/download.html

3. Run applications
java -jar [project-root]/[application-dir]/target/[application]-1.0-SNAPSHOT.jar

or

run main classes as java application in your dev environment
 ---


##Actors:

+ **User** : 
+ **Coordinator** : 
+ **Expert** : 
+ **Accounting** : 
+ **Monitoring** : 

## Release History

+ **1.0.0.0** 
	- Receiving mails (questions) from users
	- Pass message to accounting for validation via AMQP
	- Receive message from accounting
	- Content based routing
	- Send confirmation mails
	- Enrich message with tags for categorization
	- Send confirmation to accounting app (amqp)
	- Receive mail answers, aggregation by question
	- Store billing information as files in accounting app
	- Pass message to expert app (amqp)

--------

##Roadmap:
	- Receive suitable experts (amqp)
	- Send question to experts (mail)
	- Receive mail answers, aggregation by question
	- Store billing information as files in accounting app
	- Poll for billing files (file)
	- Send fee to user (mail)
	- Users simply pay their fees by clicking a link
	- Listen to HTTP requests and handle payments
	- Monitoring
	- Documentation

