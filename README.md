Description:
============
In the demo 10 messages are sent to kafka instance. Consumer receives them and saves 

messages to database table "record", which has four columns "id", "offset", "key" and 

"value".
A single java file AivenDemo.java contains all the code. This demo used sample codes from 

the internet, including Aiven's technical support page.
The demo was tested in Eclipse with JRE 1.8. It requires kafka and PostgreSQL java client 

jars, which are not included in the GitHub repository.
For simplicity, credentials of Aiven kafka and postgreSQL cloud environments are hard-coded 

in the code.
Complile and run were done in Eclipse IDE.

Worklog
=======

10:10am 7/22/2019
Break down of work
1) Set up Aiven environments: PostgresSQL and Kafka
2) Create Eclipse project with necessary libraries
3) Top level design: Two java apps: Sender: Establish Kafka connection, send message; 

Receiver: Establish kafka and PostgresSQL connections, listen to message, retrieve and save 

in DB
4) Tools needed: Kafka and PostgresSQL clients

11:35pm 7/24/2019 (Beijing Time)
Finished coding and test-passed:

Aiven Technical Exercise: Demo Kafka with PostgreSQL
Java JDBC PostgreSQL Example
Connected to PostgreSQL database!
Message 0 produced
Message 1 produced
Message 2 produced
Message 3 produced
Message 4 produced
Message 5 produced
Message 6 produced
Message 7 produced
Message 8 produced
Message 9 produced
default [0] offset=604, key=0, value="Wed Jul 24 23:23:01 CST 2019"
Record #11 inserted
default [0] offset=605, key=1, value="Wed Jul 24 23:23:08 CST 2019"
Record #12 inserted
default [0] offset=606, key=2, value="Wed Jul 24 23:23:09 CST 2019"
Record #13 inserted
default [0] offset=607, key=3, value="Wed Jul 24 23:23:10 CST 2019"
Record #14 inserted
default [0] offset=608, key=4, value="Wed Jul 24 23:23:11 CST 2019"
Record #15 inserted
default [0] offset=609, key=5, value="Wed Jul 24 23:23:12 CST 2019"
Record #16 inserted
default [0] offset=610, key=6, value="Wed Jul 24 23:23:13 CST 2019"
Record #17 inserted
default [0] offset=611, key=7, value="Wed Jul 24 23:23:14 CST 2019"
Record #18 inserted
default [0] offset=612, key=8, value="Wed Jul 24 23:23:15 CST 2019"
Record #19 inserted
default [0] offset=613, key=9, value="Wed Jul 24 23:23:16 CST 2019"
Record #20 inserted

Appendix - Assignment

Aiven homework
==============

This exercise is meant as a recruiting homework for candidate's applying for a technical 

position within Aiven. As Aiven is a Database As a Service vendor which takes great pride 

in the easy setup of our services, we'd like to see a homework regarding the use of our 

services in a demo application. This will help familiarize the applicant with our offered 

technology stack and help us evaluate the candidate's proficiency in using the services 

Aiven offers.

Exercise
========

The exercise is simple, namely we'd like to see you use multiple services from Aiven in a 

demo application. The exercise should be relatively fast to complete, taking at most an 

hour or two.

As part of the demo application you must be able to send events to a Kafka topic (a 

producer) which will then be read by a Kafka consumer application that you've written.

The consumer application must then store the consumed data to an Aiven PostgreSQL database.

To complete the homework please register to Aiven at https://console.aiven.io/signup.html 

at which point you'll automatically be given $300 worth of credits to play around with. 

This should be enough for a few hours use of our services. If you need more credits to 

complete your homework, please contact us.

We accept homework exercises written in Python, Go, Java and Node.JS. Aiven predominantly 

uses Python itself, so we give out bonus points for candidates completing the homework in 

Python.

Automatic tests for the application are not mandatory, but again we'd like to see at least 

a description of how the application could be tested.

Submission
==========

The completed homework should be stored in a git repository on GitHub for easy access, 

please return the link to the exercise repository via email.

Besides the sourcecode, the Git repository should also contain a README file describing how 

the application can be compiled/run/tested.
