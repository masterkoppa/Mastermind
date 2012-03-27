README
=======
This is the private repository for the development of the Masterming 
SE362 Group Project. 

The lastest version of the UML Design is located [here](http://www.lucidchart.com/publicSegments/view/4f6e3b96-4860-4556-af9e-7c9b0a7c4e63/image.pdf) or in the lucid chart account.

Setup Instructions
==================

Getting Familiar with the Repository
------------------------------------

The repository has the following structure:

./             
./README.md    
./.gitignore   
./src/         

This src folder is where everything will be shared, please try not to add any other
folders or files unless strictly necessary. 

Project Packages
-----------------

The project will be divided into the following packages to allow for better handling:

mastermind.gui - For any view and GUI related code
mastermind.core - All the data models and any core functionality
mastermind.test - All JUnit Tests should be used here (JUnit4)
mastermind.helper - Any helper classes that don't belong anywhere else.

Downloading the Repository
---------------------------

Go to the folder were you want to setup your project. And follow the following instructions.

### Command line way

If you are using a command line git interface you need to run the following command:  

     git clone git@github.com:masterkoppa/Mastermind.git

Please double check that the address `git@github.com:masterkoppa/Mastermind.git` is the 
same that appears in in your view of the repository above.

### GUI Tool

If you are using a GUI Tool, use [TortoiseGIT](http://code.google.com/p/tortoisegit/) and clone the repository using
the address in this page.

Creating the project
--------------------

Open eclipse up and go to File->New->Java Project. On the wizard for a new Java Project look for
location. Unmark "Use defaul location" and select the location where the repository was downloaded.
Then press finish.

Now you should have the latest copy of the project on that computer. Your eclipse 
specific settings will already be ignored so feel free to modify them as long as you
don't break anything for anyone else.

Add the log4j library
----------------------

For logging we will be using the [log4j](http://logging.apache.org/log4j/1.2/) library.

In order for you to be able to compile the code you must add this library to 
your projects class path. To do this, download the library from [here](http://logging.apache.org/log4j/1.2/download.html)
and unzip the file. Then follow the instructions on the install/readme file contained.

See [this](http://wiki.eclipse.org/FAQ_How_do_I_add_an_extra_library_to_my_project%27s_classpath%3F) to learn how to add a library
to your project class path.


Resources
=========


* [UML Collaboration](https://www.lucidchart.com)
* [Design Documents](https://docs.google.com/)
* [Design Specifications (SE Dept) ](http://www.se.rit.edu/~se362/UnitActivities/Unit2.htm)
* [Git, the simple guide. No deep shit!](http://rogerdudler.github.com/git-guide/)

