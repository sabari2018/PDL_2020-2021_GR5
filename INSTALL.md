# Install

You will have to use an IDE. This documentation is for IntelliJ and Eclipse. 
This will allow Maven to automatically import the missing libraries to properly run the project.  
Everything is now set and you are ready to run the program.

*The following installation process is for IntelliJ :*  

Requirements :
- [IntelliJ](https://www.jetbrains.com)
- [Maven](https://maven.apache.org)

Start by opening IntelliJ and close all opened projects.
Then get the project with the HTTPS link :
  - Click "File" -> "New" -> "Project from Version Control" (Sometimes you have to choose Git after)
  - Intellij version 2020 : Select "GitHub"
  - On "Git Repository URL" put the HTTPS link of this project
  - Set your project directory and parent directory as you wish
  - Click "Clone" when you are ready
  - Open the project
  - Open the Maven panel (on the right) and click the refresh button if no project is shown in
  - If the tab Maven is not present on the right : Click on "View" -> "Tools Windows" -> "Maven"
  - Unfold the project in the Maven panel
  - In "LifeCycle" select "Clean" and "Compile" to run the project
To execute the project go in "Pdl/src/main/model" and run the class "Main".
  
*The following installation process is for Eclipse :*

Requirements:
- [Eclipse](https://www.eclipse.org)

Start by opening Eclipse and close all opened projects. 
To open the git project with the HTTPS link : 
  - Click on "Window" -> "Perspective" -> "Open Perspective" -> "Other" 
  - Select "Git" and open
  - In the left click on : "Clone a git repository"
  - Paste the HTTPS link of the git project in "URI"
  - Click : "Next" -> "Next" -> "Finish"
  - Click : "File" -> "Import" -> "Maven" -> "Existing Maven project" -> "Next"
  - You choose the directory of your git project
  - "Finish"
To run the project : 
  - Right click on pom.xml 
  - Click on "Run as" -> "Maven clean" and "Maven install"
To execute the project go in "Pdl/src/main/model" and run the class "Main".
