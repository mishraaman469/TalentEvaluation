FROM maven:3.6-jdk-8-slim
EXPOSE 8080
COPY ./ ./
ADD target/talent.jar talent.jar
ENTRYPOINT ["java","-jar","/talent.jar"] 