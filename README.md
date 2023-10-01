# Para subir a aplicação
***Necessário java 17***
1. executar o seguinte comando no terminal dentro da pasta do projeto:  docker compose -f docker/kafka-compose.yml up -d
2. criar tabela no postgres: CREATE TABLE IF NOT EXISTS notes(id SERIAL PRIMARY KEY, title varchar(255), content varchar(255), done boolean);
3. subir a aplicacao: ./gradlew bootRun
4. entrar no container do kafka que subiu no passo 1: docker exec -it kafka-container /bin/bash
5. acessar o cli do kafka para enviar mensagens: cd /usr/bin/ && kafka-console-producer --bootstrap-server localhost:9092 --topic note-topic --property parse.key=true --property key.separator=:
6. dentro do cli do kafka colar a mesagem e apertar enter: xpto:{"title":"xpto123123","content":"xpto 123123321","done":false}
