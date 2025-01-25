[![Docker Pull](https://img.shields.io/badge/docker_image-pull-blue)](https://hub.docker.com/repository/docker/3425149/livecoding-api/general)

# О приложении
livecoding-platform-api - это бэкэнд для live coding платформы. На платформе можно писать и исполнять кода на Java.

# Настройки приложения
Приложение стартует на http://localhost:8080
Для того, чтобы приложение обрабатывало запросы с фронэнда в [application.yaml](src/main/resources/application.yaml) нужно указать адрес 
фронтенда с которого будут приходить запросы. Узнать адрес фронтенда можно открыв станицу фронетда из репозитория [livecoding-platform-ui](https://github.com/ap-konovalov/livecoding-platform-ui) в браузере.

# Сборка и запуск docker образа
Команда для сборки образа с приложением: 
```shell 
docker build -t livecoding-api .
```

После сборки запустить контейнер с приложением можно с помощью команды: 
```shell 
docker run -p 8080:8080 livecoding-api
``` 
Чтобы образ можно было легко загрузить и запустить на любом сервере нужно запушить его в docker hub.
Чтобы запушить локальный образ в docker hub необходимо:
1. Залогиниться (для этого вы должны быть ранее зарегистрированы на https://hub.docker.com/):
```shell 
docker login
```
2. Переименовать образ по паттерну: `docker tag your-image-name your-dockerhub-username/my-repo:tag`
Где:
your-image-name — имя вашего локального образа.
your-dockerhub-username — ваше имя пользователя в Docker Hub.
my-repo — имя репозитория в Docker Hub.
tag — тег версии образа (например, latest, v1.0, и т.д.).<br>
Пример:
```shell
docker tag livecoding-api 3425149/livecoding-api:latest
```
3. Отправить образ в docker hub:
```shell
docker push 3425149/livecoding-api:latest
```
Теперь образ с приложением можно загрузить на любую машину где есть docker в 1 команду:
```shell
docker push 3425149/livecoding-api:tagname
```