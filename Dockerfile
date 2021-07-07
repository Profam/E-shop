FROM tomcat:9.0.50-jdk11-adoptopenjdk-hotspot
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/*.war /usr/local/tomcat/webapps/E-shop.war
CMD ["catalina.sh","run"]