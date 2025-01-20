# О приложении
livecoding-platform-api - это бэкэнд для live coding платформы. На платформе можно писать и исполнять кода на Java.

# Настройки приложения
Приложение стартует на http://localhost:8080
Для того, чтобы приложение обрабатывало запросы с фронэнда в [application.yaml](src/main/resources/application.yaml) нужно указать адрес 
фронтенда с которого будут приходить запросы. Узнать адрес фронтенда можно открыв станицу фронетда из репозитория [livecoding-platform-ui](https://github.com/ap-konovalov/livecoding-platform-ui) в браузере.

# Сборка и запуск docker образа
Команда для сборки образа с приложением: `docker build -t livecoding-api .`
После сборки запустить контейнер с приложением можно с помощью команды: `docker run -p 8080:8080 livecoding-api` 



