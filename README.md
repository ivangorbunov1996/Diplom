# Дипломный проект по профессии Тестировщик ПО
## Требования для запуска тестов:
### Необходимое окружение:
- Инструменты: IntelliJ IDEA, Git, GitHub, Docker Desktop, Java, MySQL, PostgreSQL.

### Запуск Тестов:
1. Склонировать [репозиторий](https://github.com/ivangorbunov1996/Diplom) командой `git clone` на локальный компьютер;
2. Запустить на компьютере Docker Desktop.
3. В IntelliJ IDEA открыть терминал.
4. Выполнить команду: `docker-compose up`.
5. Дождаться сборки контейнера.
5. В IntelliJ IDEA открыть новую вкладку терминала.
6. Выполнить команду для запуска `aqa-shop.jar` для одной из СУБД:
- MySQL: `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
- PostgreSQL: `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
7. В IntelliJ IDEA дважды нажать Ctrl и в открывшемся окне в зависимости от выбранной СУБД выполнить команду:
- MySQL: `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
- PostgreSQL: `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
8. Выполнить команду `./gradlew allureServe` для генерации отчета Allure.
