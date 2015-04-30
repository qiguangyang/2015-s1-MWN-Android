# Instruction for add your projects to the repo,

1. git clone this repo to your computer;
2. copy your project to this repo, keep the root folder so that we can use it for both android part and server part
3. git push your codes

==========================================================================================================================
# Instruction for the server setup

1. Please setup MySQL in your local env.
2. Please import the project as "Existing Maven Project"
3. Please customize the settings below:
	src/main/webapp/WEB-INF/jdbc.properties (point to your local DB, remember to grant privileges on the user)
4. Setup the runtime (right click on the project -> properties -> Project Facets -> Runtimes -> New -> Apache Tomcat V8.0)
5. Update Maven Dependencies (right click on the project -> Maven -> Update Project)
6. Fix build path errors if any
7. Start the Mysql service and run src/main/resources/DemoDBscript.sql in your schema
8. You should be able to run the application on tomcat now (using localhost:8080/Watchdog/list to test)