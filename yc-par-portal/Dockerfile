FROM docker.aist.io/aist_tomcat

MAINTAINER Lin Jiarong <linjr@aisthink.com>

ENV WEBAPPS_HOME=$CATALINA_HOME/webapps

ADD target/partner.war $WEBAPPS_HOME/

EXPOSE 8080
CMD ["catalina.sh", "run"]
