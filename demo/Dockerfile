# Dockerfile
FROM mcr.microsoft.com/openjdk/jdk:8-mariner

RUN yum update -y \
    && yum install -y glibc-devel git gcc \
    && yum install -y tar \
    && yum install -y vim \
    && yum clean all

WORKDIR /app

COPY . /app



