# Spring application with webflux

## Description 
This application is having 2 rest endpoints

/traditional: this is working with basic rest endpoint 
/reactive: this is working with reactive rest endpoint

## External Services
We have an jar file that needs to be started before starting the WebfluxApplication

To start it run:
java -jar external-services.jar --server.port=7070

You can access the application on http://localhost:7070/