# Njiwa -  Open Source Embedded M2M UICC Remote Subscription Management Server

## What Is It

 Njiwa (Swahili for homing pigeon) is an implementation of the GSMA's
 [Embedded SIM Remote Provisioning Manager](https://www.gsma.com/iot/embedded-sim/) for M2M devices. Because M2M devices are likely installed in remote or 
 unreachable areas, the GSMA came up with with specifications to enable remote
 activation of network profiles (and other settings) on the SIMs in these devices. Please read through the [this site](https://www.gsma.com/iot/embedded-sim/how-it-works/) to familiarise yourself with the GSMA Embedded SIM Architecture. 

## Status & Roadmap
  The diagram below is taken from the GSMA specification. It shows the main
  elements in the embedded SIM ecosystem.

    <img alt="RSP Architecture Elements" src="etc/arch.png"></img>
 
  Currently Njiwa implements the SM-DP and SM-SR in the above architecture. 

  Mostly these are feature complete as per the [specifications](https://www.gsma.com/newsroom/all-documents/sgp-02-v3-2-remote-provisioning-architecture-for-embedded-uicc-technical-specification/). Please consult the issues list for more information on what's not quite done or needs improvement.
 
 Our future plans (2019-2020) include the eUICC operating system implementation. We will publish detailed plans and specifications during the course of work.

## System Requirements
 Njiwa is a Java-based server Web Service. It has been developed and tested
 on [JBOSS Wildfly v11.0](http://www.wildfly.org), but should run well under
 your favourite Web Services container. The main requirements for developing with or running Njiwa are:

* Java 8 (JDK/JRE 1.8) or better
* [JPA 2.x/Hibernate](http://hibernate.org)
* A relational SQL database (we use [PostgreSQL](http://www.postgresql.org)
* [Redis](http://redis.io) is used for caching transactional data
* [Gradle](https://gradle.org) is used as the build tool. 
  
## Getting Started

 In order to build Njiwa after downloading the source:

* Change to the folder *build*
* Run *gradle init* if this is the first time you are performing a build
* Run *gradle war* to build the *WAR* file. This will be placed in the sub-directory *build/libs* if all goes well.

## Deploying the WAR file to Wildfly

If this the first time you are deploying the WAR file to Wildfly/JBOSS, you must create 
certain resources needed to run Njiwa. Take note of the Wildfly/JBOSS home directory, referred to 
 as *${WILDFLYHOME}* below.

* If it does not exit, create the directory *${WILDFLYHOME}/modules/io/njiwa/main* and copy the 
  files *njiwa.settings* and *modules.xml* from the source sub-directory *etc/wildfly* into it. 
  These files are respectively the module registration and the server settings (for now very minimalist).
* Create a basic security domain called *njiwa* in Wildfly, with authorization policy set to *PermitAll*. Your *security-domains* configurations in the Wildfly XML configuration file should have an entry like this:
<pre>
<security-domain name="njiwa" cache-type="default">
                    <authorization>
                        <policy-module code="PermitAll" flag="optional"/>
                    </authorization>
                </security-domain>
</pre>
* Create the database for Njiwa and then create the JTA JDBC data source in Wildfly that will enable
  Njiwa connect to the database. The JNDI name must be *java:/njiwa*. [Here is the documentation for doing this in Wildfly 10.x](https://docs.jboss.org/author/display/WFLY10/DataSource+configuration). 

 You may deploy the WAR file in the usual JBOSS/Wildfly manner (usually a matter of copying it to
 *${WILDFLYHOME}/standalone/deployments*. 

## Getting Help, Helping Out, etc.

 We welcome feedback, assistance, etc. Please use the issues tab on github to send any of these, 
 or ping us directly at dev@njiwa.io - we'd love to hear from you.

 Thank you!

 
