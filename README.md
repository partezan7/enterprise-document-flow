# Enterprise document flow (Spring Boot and Vaadin application)

This repository contains the source code for
the [Enterprise document flow](https://github.com/partezan7/enterprise-document-flow).

*Live demo:* https://coming_later

## Description

This application is a working prototype for document automation in an enterprise. It is planned to create a multi-user
application with an adaptive interface, depending on the user's role.

## Running the Application

There are two ways to run the application:  using `mvn` or by running the `Application` class directly from your IDE.

## Deploying Using Docker

* Run the following command to make a production build of the Vaadin application:

  `mvn clean package -Pproduction`

* Run the following command to build your container

  `docker build . -t enterprise-document-flow:latest`

* Run the following command to run your container on localhost:

  `docker run -p 8080:8080 enterprise-document-flow:latest`

_______________________________________________________________________________________________________________________

# Документооборот предприятия (приложение Spring Boot и Vaadin)

Этот репозиторий содержит исходный код
приложения [Документооборот предприятия](https://github.com/partezan7/enterprise-document-flow).

*Живая демонстрация:* https://будет_позже

## Описание

Данное приложение представляет собой рабочий прототип автоматизации документооборота на предприятии. Планируется создать
многопользовательское приложение с адаптивным интерфейсом, зависящим от роли пользователя.

## Запуск приложения

Есть два способа запустить приложение: использовать mvn или запустить класс Application непосредственно из вашей IDE.

## Развертывание с помощью Docker

* Выполните следующую команду, чтобы создать сборку приложения Vaadin для Docker:

  `mvn clean package -Pproduction`

* Выполните следующую команду, чтобы создать контейнер:

  `docker build . -t enterprise-document-flow:latest`

* Выполните следующую команду, чтобы запустить контейнер на localhost:

  `docker run -p 8080:8080 enterprise-document-flow:latest`