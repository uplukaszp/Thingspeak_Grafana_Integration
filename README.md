# Description
It allows you to view data collected at thingspeak.com on the Grafana platform. It uses [json plugin](https://grafana.com/grafana/plugins/simpod-json-datasource) for Grafana.

# Features
* Generating a list of channels and Fields during the Query creation
* Showing data in Grafana from thingspeak.com based on created queries.

* Handling thingspeak query parameters:
  * results=[number of entries to retrieve (8000 max)]
  * days=[days from now to include in feed]
  * start=[start date] – YYYY-MM-DD%20HH:NN:SS
  * end=[end date] – YYYY-MM-DD%20HH:NN:SS
  * offset=[timezone offset in hours]
  * status=true (include status updates in feed)
  * location=true (include latitude, longitude, and elevation in feed)
  * min=[minimum value to include in response]
  * max=[maximum value to include in response]
  * round=x (round to x decimal places)
  * timescale=x (get first value in x minutes, valid values: 10, 15, 20, 30, 60, 240, 720, 1440)
  * sum=x (get sum of x minutes, valid values: 10, 15, 20, 30, 60, 240, 720, 1440)
  * average=x (get average of x minutes, valid values: 10, 15, 20, 30, 60, 240, 720, 1440)
  * median=x (get median of x minutes, valid values: 10, 15, 20, 30, 60, 240, 720, 1440).
  
  <b>Example</b>
  
  To get average of 30 minutes, greather than 10, put:
  ```JSON
  {
    "average":30,
    "max":10
  }
  ```
  in section "Additional JSON Data", in query editor.
# Installation
Clone repository to your machine using:
```bash
git clone https://github.com/uplukaszp/Thingspeak_Grafana_Integration
```
Then go to the project directory:
```bash
cd Thingspeak_Grafana_Integration/
```
Program need your thingspeak account API key, to fetch data about your channels. API key can be found at: [https://thingspeak.com/account/profile](https://thingspeak.com/account/profile) in section ThingSpeak Settings - User API Key.

## Installation using maven
To build the project:
```bash
mvn package
```
To run the project:
```bash
cd target
java -jar  --thingspeak.apiKey==YOUR_ACCOUNT_API_KEY
```
By default application will be start at port 8080

## Installation using docker
Project can be run as docker image. Before build the image, you should specify ThingSpeak API Key in file application.properties, as in example file.

### Installation using docker compose
TODO

# Registration datasources
* In grafana go to the Configuration - Data Sources and click "Add Data Source" button.
* Next choose "JSON".
* In url section provide following value: 
~~~
http://application_ip:application:port 
~~~
For example it will be:
~~~
http://localhost:8080
~~~
* Click Save & Test to confirm changes.

# Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
# Tech stack
* Spring Boot
* Lombok
* JUnit
* Mockito
* Docker
* Docker Compose
