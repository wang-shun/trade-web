FROM docker.aist.io/aist_tomcat_apr

MAINTAINER Lin Jiarong <linjr@aisthink.com>

ENV WEBAPPS_HOME=$CATALINA_HOME/webapps

ADD target/trade-mobile.war $WEBAPPS_HOME/

EXPOSE 8080
CMD ["catalina.sh", "run"]
