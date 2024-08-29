
1. Установить необходимые инструменты

Убедитесь, что у вас установлены следующие инструменты:
- JDK 17 или выше.
- Maven.
- PostgreSQL.

2. Настроить PostgreSQL

1. Создать базу данных :
    
    CREATE DATABASE librarydb;
    

2. Создать пользователя и предоставьте ему доступ к базе данных:
    
    CREATE USER postgres WITH PASSWORD '1233210';
    GRANT ALL PRIVILEGES ON DATABASE librarydb TO postgres;
  
3. Записать эти данные для настройки подключения в `application.properties`.

3. Настройка application.properties

Откройте файл `src/main/resources/application.properties` и добавьте следующие настройки:

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=postgres
spring.datasource.password=1233210

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.show_sql=true
spring.mvc.hiddenmethod.filter.enabled=true
spring.cloud.config.enabled=false



4. Доступ к приложению

Приложение будет доступно по умолчанию на порту `8080`. Откройте браузер и перейдите по адресу:

http://localhost:8080/books


5. Использование приложения

- Вы сможете получить список всех книг, добавить новую книгу, редактировать или удалять существующую по /books, 
а также использовать функциональность библиотеки (LibraryService) для учёта свободных книг по /library.

6. Дополнительно

- Убедитесь, что PostgreSQL запущен перед запуском приложения.

