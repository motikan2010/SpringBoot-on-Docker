#!/bin/bash

cp ../build/libs/demo-0.1.jar .
cp -r ../config .

docker build -t spring-boot .

