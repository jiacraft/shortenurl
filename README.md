#######################################################################################
  - A Spring boot based application, offer shorten URL service, 
  	can be containized as standard Docker image
  - Cache: Service APIs is cache enabled, but for this demo project, we do not use a specific 
    caching library. The cache store by Spring default is ConcurrentHashMap
  - Data Repository: Shorten URL and their original URL are stored in two ConcurrentHashMap 
  - Limitations: current cache and data repository supports single Server instance scenarios.
    To support distributed multi server instance, need to add centrolized DATA storages 
    (AWS RDS. Dynamos DB, etc). For distributed cache, the service could integrate with 
    distributed cache service (MEMCACHE, for example)  
#######################################################################################


- github link: https://github.com/jiacraft/shortenurl

- The app requires Java 8 

- to test this service locally, built by maven, type:

	1. Download the zip from github link

	2. unzip the zip and, 
	
		mvn spring-boot:run			
		
		or:

		mvn clean package
		java -jar target/shortenurl-service.jar

	3. Access the service by either http POST to get shorten URL, or http GET to get original/long URL, described as next section

- There are two service APIs, 1. http POST to get shortened URL; 2. http GET to get original/long URL

	1. To shorten a URL, run http POST, against:

		http://{ServiceBaseHostname}/url

	        At local, {ServiceBaseHostname} shoild be: localhost:5000

	   Request Body should be JSON format, as:

		{ "longUrl" : "{TheURLToBeShorten}"}

	   e.g.:

		{"longUrl" : "http://myurl.for.shorten/asds" }

	   Sample response:

		{
                        "shortUrl": "http://intu.su/Sbs8WTp",
                        "longUrl": "http://longulr.toshorten.myurl.com"
                }

	   NOTE: the value of 'longUrl' should have valid URL format, 

	2. To query/get the long url by shorten URL, run http GET, with URL format as:

		http://{ServiceBaseHostname}/url?shortUrl=http://intuit.su/{ShortenKey}	

	   Sample request:

		http://localhost:5000/url?shortenUrl=http://intu.su/Sbs8WTp

	   Sample response,

		{
			"shortUrl": "http://intu.su/Sbs8WTp",
    			"longUrl": "http://longulr.toshorten.myurl.com"
		}

########################################################################################
 Steps to create docker image and run the app in Docker container
 
 NOTE: docker need to be up and running prior to perform the following steps
########################################################################################

- to create docker image, from where the project root (where pom.xml is located), type:

	mvn package docker:build

  a docker iamge should be created with name: bjia/intuit-social-service

- to run app from local docker container (need to have docker/Toolbox installed )	

 	- start a docker machine (use default is ok)

		docker-machine start default

	- check and record the ip of docker machine

		docker-machine ip default

	- connect current shell to the machine (default) 

		eval "$(docker-machine env default)"

	- run docker container for the image created of our app, need to bind container port to host port

		docker run -p 8080:5000 bjia/intuit-social-service

	- to access the application

		http://$(docker-machine env default):8080/feed
