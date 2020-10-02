# Install

Depending on the IDE that you chose, you will have to import the pom.xml file into your project.  
This will allow Maven to automatically import the missing libraries to properly run the project.  
Everything is now set and you are ready to run the program.

*The following installation process is for IntelliJ :*  

Requirements :
- IntelliJ
- Maven

Start by opening IntelliJ and close all opened projects.
Then get the project with the HTTPS link :
  - Click "Check out from Version Control" on IntelliJ
  - Select "GitHub"
  - On "Git Repository URL" put the HTTPS link of this project
  - Set your project directory and parent directory as you wish
  - Click "Clone" when you are ready
  - Open the project
  - Open the Maven panel (on the right) and click the refresh button if no project is shown in
  - Unfold the project in the Maven panel
  - In "LifeCycle" select "Clean" and "Compile" to run the project Or you can run mvn clean install compile command in terminal of Intellij
