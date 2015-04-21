# stock-ex
A project for WMPM TU Wien

#Running Apps:

1. Build projects
mvn clean install

2. Run space server:
Install RabbitMQ Server https://www.rabbitmq.com/download.html

3. Run applications
java -jar [project-root]/[application-dir]/target/[application]-1.0-SNAPSHOT.jar

@Ramon:
- Das sind mal die voräufigen Build Scripts
- Wenn du was im Domain Modul änderst, dann musst ein mvn clean install machen
- Wenn du ein Modul von IntelliJ startest must du in der RunConfiguration von jeder main Class die "Program Arguments setzten" -> --spring.profiles.active=[amqp,space] --additional_arg=value