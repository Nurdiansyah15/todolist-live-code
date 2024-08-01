FROM openjdk:17-alpine

ADD target/todolist-0.0.1-SNAPSHOT.jar todolist.jar

CMD ["java", "-jar", "todolist.jar"]