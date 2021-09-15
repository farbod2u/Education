FROM openjdk:18-slim-buster
COPY ./target/education-1.0.jar  /home/hr/education-1.0.jar
CMD ["java", "-jar", "/home/hr/education-1.0.jar"]
