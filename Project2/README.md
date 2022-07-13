# Running on Eclipse
We will be assuming that you will be running the project on Eclipse. Once you have chosen your workspace on eclipse, please right click on an empty portion of the file explorerer as seen below and click on "Import..."

![Import a Repo](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/import.JPG)

Next, you will be asked to select an import wizard. Click on the arrow next to "Git" and a dropdown will appear. Click on "Projects from Git (with smart import)" as seen below.

![GitHub Import](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/git%20im.JPG)

Next, click on Clone URl, then click on next. In the URl textbox, you should type in the link to our repository. In this case, our repository is "https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19". Copy and paste this link into the URl textbox as shown in the picture below then click next and enter in your University of Minnesota login information.

![GitHub URl](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/url.JPG)

There will be only one branch to choose from and it should automatically be checked. If not, click on the box next to the master branch and a check will appear. Ensure that the master branch is checked as shown then click next. 

![GitHub Branch to Import](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/branch.JPG)

It will ask you for a directory to clone the repository into. Simply click next. If it indicates that the directory you've chosen is not an empty directory, you can simply add something to the name to allow Eclipse to create a new directory for you as shown below. Click next after this is done and login with your UMN information again.

![GitHub Directory Error](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/import2.JPG)

If this happens, just change the name, maybe to something like this:

![GitHub Directory Error Fixed](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/import3.JPG)

Next, you will be shown a page called "Import Projects from File System or Archive. Ensure that the folder that you created in the previous page is checked as shown below. After this is completed, click "Finish"

![Import Projects from File System or Archive](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/import4.JPG)

You should now have all the files in your Package Explorer! Congratulations! In order to run the program, navigate to the "Project 2" folder and click arrow next to the folder to show the other files located within it. Locate the src file and do the same thing to ensure that all class .java files or packages are within this folder. Right click on the election package and click "Run As.." then click "Java Application" as shown below

![Run as Java Application](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/Runas.JPG)

Lastly, a "Select Java Application" will appear, giving you several options to choose as the main function. Please select "VotingSystem - election" to run the program and then click "OK". The program should now be running.

![Choosing the Main Function](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/Voting.JPG)

After pressing the Process Votes button on the window, you will be able to begin processing votes. The window should look just like below. By clicking on select file(s), you can select the election file you wish to process. In addition, you can change where you want the audit files and media files to appear via the configurations. Good luck!

![The Voting System](https://github.umn.edu/umn-csci-5801-S21-001/repo-Team19/blob/master/Project2/images/system.JPG)


# Tests

Our test classes can be found in src/. In order to run the test file, follow the steps above, however, instead of running a package such as "election", you will be running a class file instead. Look for the file, UnitTester.java, and then follow the same steps above to run the file as a Java Application.

# Utility File

EFmaker class can be used to generate IR and OPL election files of arbitary size, with varying party, candidate, and ballot information. It is a very useful file than can allow the user to make files of any size very quickly. You can create these files in any kind of main program and it will generate onto your computer.

