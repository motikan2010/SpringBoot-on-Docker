FROM openjdk:8u181

LABEL maintainer "maintainer <user@example.com>"

ADD ./demo-0.1.jar /var/opt/local/
ADD ./config /var/opt/local/config

CMD ["/var/opt/local/demo-0.1.jar", "start"]
