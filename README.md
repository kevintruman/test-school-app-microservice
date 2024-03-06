
# School Foo

Simple microservice application using java technology and springboot framework.

### Architecture
![school-foo](images/ss.png?raw=true "school-foo")

#### - mw-config
retrieve all configs on each service

#### - mw-discovery
monitor the service that is running

#### - mw-gateway
as routing and filtering incoming requests

#### - be-user
service for authorization 

#### - be-lesson, be-attendance
as the main service of the application

#### - fe-app
as the web application, open in http://localhost:8094/

## Required

- JDK 21 Version


## How to run

### clone the project

```bash
$ git clone https://github.com/kevintruman/school-foo.git
```

### build the project

```bash
$ cd school-foo
$ mvnw clean package -DskipTests
```

### run script for database in `script` directory
```bash
$ cd script
```

### run middleware

#### - run mw-config
```bash
$ java -jar mw-config/target/mw-config-1.0.0.jar
```

#### - run mw-discovery
```bash
$ java -jar mw-discovery/target/mw-discovery-1.0.0.jar
```

open http://localhost:8761/ to see the service running

#### - run mw-gateway
```bash
$ java -jar mw-gateway/target/mw-gateway-1.0.0.jar
```

### run main app

#### - run be-user
```bash
$ java -jar be-user/target/be-user-1.0.0.jar
```

#### - run be-lesson
```bash
$ java -jar be-lesson/target/be-lesson-1.0.0.jar
```

#### - run be-attendance
```bash
$ java -jar be-attendance/target/be-attendance-1.0.0.jar
```

you can access the Api from http://localhost:8080

- `/user/v1.0` - for user app
- `/lesson/v1.0` - for lesson app
- `/attendance/v1.0` - for attendace app


#### - run fe-app
```bash
$ java -jar fe-app/target/fe-app-1.0.0.jar
```
you can access the web app from http://localhost:8094



## Api Collection

- Sign In

```
POST: localhost:8080/user/v1.0/auth/sign-in

headers:
{
    "content-type": "application/json"
}

body:
{
    "username": "root",
    "password": "root"
}
```



- Validate token

```
POST: localhost:8080/user/v1.0/auth/validate-token

headers:
{
    "content-type": "application/json"
}

body:
{
    "token": "eyxxx.xxx.xxx"
}
```



- Register Bulk Student

```
POST: localhost:8080/user/v1.0/register/student-bulk

headers:
{
    "content-type": "application/json",
    "authorization": "Bearer eyxxx.xxx.xxx"
}

body:
{
    "classId": <Long>,
    "students": [
        {
            "fullName": "",
            "dob": "yyyy-MM-dd",
            "pob": "",
            "phone": "",
            "gender": "<MALE|FEMALE>",
            "guardian": {
                "id": <Long>,
                "fullName": "",
                "phone": "",
                "gender": "<MALE|FEMALE>"
            }
        }
    ]
}
```


- Attendance Teacher

```
POST: localhost:8080/attendance/v1.0/auth/attend

headers:
{
    "content-type": "application/json",
    "authorization": "Bearer eyxxx.xxx.xxx"
}

body:
{
	"date": 1709086683000,
	"lat": 1.234234,
	"lon": 0.56868657
}
```


- History Attendance Today

```
GET: localhost:8080/attendance/v1.0/auth/attend

headers:
{
    "content-type": "application/json",
    "authorization": "Bearer eyxxx.xxx.xxx"
}
```


- Attendance Student

```
POST: localhost:8080/attendance/v1.0/auth/attend/student

headers:
{
    "content-type": "application/json",
    "authorization": "Bearer eyxxx.xxx.xxx"
}

body:
{
	"studentsId": [<Long>],
	"lessonScheduleId": <Long>,
}
```


- Schedule Lesson Today

```
GET: localhost:8080/lesson/v1.0/schedule

headers:
{
    "content-type": "application/json",
    "authorization": "Bearer eyxxx.xxx.xxx"
}
```

