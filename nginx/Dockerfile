FROM nginx:latest
LABEL maintainer "user <user@example.com>"

RUN mkdir -p /var/log/example/nginx/

COPY header.conf /etc/nginx/
COPY server-api.conf /etc/nginx/
COPY nginx.conf /etc/nginx/
