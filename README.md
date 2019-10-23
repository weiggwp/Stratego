# Welcome to Stratego
This is a single player Stratego game.

## Teammates:
Ying Zhang, Evan Rubinovitz,  Chuangwei Ma

## Overview
1. __Front-end__  
  The web pages are rendered using Thymeleaf and HTML, 
  but actions triggers JavaScript functions to send HTTPS request to back-end and update the pages with response.
2. __Back-end__  
  Java Spring framework provides the infrastructure so we can focus on the application. 
  Specifically, 
    Spring Boot framework is used to build production ready application for easy deployment.
    Spring Security is used to secure the web pages and user verifications.
    Gradle is used to automatic download,configure, and manage all the dependencies the application needs.
  

3. __Database__  
MySQL is used to store permanent data.
The database and schema were created using MySQLWorkbench, 
but models were created in Java classes then mapped to relational entities and populated the tables in database automatically by Spring.  

4. __Deployment__
The database was deployed on AWS RDS.
The application was packaged into a Jar file and deployed on AWS ES2 using Beanstalk.
