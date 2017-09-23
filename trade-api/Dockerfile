FROM docker.aist.io/aist_tomcat_apr

MAINTAINER WU JIE <wujie18@centaline.com.cn>

ENV WEBAPPS_HOME=$CATALINA_HOME/webapps

ADD target/trade-api.war $WEBAPPS_HOME/

EXPOSE 8080
CMD ["catalina.sh", "run"]
