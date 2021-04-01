## Command to run the app
    ./mvnw spring-boot:run -Dspring-boot.run.arguments=--input.file=<input_file_location>
    example: ./mvnw spring-boot:run -Dspring-boot.run.arguments=--input.file=/home/setu/Downloads/input.csv

    It can be run without supplying input file, in that case it would use the sample-data.csv shipped with this code base
    example: ./mvnw spring-boot:run

## Output
    For each validated record a file with name <MSISDN>.txt can be found at "out" directory
    Validated records are also persisted in in-memory hsqldb db using JPA(default implementation is hibernate)
## ToDo
    In order to have full test coverage more test case need to be added specifically to test against persistance storage
