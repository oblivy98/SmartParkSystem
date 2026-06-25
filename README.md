|| SMARTPARK SYSTEM ||

In order to run this system. I recommend having this environment setup in the system

   |   Java: 25 (25.0.2)
   |   Maven: 3.9.13

once Java and Maven is installed in your system. Pull the repository on your respective repository folder (On my case, D:/dev/repository)

Once you have pulled the repository on your local environment. This will be the step in order to run the SmartPark System

|| SETUP GUIDE for SmartPark System ||
1) Open your preferred IDE(**Intellij is recommended**)
   -- The next steps are done with Intellij, other IDE such as Visual Studio Code or Eclipse might have a different setup --
3) Import/Open Project the SmartPark repository. Then wait until the repository is fully loaded on the IDE
4) **In intellij, Go to the right side bar and find the Maven Tab. Click Sync all Maven Projects, then after successfully syncing, Click Generate Sources and Update Folders
5) On the Intellij IDE Terminal(I recommend git bash), check first if your terminal can detect mvn by running this command "mvn --version". Make sure that it shows your Java Version and Maven version before proceeding.
6) Run this command on the terminal "mvn clean install". During this process, it will build the application
7) Once you have build the application, from Intellij IDE, go to the top right bar and navigate to Run/Debug Configuration then click Edit Configuration
8) A pop-up window will show up. Click the Plus Sign then it will show an add new configuration window. From here, find Application
9) Once you have added the Application, you can add the Main Class "SmartparkApplication" and edit the name.
10) Once this is done, click Apply. Then you can now click the Play button from the top bar navigation.
11) All of the Dummy data(Sample Vehicle, Parking lot, and Username and password are initialized during application initialization.
12) Once running, you can now test the API Endpoints.

|| Testing Endpoints with Postman Collection ||

To fully simulate login and authentication through our endpoints, we have to configure the environment and the collection of the postman
|| Using Exported Postman Collection with JSON ||
