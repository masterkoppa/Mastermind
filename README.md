This is the private repository for the development of the Masterming 
SE362 Group Project. 

The lastest version of the UML Design is located [here](http://www.lucidchart.com/publicSegments/view/4f6e3b96-4860-4556-af9e-7c9b0a7c4e63/image.pdf) or in the lucid chart account.

Setup Instructions:
-------------------

Create Project:
---------------

To set up your enviorment for this project you must first create an empty 
project and place the contents wherever you wish. Exit eclipse and go to 
the folder where you created you project. 


Then you must install a git client and clone the repository 
into the root folder. The repository has the following
structure:

/           --root
/README.md  --this file
/.gitignore --git file to ignore binary files
/src/       --the src dir

This src folder is where everything will be shared, please try not to add any other
folders or files unless strictly necessary. Git will not complain about other folders
there, they will just not be tracked.

Add the log4j library:
---------------------

For logging we will be using the [log4j](http://logging.apache.org/log4j/1.2/) library.

In order for you to be able to compile the code you must add this library to 
your projects class path. To do this, download the library from [here](http://logging.apache.org/log4j/1.2/download.html)
and unzip the file. Then follow the instructions on the install/readme file contained.

See [this](http://wiki.eclipse.org/FAQ_How_do_I_add_an_extra_library_to_my_project%27s_classpath%3F) to learn how to add a library
to your project class path.


Resources:
---------


* [UML Collaboration](https://www.lucidchart.com)
* [Design Documents](https://docs.google.com/)
* [Design Specifications (SE Dept) ](http://www.se.rit.edu/~se362/UnitActivities/Unit2.htm)

