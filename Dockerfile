FROM amazoncorretto:11
MAINTAINER Suleiman Suleiman
ADD target/softwareTesting-0.0.1-SNAPSHOT.jar softwareTesting-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","softwareTesting-0.0.1-SNAPSHOT.jar"]
