# sdk-tester
Universal SDK Tester API

This exposes the "isgood" API as specified in the swagger spec in the repository.

The "isgood" API validates a Universal SDK output.

Project is built using Java/SpringBoot

##Running the app:
###Using JRE:
mvn clean package</br>
java -jar target/sdk-tester-1.0.jar

###Using docker:
mvn clean package</br>
docker build -t validate-service .</br>
docker run -p 8080:8080 -t validate-service



