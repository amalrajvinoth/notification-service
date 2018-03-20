## Synopsis

**Generic Notification System**   
This project demonstrate sample generic library for notification.

Following capabilities are supported:

1. Accept messages including from, to and subject
2. Ability to notify on multiple channels (email, slack)
3. Deliver messages in correct order

## Technologies

1. Spring Boot - 2.0
2. Jackson
3. Gradle
4. Swagger
5. simpleslackapi
6. spring-boot-email-tools

## Installation

Run `gradlew bootRun` to start the server manually.

To access Rest API doc: `http://localhost:8080/api`

## API Reference

**API 1: Notify to a Channel**

URL: `http://<HOST>/notifier/{channelType}/notify`

This sends given message to a specified channel like slack or email.
Where the `channelType` is slack or email.

e.g: `http://localhost:8080/api/v1.0/notifier/{channelType}/notify`   
with body as:
```javascript 
{  
   "body": "Body of the message",  
   "from": "sender@gmail.com",  
   "subject": "Notification Service Test Subject",  
   "to": "receiver@gmail.com"  
 }
```

**API 2: Notify All**

URL: `http://<HOST>/notifier/notifyAll`

This sends given message to all configured channels like slack and email.

e.g: `http://localhost:8080/api/v1.0/notifier/notifyAll`
with body as: 
```javascript 
{  
   "body": "Body of the message",  
   "from": "sender@gmail.com",  
   "subject": "Notification Service Test Subject",  
   "to": "receiver@gmail.com"  
}
```
 
## Tests

Run `gradlew test` from console.

## Settings
1. Make sure all email and slack properties are configured properly in `application.properties```
2. If GMail ia configured as your mail service then set `Allow less secure apps = ON` in Sing-in & Security of your google account to send message properly .


