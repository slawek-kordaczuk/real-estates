# real-estates

An application that calculates the average price of real estate in a given region.

Hexagonal architecture was used (to easily use different infrastructure like message broker, 
repository or REST controller) with DDD on code level (to easily extend functionalities in the future).

H2 was used as the database to easily run the application without the need to use an external database.

WireMock was used to mocking the external API. 
The responses from the external API are in files in the container/src/main/resources/mock_responses path.
In order to limit the number of files with mocked responses from an external server, only one page has been mocked for each region
