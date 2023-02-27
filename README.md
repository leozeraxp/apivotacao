# Desafio Sicredi <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"  target="_blank"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"  target="_blank"> <img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white"  target="_blank">

Olá, este projeto de trata de um Desafio da Sicredi para fazer um sistema de votação para os associados!

## Guia de Instalação para testes

<b>O que é necessário para começar</b>
- MySQL
- JDK 17 ou superior
- JRE 1.8.0 ou superior
- IDE Java (Intellij, Eclipse ou outros)
- Framework pra teste de API (Insomnia ou Postman)
- Lombok
- Git

<b>Para testar o projeto</b>
- Realize a instalação de todos os Requisitos
- Baixe o projeto direto do Github
- Abra este projeto em um Editor de Código para Java como o Intellij ou o Eclipse
- Configure o arquivo encontrado em main/java/resources chamado application.properties, altere o url, username e senha para de acordo com seu Banco de Dados MySQL

```
spring.datasource.url = jdbc:mysql://localhost:3307/seubanco
spring.datasource.username = seuusername
spring.datasource.password = suasenha
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto = update

```


## Como utilizar

### API
Execute o arquivo com o método main com o nome de DesafiovagasicrediApplication, e então utilize um framework para testes de API para acessar os seguintes URIs disponíveis. (http://localhost:8080 deverá ser alterado caso utilize outra porta padrão para executar o servidor em sua máquina);
<br>
- Para cadastrar um associado

|  *HEADER* | *FORMATO* |
| ------------- | ------------- |
| URI  | http://localhost:8080/api/associados/ |
| METODO  | POST |

| *BODY* | *FORMATO*  |
| ------------- | ------------- |
| cpf   | 999.999.999-99 |
| senha| 123456|
<br>

- Para criar uma pauta

|  *HEADER* | *FORMATO* |
| ------------- | ------------- |
| URI  | http://localhost:8080/api/pautas/ |
| METODO  | POST |

| *BODY* | *FORMATO*  |
| ------------- | ------------- |
| tema  | tema válido |
| criador  | cpf:  999.999.999-99 |
<br>

- Para iniciar a sessão da pauta (Só pode ser iniciada pelo associado que a criou)

|  *HEADER* | *FORMATO* |
| ------------- | ------------- |
| URI  | http://localhost:8080/api/pautas/{id}/iniciar |
| METODO  | PATCH |

| *BODY* | *FORMATO*  |
| ------------- | ------------- |
| criador  | cpf:  999.999.999-99 |
<br>

- Para votar em uma pauta (Você pode escolher apenas entre SIM e NAO)

|  *HEADER* | *FORMATO* |
| ------------- | ------------- |
| URI  | http://localhost:8080/api/pautas/{id}/votar |
| METODO  | POST |

| *BODY* | *FORMATO*  |
| ------------- | ------------- |
| voto | SIM | 
<br>

- Para retornar o resultado de uma votação (Só pode retornar resultado ao ter uma sessão finalizada)

|  *HEADER* | *FORMATO* |
| ------------- | ------------- |
| URI  | http://localhost:8080/api/pautas/{id}/resultado |
| METODO  | GET |
<br>

### TESTES

Os métodos de testes se encontram em <b>desafiovagasicredi/src/tests</b> contando atualmente com os testes para o método da classe <b>PautaServiceImpl</b>. É possível executar a classe de teste ou executar apenas 1 método.

