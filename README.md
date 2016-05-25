# spring-batch
Example project using spring-batch.



##JOB
Job have 1 step which:
- Read multiple files from resource (set default in /tmp/input/car*.csv)
- For every file:
	+ Map file data into database into Car table
	+ Move file into target resource (set by default as /tmp/output )



##Databases:
There are 2 configurations for Databases:
- MySQL
- Oracle

Every DB configuration have initialization scripts that can drop and create needed tables.


##Before run:
- Make sure directories and rights are properly set:
- 
  ``` bash
  cp -r spring-batch/src/main/resources/input /tmp/spring-batch/input
  ```
  
  ``` bash
  mkdir -p /tmp/spring-batch/output
  ```
- Make sure configuration of DB are properly set. (keep on mind db user need privileges to create tables)


TODO:
--------------
- Use quartz to run job ( new files can come to input ).

