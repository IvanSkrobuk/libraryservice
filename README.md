
# Сервис Библиотеки - Микросервисная архитектура

Репозиторий содержит четыре микросервиса:
1. **AuthService**: Отвечает за аутентификацию пользователей и генерацию JWT токенов.
2. **BooksService**: Управляет операциями с книгами, такими как добавление, просмотр, обновление и удаление книг.
3. **LibraryService**: Отвечает за учёт доступных и взятых на чтение книг.
4. **EurekaService**: Управляет регистрацией и поиском микросервисов с использованием Netflix Eureka.

## Предварительные требования

Убедитесь, что у вас установлены следующие инструменты:
- JDK 17 или выше
- Maven
- PostgreSQL

## 1. Настройка PostgreSQL

1. Создайте базу данных:
    ```sql
    CREATE DATABASE librarydb;
    ```

2. Создайте пользователя и предоставьте ему доступ к базе данных:
    ```sql
    CREATE USER postgres WITH PASSWORD '1233210';
    GRANT ALL PRIVILEGES ON DATABASE librarydb TO postgres;
    ```

3. Запомните эти данные для настройки подключения в файле `application.properties`.

## 2. Настройка файла application.properties

Откройте файл `src/main/resources/application.properties` в каждом микросервисе и добавьте следующие настройки для подключения к PostgreSQL:

```properties
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=postgres
spring.datasource.password=1233210

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 3. Запуск сервисов

Каждый микросервис можно запустить независимо на своём порту.

- **AuthService**: Обрабатывает аутентификацию (генерацию JWT).
- **BooksService**: Предоставляет API для работы с книгами.
- **LibraryService**: Управляет учётом доступных и взятых книг.
- **EurekaService**: Управляет регистрацией и поиском микросервисов.

### Доступ к приложению

- По умолчанию, основной Library API доступен на порту `8080`. API для работы с книгами доступен по адресу:
    ```
    http://localhost:8080/books
    ```

## 4. Использование приложения

- Вы можете получить список всех книг, добавить новую книгу, редактировать или удалить существующую через эндпоинт `/books`.
- API библиотеки (`/library`) предоставляет функционал для отслеживания доступных и взятых книг.

### Swagger

- Рекомендуется использовать Swagger для удобной работы с API. После запуска каждого сервиса, Swagger интерфейс будет доступен по следующим ссылкам:
  - **AuthService**: `http://localhost:8081/swagger-ui.html`
  - **BooksService**: `http://localhost:8080/swagger-ui.html`
  - **LibraryService**: `http://localhost:8082/swagger-ui.html`

## 5. Дополнительно

- Убедитесь, что PostgreSQL запущен перед запуском микросервисов.
