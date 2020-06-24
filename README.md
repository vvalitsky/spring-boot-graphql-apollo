# Example of using GraphQL Apollo Federation + Spring Boot

###### Official documentation about Spring -> https://spring.io
###### Official documentation about Apollo Federation -> https://www.apollographql.com/docs/apollo-server/federation/introduction/

##### For starting Spring Boot:
````

./gradlew clean build bootRun

````
![start_spring_boot](https://raw.githubusercontent.com/vvalitsky/spring-boot-graphql-apollo/master/screenshots/start_spring_boot.png)

##### For starting Apollo Federation:
````

npm install && NODE_ENV=local npm run start-gateway

````
![run_apollo_federation](https://raw.githubusercontent.com/vvalitsky/spring-boot-graphql-apollo/master/screenshots/run_apollo_federation.png)

##### Configuration of Apollo Federation
###### apollo-federation/gateway.js and you can add another services :)
![apollo_configuration](https://raw.githubusercontent.com/vvalitsky/spring-boot-graphql-apollo/master/screenshots/apollo_configuration.png)

#### For test, you can use Altair GraphQL Client

##### Example of query
![use_altair_for_run_query](https://raw.githubusercontent.com/vvalitsky/spring-boot-graphql-apollo/master/screenshots/use_altair_for_run_query.png)

##### Example of mutation
![use_altair_for_run_mutation](https://raw.githubusercontent.com/vvalitsky/spring-boot-graphql-apollo/master/screenshots/use_altair_for_run_mutation.png)

##### Example of a query with not all fields after update
![major_thing_about_graphql](https://raw.githubusercontent.com/vvalitsky/spring-boot-graphql-apollo/master/screenshots/major_thing_about_graphql.png)
