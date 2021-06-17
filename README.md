# RUG-Software-Engineering-RogerThatProject
Cainarean Constantin, Denis Garabajiu, Valeria Mavceanscaia, Gasan Rzaev
## General Rules for this git repository
</br>
Each student in the group will have different tasks and some of them might result into changes in the same file, therefore we can't work all on the same branch.
Before starting doing your task you MUST:</br>

**1.** Open an Issue for your current task. The name of the issue will have the following structure: ID: "name of the task". There can't be 2 issues with same ID. </br>
**2.** Create a new branch related to your issue (github will propose to do this for you, but if you choose to do this manually, the branch must have the same name as the issue it's related to.)</br>
**3.** After you finished doing your work and you tested the functionality, create a merge request with master, see if you have any git conflicts, solve them and after that you can push to master.</br>
</br>
By following this simple rules we can be sure that someone's mistakes will not affect the work of other students.

## Backend and DB
Our application uses a MySql DB, the configuration file is in root folder/src.../resources/application.properties
In order to compile our program change : <br />
quarkus.datasource.jdbc.url=jdbc:mysql://127.0.0.1:3306/**rogerthat** with the name of your db(it must be empty and have no schema)<br />
quarkus.datasource.username=root - here put your DB credentials<br />
quarkus.datasource.password=rogerthat<br />
if you compile the application for the first time also use:<br />
#quarkus.hibernate-orm.database.generation = none<br />
quarkus.hibernate-orm.database.generation = create<br />
if it is not for the first time use : <br />
quarkus.hibernate-orm.database.generation = none <br />
#quarkus.hibernate-orm.database.generation = create <br />
Also you must change <br />
csv.directory=/Users/c.c.1/Desktop/RUG-Software-Engineering-RogerThatProject/csv/ to a location where the user's csv uploaded files will be stored. After you have done this, go to the root folder of our program and run it with the comand:<br />


```bash
$ mvn clean compile quarkus:dev

```
<br /> The backend application should now be running on `localhost:8080`

## Web Application
Our web application has been implemented using the Angular framework.

### Deploying an instance of the web application
The web application can be deployed easily. NodeJS and Angular should be present on the system in order to deploy the instance. The following instructions are written with Ubuntu 20.04 in mind, but instructions should be relatively similar for most other Linux, Mac OS or Windows based operating systems.

1. Install NodeJS and Angular
```bash
$ sudo apt-get install curl
$ curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -
$ sudo apt-get install nodejs
$ npm install -g @angular/cli
```
2. Navigate to the _webApp/_ folder
```bash
$ cd webApp/
```
3. Install dependencies
```bash
$ npm install
```
4.  Run the application
```bash
$ ng s -o
```

The web application should now be running on `localhost:4200`
