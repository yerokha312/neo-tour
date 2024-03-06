# Neo Tour

This project is an assignment for 7th week of Neobis Club's Freshman program.
It is a simple application for exploring travel tours around Asia and Europe
Neo Tour is a sample online service that allows users to browse, book,
and manage booking list.

### Table of Contents

- [About](#about)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Usage](#usage)
- [Built With](#built-with)
- [Authors](#authors)
- [Contact](#contact)


### About
This is a sample Java Spring Boot application for an online service of traveling tours exploring. Users can browse
tours and book them.
The application has respective filtering and sorting algorithms. 
Key Features:
* Browse travel tours catalog
* Book tours and delete booking
* Booking history
* Add and read reviews in tour details
* Authentication and registration
* Caching listed results

### Getting Started

These instructions will get you a copy of the project up and running on your local machine for
development and testing purposes.

#### Prerequisites

Requirements to run this project:
* Java 17
* Maven 3.5+
* Spring Boot 3.2+
* PostgreSQL 15+

### Installation

To install the application you need:
1. run `git clone https://github.com/yerokha312/neo-tour.git`
2. `cd neo-tour`
3. run maven command `mvn clean install`, if you have maven installed locally. Or `./mvnw clean install` if you have no maven installed.

#### Testing

To run tests just open terminal in root directory and type `mvn clean test`. All test cases could be found in test directory of the project.

#### How To Run

After successful installation the app needs several variables:
- type into terminal `cd target/`
- `java -jar *.jar --DATABASE_URL=your-database-url(omit 'jdbc:' part of URL)
  --api_key= --api_secret= --cloud_name= --CLOUDINARY_URL= --DATABASE_URL=`

#### How To Use

Please go to http://neotour.netlify.app/ and test the site made in cooperation with frontend developer.

### Usage

* Users can browse tours without logging in and read reviews.
* Add booking after logged in.
* Leave a review.
* Registered users can make bookings and view booking history.

### Built With

[Spring Boot](https://spring.io/projects/spring-boot/) - Server framework

[Maven](https://maven.apache.org) - Build and dependency management

[PostgreSQL](https://www.postgresql.org) - Database

### Authors

[Yerbolat Yergaliyev](https://github.com/yerokha312)

[Adilet Pranov](https://github.com/PranovAdilet)

### Contact
For support, questions or suggestions please contact:

[linkedIn](https://lnkd.in/ddpDGKY2) - Yerbolat Yergaliyev

erbolatt@live.com

`date` Creation date: 14 February 2024
