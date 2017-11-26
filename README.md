# Booker
> Manage your books

Booker is a simple grails application built to demo a few things:

- CouchDB
- Docker
- Grails Swagger API documentation.

Booker provides simple CRUD API to manage books. The application stores data in a CouchDB database.

## Getting Started:

- You can get started by cloning the repo and importing the projcet into intelliJ
- In the intellij Run configuration set environment variable for pointing your application to couchDB
-- `COUCH_DB_SERVER_URL`. E.g. if you are running it couchdb locally set it to `http://localhost:5984`

## API Documentation:

- You can view the APIs for this application at by using the self hosted Swagger client.
- Once you run the application you can visit the following URL: 
--  [http://localhost:8080/webjars/swagger-ui/2.2.5/index.html?url=/apidoc/getDocuments]

## Docker

- Code for docker is available in the `release` branch
