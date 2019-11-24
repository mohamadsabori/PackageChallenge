{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf600
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww19880\viewh12440\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs44 \cf0 Package Challenge Assignment\

\fs26 Created by: Mohamad Sabrori, November 24th, 2019\

\fs24 \

\b\fs40 Technologies and Logic
\b0 \

\fs26 \

\fs36 The solution utilizes Java programming language using Maven for its build phases. \
The solution includes separate packages for Model and main classes. This is used to separate the logics and concerns of each one of the classes.\
When the program runs, it reads the input from the file provided. Then it instantiates needed objects from the information of the file and determines which things can be packed as a package. At the end, it evaluates if more than one package with the same price exists and puts the one with the lesser weight in the final output as stated in the document.\
Based on the algorithm used, a sorted map is used to hold the final values to know that the last key is always the most expensive thing. The map keys are cost of the things and values are indexes of the things.\
Ultimately, a map of total cost of things as keys and n packages that have equal costs. If the value list has more than one instance, we get the package with lesser weight as the final output.\
For testing purposes, the JUnit and Mockito frameworks are utilized. The solution is well tested under different scenarios and reactions using unit tests and it\'92s TDD based. \

\b\fs40 \
Building source files
\b0\fs36 \
There\'92s a build.sh file which builds the whole solution as a Jar file. It can be used as a library and the method pack is available in the Packer class as a static method. }