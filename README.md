# forecast-rest-api

 
## Requirements
This project handles the 2 use cases:
* As a business owner I want to be able to load a new set of suppliers into my webshop using an REST service
* As a business owner I want to be able to query my forecast towards glasses and frames using a REST service which outputs JSON data
 
 
## Short Description
This REST service has been built using [Play Framework](https://www.playframework.com/). A Model-Controller architecture has been used to seperate the different layers of the application. The model part to handle all the business data and calculations, and the controllers to handle the HTTP requests properly. 
   
In this MVP I decided to be able to modify the list of suppliers in-memory by making use of a mutable list. This can bring side-effect. A solution could be to make use of an immutable list and connect the service to a dedicated database and withdraw the saved data from the database before calculating the forecast. However, the suppliers data needs to be deleted from the database before each load and this might increase the response time as you need to operate several actions to the database. Another alternative I found is migrating the code by to [http4s](https://http4s.org/) with [IO](https://typelevel.org/cats-effect/docs/2.x/datatypes/io) from cats-effect to handle more "purely" side-effects. As I was interested by http4s I'm currently working on it on another feature branch. 
   
To handle client errors each data model has been validated to be parsed and written in json, and in case of server internal error the ```play.http.errorHandler = play.api.http.JsonHttpErrorHandle``` have been added to the application.conf file returning a json to the client.


## Requests
I personnaly used [postman](https://www.postman.com/) to test my different requests but they can be tested as well using curl  directly from the terminal console. Once you have run the application (see *How to run section*), you can make use of the different requests handled by the REST service from another terminal console.

### Load the suppliers
As an example, you can load the following list of suppliers by running:   
```
curl -X POST \
  http://localhost:9000/suppliers/load \
  -H 'content-type: application/json' \
  -d '{
        "suppliersInfo": [{
                "name": "GlassesRUs",
                "age": 4
        }, {
                "name": "Redstick",
                "age": 10
        }]
}'
```  
 in another terminal. The list of suppliers will be loaded in ```suppliersList``` and a ```RESET_CONTENT``` status code will be sent.  
 Each time you use the request again the former ```suppliersList``` will be empted and only the new suppliers will be loaded.

### Get the Suppliers
```
curl -X GET http://localhost:9000/forecast/suppliers
```
enables the client to get the list of suppliers. A ```NO_CONTENT``` status code will be sent if the suppliers list is empty.

### Get the forecast stock
You can get the forecast stock at T = 113 days for example by running:
```
curl -X GET http://localhost:9000/forecast/stock/113
```
With the suppliers list loaded in the *Load the suppliers* section for example, the result should be:
```
{
    "glasses": 100,
    "frames": 19000
}
```

### Get the production suppliers data
At T = 113 days again, the request will be:
```
curl -X GET http://localhost:9000/forecast/suppliers/113 
```
and the answer with the same list of providers:
```
{
    "suppliers": [
        {
            "name": "GlassesRUs",
            "age_in_days": 117,
            "last_day_of_frame_production": 112
        },
        {
            "name": "Redstick",
            "age_in_days": 123,
            "last_day_of_frame_production": 103
        }
    ]
}
```
If the suppliers list is empty it will simply return:
```
{
    "suppliers": []
}
```
## Model
The model layers gathers the business data and the forecast
### Business data
1. SupplierInfo: encapsulating the data for one supplier
2. Suppliers: encapsulating the list of all SupplierInfo
3. Stock: encapsulating the stock of glasses and frames
4. Production: encapsulating the suppliers production information

### Forecast
The Forecast class gathering the different requirements in terms of forecast calculations making use of the business data.

## Prerequesites
The program has been implemented using the following versions:
* sbt 1.5.2
* Java 11.0.12
* Scala 2.13.6.

## Tests
The ```ForecastController``` requests have been tested through the ```ForecastControllerSpec``` test class.  
The Model layer have been tested in the ```ForecastModelSpec``` test class.
To run the tests, run the command ```sbt test``` from the root directory of this program.

## How to run
At the directory root of this project, simply run:
```sbt run``` from a terminal console to run the application on localhost.
