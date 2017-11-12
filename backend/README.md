In order to run application:
1. You need to download tomcat server, you can get it from:
https://tomcat.apache.org/download-80.cgi 
download file according to your operating system

2. You need to install eclipse for Java EE developers (Eclipse oxygen for example).

3. From Eclipse; add tomcat as run time environment by going to following screen:
Windows  Preferences  Server  Runtime Environments  Add  [select path where you extracted tomcat]

4. Import backend application from File  Open Projects from File System option. Select path where you checked out backend application from git.

5. After import; Right click on application and select Run  Run on Server.
**
In this step, you might be asked to select run time environment and deploy application if you running this application for the first time
**
