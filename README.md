# spring-batch
Example project using spring-batch with quartz.



##Quartz
Running job per 20 seconds while application is running. 



##JOB
Job have 1 step which:
- Read multiple files from resource (set default in file:/tmp/spring-batch/input/car*.csv)
- For every file:
	+ Map file data into database into Car table - mapped per 100 rows
	+ Move file into target resource when mapping finished (set by default as file:/tmp/spring-batch/output/ )



##Databases:
There are 2 configurations for Databases:
- MySQL
- Oracle

Every DB configuration have initialization scripts that can drop and create needed tables.



##Before run:
- Make sure directories and rights are properly set:

  ``` bash
  cp -r spring-batch/src/main/resources/input /tmp/spring-batch/input
  ```
  
  ``` bash
  mkdir -p /tmp/spring-batch/output
  ```
- Make sure configuration of DB are properly set. (keep on mind db user need privileges to create tables)



##Sample output execute:
```
//3 files in input folder: car01.csv,car02.csv,car03.csv >
2016-05-30 14:54:40 DEBUG JdbcBatchItemWriter:167 - Executing batch with 100 items.
2016-05-30 14:54:40 DEBUG JdbcBatchItemWriter:167 - Executing batch with 32 items.
2016-05-30 14:54:40 DEBUG MultiResourceUpdateStepListener:72 - File "car03.csv" moved
2016-05-30 14:54:40 DEBUG MultiResourceUpdateStepListener:72 - File "car01.csv" moved
2016-05-30 14:54:40 DEBUG MultiResourceUpdateStepListener:72 - File "car02.csv" moved
2016-05-30 14:54:40 DEBUG CVSToDBQuartzJobBean:32 - Job finished. Status : COMPLETED 1
// Files moved by application from input into output folder
// Added 2 files onto input folder (copy manually) car04.csv and car05.csv
2016-05-30 14:55:00 DEBUG JdbcBatchItemWriter:167 - Executing batch with 100 items.
2016-05-30 14:55:00 DEBUG JdbcBatchItemWriter:167 - Executing batch with 100 items.
2016-05-30 14:55:00 DEBUG JdbcBatchItemWriter:167 - Executing batch with 25 items.
2016-05-30 14:55:00 DEBUG MultiResourceUpdateStepListener:72 - File "car05.csv" moved
2016-05-30 14:55:00 DEBUG MultiResourceUpdateStepListener:72 - File "car04.csv" moved
2016-05-30 14:55:00 DEBUG CVSToDBQuartzJobBean:32 - Job finished. Status : COMPLETED 2
// Files moved by application from input into output folder
2016-05-30 14:55:20 WARN  MultiResourceItemReader:177 - No resources to read. Set strict=true if this should be an error condition.
2016-05-30 14:55:20 DEBUG CVSToDBQuartzJobBean:32 - Job finished. Status : COMPLETED 3
// No files in input folder -> nothing happened
2016-05-30 14:55:40 WARN  MultiResourceItemReader:177 - No resources to read. Set strict=true if this should be an error condition.
2016-05-30 14:55:40 DEBUG CVSToDBQuartzJobBean:32 - Job finished. Status : COMPLETED 4
// No files in input folder -> nothing happened
```