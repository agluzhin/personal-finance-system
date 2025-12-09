# Personal Finance System

---
**Описание:**
> Spring Boot in-memory приложение для работы с личными финансами.
---
## Содержание
* [Технологии](#технологии)
* [Структура проекта](#структура-проекта)
* [API Endpoints](#api-endpoints)
* [Примечания](#примечания)
---
## Технологии
* Java 17
* Spring Boot 3.4.12
* Database (In-Memory - HashMaps)
* Maven
---
## Структура проекта
```
project-root/
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/app/agluzhin.personal_finance_system
│  │  │  ├─ core
│  │  │  │  ├─ controllers
│  │  │  │  ├─ dtos
│  │  │  │  ├─ entities
│  │  │  │  ├─ repositories
│  │  │  │  ├─ services
│  │  │  │  └─ utils
│  │  │  └─ PersonalFinanceSystemApplication.java
│  │  └─ resources/...
│  └─ test/...
├─ agluzhin.personal_finance_system.postman_collection.json
├─ HELP.md
├─ mvmw
├─ mvmw.cmd
├─ pom.xml 
└─ README.md
```
---
## API Endpoints

Документация API (спецификация endpoint'ов):

BASE_URL = http://localhost:8080/api

| Метод | Endpoint              | Описание                                                               |
|-------|-----------------------|------------------------------------------------------------------------|
| GET   | /users                | Получить всю информацию о пользователях                                |
| GET   | /users/{id}           | Получить информацию о пользователе по "id" (PathVariable)              |
| POST  | /users/create         | Создать пользователя по "login" и "password" (RequestParams)           |
| PATCH | /users/{id}/set       | Установить статус активности пользователю с "id" (PathVariable) по "isActive" (RequestParam) |
| PATCH | /users/{id}/authorize | Авторизовать пользователя с "id" (PathVariable) по "login" и "password" (RequestParams)      |
| GET   | /wallets              | Получить всю информацию о кошельках                                    |
| GET   | /wallets/{id}         | Получить информацию о кошелька по "id" (PathVariable)                  |
| GET   | /wallets/{id}/info    | Получить сводку о кошельке с "id" (PathVariable): общий доход/расход, доход/расход по категориям, оставшиеся бюджеты |
| POST  | /wallets/{id}/categories/add | Добавить категорию в кошелек с "id" (PathVariable) по "type", "name" и "value" (RequestParams)                |
| DELETE| /wallets/{id}/categories/delete | Удалить категорию из кошелека с "id" (PathVariable) по "type" и "name" (RequestParams)                     |
| POST  | /wallets/{id}/budgets/add | Добавить бюджет в кошелек с "id" (PathVariable) по "name" и "value" (RequestParams)                              |
| DELETE| /wallets/{id}/budgets/delete | Удалить бюджет из кошелека с "id" (PathVariable) по "name" (RequestParam)                                     |
---

