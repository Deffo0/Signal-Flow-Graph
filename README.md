# Signal-Flow-Graph
## Table of Contents
- [Overview](#Overview)
- [Setup](#Setup)
- [Design Decisions](#Design-Decisions)
- [Main Modules](#Main-Modules)
- [Sample Runs](#Sample-Runs)
---
## Overview
+ Web-App includes representation for SFG and calculations for:
  - Overall Transfer Function.
  - Individual Loops.
  - Forward Paths.
  - Non-touching Loops.
  - Deltas.
---
## Setup
> 1. First of all: you must install the `Front-End` folder which contains angular project, using npm install just because node modules may be missing. 
> 1. Secondly: Spring Boot folder is straight forward just open the pom file using any IDE.
> 1. Thirdly: you run the Angular project and on localhost:4200 and the Spring Boot project on localhost:8080.
---
## Design Decisions
+ Apply the MVC architecture in the design, as we have a model for the data, which we received from the clients, 
a controller to manage all the business logic and data manipulation and a view represented in the front end(Angular) to show the data after performing any kind of operations on it to the clients.
+ Source and Sink Nodes were predetermined.
---
## Main Modules
### Node:
  - It contains all information about each node in the back, creating new ones and gets the neighbors of the node.
### Network Analyser: 
  - It determines and returns the forward paths, loops, non-touching loops, deltas and overall TF of the system. 
### Network Optimizer:
  - It converts the recieved hashMap of strings to list of `Node` objects and optimize the edges.
### Machine: 
  - It collects information about each node in the front, its order, color and position.
---
## Sample Runs
+ **First Network**:
![image](https://raw.githubusercontent.com/Deffo0/Signal-Flow-Graph/main/Sample-Runs/Run1.1.png?token=GHSAT0AAAAAABOS2XDBHJAPNAFWON2ZJUDWYTKYBTQ)
+ **First Result**:
![image](https://raw.githubusercontent.com/Deffo0/Signal-Flow-Graph/main/Sample-Runs/Run1.2.png?token=GHSAT0AAAAAABOS2XDAK4TMRK6DQV2YR7QMYTKYDVA)
+ **Second Network**:
![image](https://raw.githubusercontent.com/Deffo0/Signal-Flow-Graph/main/Sample-Runs/Run2.1.png?token=GHSAT0AAAAAABOS2XDBYDVXHLU6RE3RCFS2YTKYENA)
+ **Second Result**:
![image](https://raw.githubusercontent.com/Deffo0/Signal-Flow-Graph/main/Sample-Runs/Run2.2.png?token=GHSAT0AAAAAABOS2XDAAIYJMOBYK3BH64LYYTKYFCA)
