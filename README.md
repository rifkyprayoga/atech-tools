[![Release](https://jitpack.io/v/com.atech-software/atech-tools.svg)](https://jitpack.io/#com.atech-software/atech-tools)

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/andyrozman/atech-tools/maven.yml)



# Atech-Tools
ATech Tools (library for help with development in Java (and also C#). (moved from sf.net on 10th March 2024).

## Origins
This is helper library I was implementing since I started working in Java (back on 2000). 

## History
This repository was hosted on sourceforge.net for quite a while and it moved from different types of repositories 
(CVS -> SVN -> GIT) and now its been moved her, because of much better availability and build options support.

# Atech-Tools (Java)

## History of changes 
In last few years (last 15 or so) I worked mostly on java part, so all changes can be found in java subfolder in 
file CHanges.txt.

## Building
This is simple java project with maven build, so it can be rebuild very easilly... 

## Usage

Add following repository:

```xml
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```


And then following dependency in your code (change 0.8.7 with latest version - see badge on top of this document):

```xml
	<dependency>
	    <groupId>com.atech-software</groupId>
	    <artifactId>atech-tools</artifactId>
	    <version>0.8.7</version>
	</dependency>

```



