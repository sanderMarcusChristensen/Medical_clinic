# Task 1: Build a REST Service - endpoints

### GET http://localhost:7007/api/doctor

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 14:11:59 GMT
Content-Type: application/json
Content-Length: 1054

[
{
"id": 1,
"name": "Dr. Alice Smith",
"dateOfBirth": [
1975,
4,
12
],
"yearOfGraduation": 2000,
"nameOfClinic": "City Health Clinic",
"speciality": "FAMILY_MEDICINE"
},
{
"id": 2,
"name": "Dr. Bob Johnson",
"dateOfBirth": [
1980,
8,
5
],
"yearOfGraduation": 2005,
"nameOfClinic": "Downtown Medical Center",
"speciality": "SURGERY"
},
{
"id": 3,
"name": "Dr. Clara Lee",
"dateOfBirth": [
1983,
7,
22
],
"yearOfGraduation": 2008,
"nameOfClinic": "Green Valley Hospital",
"speciality": "PEDIATRICS"
},
{
"id": 4,
"name": "Dr. David Park",
"dateOfBirth": [
1978,
11,
15
],
"yearOfGraduation": 2003,
"nameOfClinic": "Hillside Medical Practice",
"speciality": "PSYCHIATRY"
},
{
"id": 5,
"name": "Dr. Emily White",
"dateOfBirth": [
1982,
9,
30
],
"yearOfGraduation": 2007,
"nameOfClinic": "Metro Health Center",
"speciality": "GERIATRICS"
},
{
"id": 6,
"name": "Dr. Fiona Martinez",
"dateOfBirth": [
1985,
2,
17
],
"yearOfGraduation": 2010,
"nameOfClinic": "Riverside Wellness Clinic",
"speciality": "SURGERY"
},
{
"id": 7,
"name": "Dr. George Kim",
"dateOfBirth": [
1979,
5,
29
],
"yearOfGraduation": 2004,
"nameOfClinic": "Summit Health Institute",
"speciality": "FAMILY_MEDICINE"
}
]
Response file saved.
> 2024-11-02T151200.200.json

Response code: 200 (OK); Time: 1098ms (1 s 98 ms); Content length: 1054 bytes (1,05 kB)


### GET http://localhost:7007/api/doctor/1

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 14:15:50 GMT
Content-Type: application/json
Content-Length: 150

{
"id": 1,
"name": "Dr. Alice Smith",
"dateOfBirth": [
1975,
4,
12
],
"yearOfGraduation": 2000,
"nameOfClinic": "City Health Clinic",
"speciality": "FAMILY_MEDICINE"
}
Response file saved.
> 2024-11-02T151550.200.json

Response code: 200 (OK); Time: 16ms (16 ms); Content length: 150 bytes (150 B)


### GET http://localhost:7007/api/doctor/speciality/SURGERY

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 14:17:11 GMT
Content-Type: application/json
Content-Length: 301

[
{
"id": 2,
"name": "Dr. Bob Johnson",
"dateOfBirth": [
1980,
8,
5
],
"yearOfGraduation": 2005,
"nameOfClinic": "Downtown Medical Center",
"speciality": "SURGERY"
},
{
"id": 6,
"name": "Dr. Fiona Martinez",
"dateOfBirth": [
1985,
2,
17
],
"yearOfGraduation": 2010,
"nameOfClinic": "Riverside Wellness Clinic",
"speciality": "SURGERY"
}
]
Response file saved.
> 2024-11-02T151711.200.json

Response code: 200 (OK); Time: 8ms (8 ms); Content length: 301 bytes (301 B)

### POST http://localhost:7007/api/doctor

HTTP/1.1 201 Created
Date: Sat, 02 Nov 2024 14:25:10 GMT
Content-Type: application/json
Content-Length: 146

{
"id": 1,
"name": "Dr. Sarah Connor",
"dateOfBirth": [
1990,
5,
15
],
"yearOfGraduation": 2015,
"nameOfClinic": "Skynet Medical Center",
"speciality": "SURGERY"
}
Response file saved.
> 2024-11-02T152510.201.json

Response code: 201 (Created); Time: 69ms (69 ms); Content length: 146 bytes (146 B)

### PUT http://localhost:7007/api/doctor/7

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 14:29:44 GMT
Content-Type: application/json
Content-Length: 154

{
"id": 7,
"name": "Dr. George Kim",
"dateOfBirth": [
1979,
5,
29
],
"yearOfGraduation": 2006,
"nameOfClinic": "Summit Health Institute",
"speciality": "FAMILY_MEDICINE"
}
Response file saved.
> 2024-11-02T152944.200.json

Response code: 200 (OK); Time: 8ms (8 ms); Content length: 154 bytes (154 B)

# Task 2  REST Error Handling

### GET http://localhost:7007/api/doctor/999

HTTP/1.1 404 Not Found
Date: Sat, 02 Nov 2024 14:30:47 GMT
Content-Type: application/json
Content-Length: 112

{
"time of error": "2024-11-02 15:30:47",
"error message": {
"status": 404,
"message": "Plant with that id not found "
}
}
Response file saved.
> 2024-11-02T153048.404.json

Response code: 404 (Not Found); Time: 833ms (833 ms); Content length: 112 bytes (112 B)

### GET http://localhost:7007/api/doctor/speciality/doctor

HTTP/1.1 400 Bad Request
Date: Sat, 02 Nov 2024 14:33:14 GMT
Content-Type: application/json
Content-Length: 111

{
"time of error": "2024-11-02 15:33:14",
"error message": {
"status": 400,
"message": "Invalid speciality provided."
}
}
Response file saved.
> 2024-11-02T153314.400.json

Response code: 400 (Bad Request); Time: 8ms (8 ms); Content length: 111 bytes (111 B)

### GET http://localhost:7007/api/doctor/birthdate/range?from=2000-01-01&to=2005-12-31

HTTP/1.1 500 Server Error
Date: Sat, 02 Nov 2024 14:34:17 GMT
Content-Type: application/json
Content-Length: 140

{
"time of error": "2024-11-02 15:34:17",
"error message": {
"status": 500,
"message": "Server error while retrieving doctors by birthdate range."
}
}
Response file saved.
> 2024-11-02T153417.500.json

Response code: 500 (Server Error); Time: 8ms (8 ms); Content length: 140 bytes (140 B)

### POST http://localhost:7007/api/doctor

HTTP/1.1 400 Bad Request
Date: Sat, 02 Nov 2024 14:38:32 GMT
Content-Type: application/json
Content-Length: 126

{
"time of error": "2024-11-02 15:38:32",
"error message": {
"status": 400,
"message": "Invalid name provided, doctor needs a name."
}
}
Response file saved.
> 2024-11-02T153832.400.json

Response code: 400 (Bad Request); Time: 760ms (760 ms); Content length: 126 bytes (126 B)

### PUT http://localhost:7007/api/doctor/7

HTTP/1.1 500 Server Error
Date: Sat, 02 Nov 2024 14:39:03 GMT
Content-Type: application/json
Content-Length: 123

{
"time of error": "2024-11-02 15:39:03",
"error message": {
"status": 500,
"message": "Server error while updating doctor data."
}
}
Response file saved.
> 2024-11-02T153903.500.json

Response code: 500 (Server Error); Time: 14ms (14 ms); Content length: 123 bytes (123 B)

# Task 3 : Generics

#### 3.2 Explain in the README.md file the purpose of generics in this exercise. Why can it be helpful?

- Generics are a powerful feature in Java that allow you to create classes, interfaces, and methods with a placeholder for types. This means that you can write code that is flexible, reusable, and type-safe. in this exercise enhances the robustness and maintainability of the code. It allows the application to be more adaptable to future changes and expansions

# Task 4: JPA and Persistence

#### GET http://localhost:7007/api/doctor

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 17:16:38 GMT
Content-Type: application/json
Content-Length: 299

[
{
"id": 1,
"name": "Dr. Alice Smith",
"dateOfBirth": [
1975,
4,
12
],
"yearOfGraduation": 2000,
"nameOfClinic": "City Health Clinic",
"speciality": "FAMILY_MEDICINE"
},
{
"id": 2,
"name": "Dr. Bob Johnson",
"dateOfBirth": [
1980,
8,
5
],
"yearOfGraduation": 2005,
"nameOfClinic": "Downtown Medical Center",
"speciality": "SURGERY"
}
]
Response file saved.
> 2024-11-02T181639.200.json

Response code: 200 (OK); Time: 1374ms (1 s 374 ms); Content length: 299 bytes (299 B)


#### GET http://localhost:7007/api/doctor/1

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 17:19:05 GMT
Content-Type: application/json
Content-Length: 150

{
"id": 1,
"name": "Dr. Alice Smith",
"dateOfBirth": [
1975,
4,
12
],
"yearOfGraduation": 2000,
"nameOfClinic": "City Health Clinic",
"speciality": "FAMILY_MEDICINE"
}
Response file saved.
> 2024-11-02T181905.200.json

Response code: 200 (OK); Time: 28ms (28 ms); Content length: 150 bytes (150 B)

#### GET http://localhost:7007/api/doctor/speciality/SURGERY

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 17:19:41 GMT
Content-Type: application/json
Content-Length: 148

[
{
"id": 2,
"name": "Dr. Bob Johnson",
"dateOfBirth": [
1980,
8,
5
],
"yearOfGraduation": 2005,
"nameOfClinic": "Downtown Medical Center",
"speciality": "SURGERY"
}
]
Response file saved.
> 2024-11-02T181941.200.json

Response code: 200 (OK); Time: 88ms (88 ms); Content length: 148 bytes (148 B)

#### POST http://localhost:7007/api/doctor

HTTP/1.1 201 Created
Date: Sat, 02 Nov 2024 17:22:08 GMT
Content-Type: application/json
Content-Length: 144

{
"id": 3,
"name": "Dr. David Park",
"dateOfBirth": [
1990,
5,
15
],
"yearOfGraduation": 2015,
"nameOfClinic": "Skynet Medical Center",
"speciality": "SURGERY"
}
Response file saved.
> 2024-11-02T182208.201.json

Response code: 201 (Created); Time: 65ms (65 ms); Content length: 144 bytes (144 B)

#### PUT http://localhost:7007/api/doctor/1

HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 17:24:27 GMT
Content-Type: application/json
Content-Length: 155

{
"id": 1,
"name": "Dr. Alice Smith",
"dateOfBirth": [
1979,
5,
29
],
"yearOfGraduation": 2006,
"nameOfClinic": "Summit Health Institute",
"speciality": "FAMILY_MEDICINE"
}
Response file saved.
> 2024-11-02T182427.200.json

Response code: 200 (OK); Time: 53ms (53 ms); Content length: 155 bytes (155 B)


# Task 5 : Automated Tests for DoctorDAO

6.4 In your README.md file, please describe the main differences between regular unit tests and tests performed in this task.? 

- Unit tests focus on testing individual components or methods in isolation. They are designed to verify that each small piece of code works as expected on its own. In contrast, the tests in this task don’t only test individual methods in isolation—they test the entire structure of the API. This includes verifying that different parts of the application, such as routes, services, and database interactions, all work together as intended.


# Task 6 : Testing the Doctor API with REST Assured

7.3 Please describe why testing REST endpoints is different from the tests you performed in Task 5? 

- whiled the last tested methods and connection to and from a database and the hold structure of the API. This test tests the output of our API. They check the sends we send out to world so we can make sure it nothing to personnel like a password with the DTO   






