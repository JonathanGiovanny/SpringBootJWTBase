# H V2
__Description:__
Spring boot base project that uses JWT as login token, the project removed all Spring Security innecessary configuration.
Includes Hibernate Validator and ExceptionHandler

## DB schemes
__H2__ data related schema
__Neo4j__ security schema

## Instructions
1. Create neo4j admin user
```
CALL dbms.security.createUser('admin', 'your_pass', false)
```


2. Generate the classes needed from the mapper and the DSLQuery
```
mvn clean install
```

3. You can run the app now

### IDE configuration
Add the target/generated-sources to the build path in order the IDE to find out the generated classes 

### What's comming
- Add Jacoco to validate coverage
- Add Exceptions service to handle the exceptions handled, to print them and save them if necessarily