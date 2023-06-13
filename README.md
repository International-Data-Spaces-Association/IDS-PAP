![Fraunhofer](https://img.shields.io/badge/Fraunhofer-IESE-179C7D.svg?logo=fraunhofergesellschaft)
![apache-2.0](https://img.shields.io/badge/apache_2.0-license-D22128.svg?logo=apache) ![maven](https://img.shields.io/badge/maven-bulid-C71A36.svg?logo=apachemaven) ![javaScript](https://img.shields.io/badge/JavaScript-language-F7DF1E.svg?logo=javascript) ![Yarn](https://img.shields.io/badge/Yarn-package-2C8EBB.svg?logo=yarn) ![spring](https://img.shields.io/badge/spring-framework-6DB33F.svg?logo=spring) ![springboot](https://img.shields.io/badge/spring_boot-configuration-6DB33F.svg?logo=springboot) ![react](https://img.shields.io/badge/react-library-61DAFB.svg?logo=react) ![Java](https://badges.aleen42.com/src/java.svg)
# ODRL Policy Administration Point
The ODRL PAP webservice is a demonstrator to show how IDS or ODRL policies (of [IDS contract policy classes](https://github.com/International-Data-Spaces-Association/IDS-G/tree/UC-branch/UsageControl/Contract)) can generated and be translated using a user interface. It is a Spring-Boot application,
The Policies can be currently translated to Text or the enforceable MY DATA policy language. We are able to interpret policies using the IDS contract policy class patterns of the following formats:

- Infomodell java implementation version 4
- Infomodell java implementation version 5
- ODRL PAP generatet IDS -Format
- ODRL PAP generatet ODRL Format

## Maven
### Download
Download maven [here](https://maven.apache.org)
### Dependencies
#### Policy Library
To be able to run it, it is necessary to install  the maven project Policy Library first.
You can get it [here](https://gitlab.cc-asp.fraunhofer.de/iese-ids/policy-library).

    <dependency>  
         <groupId>de.fraunhofer.iese.ids.odrl</groupId>  
         <artifactId>policy-library</artifactId>  
         <version>${version}</version>  
    </dependency>

#### MY DATA Translator
To be able to run it, it is necessary to install  the maven project MY Data Translator.
You can get it [here](https://gitlab.cc-asp.fraunhofer.de/iese-ids/mydata-translator).

    <dependency>  
         <groupId>de.fraunhofer.iese.ids.odrl</groupId>  
         <artifactId>mydata-translator</artifactId>  
         <version>${version}</version>  
    </dependency>

### Installation
To install the project to your local maven repository run:

    mvn clean install
To install skip the tests:

     mvn clean install -DskipTests
### run it
To run the project using maven

    mvn spring-boot:run

