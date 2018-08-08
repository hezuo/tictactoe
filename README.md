# TicTacToe

## Requirements

 - Java 8
 - Maven 3.3.3

## Installation Steps

#### 1. Modify file configuration.properties to include the desired configuration:
```
playgroundSize=4
player1=a
player2=b
computer=c
```
** Keep in mind that the symbols for the playes and computer must always be a single character and must be unique.


#### 2. Build and Run the executables: 

Build the executable jar
```
mvn package
```

after this a file "app-1.0-SNAPSHOT.jar" will be created inside the folder target 


To run the executable, an argument is mandatory, which is the filename of the configuration file
```
java -jar target/app-1.0-SNAPSHOT.jar configuration.properties
```