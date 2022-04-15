
---
# Drone Dispatch Application

## About
* REST API service that allows Drone dispatch medications.
---
[[_TOC_]]
## Table of contents
>* [Title](#drone-dispatch-application)
>* [About](#about)
>* [Table of contents](#table-of-contents)
>* [Requirements](#requirements)
>* [Installation | Build](#installation--build)
>* [Usage | Run](#usage--run)
>* [APIs](#apis)
>    * [Register a drone](#register-a-drone)
>    * [Load a drone with medication items](#load-a-drone-with-medication-items)
>    * [Get loaded medication items for a given drone](#get-loaded-medication-items-for-a-given-drone)
>    * [Get drones available for loading](#get-drones-available-for-loading)
>    * [Get drones available for loading](#get-drones-available-for-loading)
>    * [Get battery level for a given drone](#get-battery-level-for-a-given-drone)
>
---
# Requirements
* Java 11 SDK or newer.
* Maven 3.6 or newer.
---
# Installation | Build
* The service can be installed by running system shell (command line) in the project folder and using the below maven
command.

```
mvn clean install
```
---
# Usage | Run

* To run the service, locate the `/target folder` in the project folder and enter the below command. 
* Note that `/target folder` - is created after a successful installation/build.

```
java -jar drone-dispatch-0.0.1-SNAPSHOT.jar
```
---
# APIs
## Register a drone:
- Url: `http://localhost:8080/rest/drone
- Request type: POST
- Request body Sample
```
{
    "serialNumber":"350315e9-d90f-43f0-8226-5e5de0cced3f",
    "model":"Lightweight",
    "weightLimit":"100",
    "state":"IDLE",
    "batteryCapacity": 35
}
```

## Load a drone with medication items:
- Url: `http://localhost:8080/rest/drone/medications
- Request type: PUT
- Request Parameters Sample
```
    KEY1 : serialnumber 
    VALUE1: 350315e9-d90f-43f0-8226-5e5de0cced3f
    
    KEY2 : medicationcodes 
    VALUE3 : 6aa89d53-72dc-4f90-bea5-6c4a7d90f2a0, 14d7b017-ae4f-4c75-92e4-ac086b9813f4

```
## Get loaded medication items for a given drone:
- Url: `http://localhost:8080/rest/drone/medications
- Request type: GET
- Request Parameter Sample
```
{
    KEY : serialnumber 
    VALUE: 350315e9-d90f-43f0-8226-5e5de0cced3f
}
```
## Get drones available for loading:
- Url: `http://localhost:8080/rest/drones
- Request type: GET


## Get battery level for a given drone:
- Url: `http://localhost:8080/rest/drone/battery
- Request type: GET
- Request Parameter Sample
```
{
       KEY : serialnumber 
       VALUE: 350315e9-d90f-43f0-8226-5e5de0cced3f
}
```
---
