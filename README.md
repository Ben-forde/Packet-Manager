# cosc201 algorithims and Structures.
This is the second assignment for which i recived full marks (20%). The code below was not fully mine as code classes were given at the start (location, packetHoarder,packetManager, randomWarehouse, and warehouse). the task was to implement 4 mangers (basic, loadAware, priority, and one of our choice). Changes where requried in other parts of the program aswell though. 


## Changes outside of the 4 mangers: 
* Lines 53 to 80 were all implemented to create the comparisons for the priority queue. 
* Lines 30 to 50 in warehouse was also required for my implementations.



## Changes I would make if I were to refactor this: 
  * I would put the methods sendDiagonal and sendStraight into the packet class as i am reusing code which doesnt need to be done. 

## How to switch between mangers: 
* Lines 47 to 50 are how you switch between different implentations. currently basicManager is selcted but to switch just comment out basicManger and uncomment another.


