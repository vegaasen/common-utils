#SUIVEG::CommonUtils

##Introduction

Common utils was created/born as a project in January 2012 when Marius and Vegard found out that we way too often wrote the same code over and over again in many different projects. To be a bit more efficient we started to create a small library that had all this functionality built in. This has proven to be a great idea, and we're using this library many places right now.

New to version 2 will be the removal of all refering jars. We will provide all access (where possible) ourselves. This is to minimalize the possibility of jar-collide when this jar is beeing used in a e.g project.

##Contributions

If you like it, why not just fork the ever on-going "project", and submit your own additions? 

Enjoy it, we do!

##So what utilities does this include?

We have functionality for the following elements in this project:

* Database connection tools
* File handling
* FTP
* Simple image handling 
* Encryption

We will try to update this README as we progress.
You can also visit our [old google code page](http://code.google.com/p/common-utils-suiveg/) (note: this is a dead project-page, please do not check out the code from that location. Thanks).

##Majors to be done

We are working with TDD for this project at the moment, but it is far from finalized. Updates will arrive every now and then!

##Releases

##Release-0.2

New:

* EncryptionUtils
* SSHUtils
* PropUtils
* StringUtils
* IOUtils -- COMING SOON!

Changed:

* FileUtils
* ImageUtils
* FTPUtils
* Removing all of the commons-utils delivered by the Apache Commons-project. This is due to unwanted dependencies in the pom.xml for projects where this is already included (jar/java-version-mismatches)

New additions:

* FileUtils

##Release-0.1

This was the Initial Release

Release contained:

* FileUtils
* FTPUtils
* URL/HTTPUtils
* ImageUtils
