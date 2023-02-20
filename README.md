Olá, este projeto de trata de um Desafio da Sicredi para fazer um sistema de votação para os associados!

#Do que eu preciso para testar
- MySQL
- JDK 17 ou superior
- JRE 1.8.0 ou superior
- IDE Java (Intellij, Eclipse ou outros)
- Framework pra teste de API (Insomnia ou Postman)
- Lombok

#Como utilizar
Todas as páginas precisam de autenticação para utilizar com excessão do cadastro de associado e login.
acesse a http://localhost:8080/api/associado/ e coloque no corpo da requisição as seguinte informações
{
  "nome":"Nome",
  "cpf":"cpf",
  "senha":"senha",
  "confirmarSenha":"senha"
 } 

após isso realize o login em http://localhost:8080/api/associado/login e coloque no corpo da requisição
{
  "cpf":"cpf",
  "senha":"senha"
}

E utilize o acessToken recebido pelo login para utilizar os outros métodos que precisam de autorização
