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
Os seguintes métodos podem ser acessados

- Para criar uma pauta

http://localhost:8080/api/pauta/salvar

@RequestBody
{
 "tema":"tema"
}

- Para iniciar a sessão da pauta (Só pode ser iniciada pelo associado que a criou)

http://localhost:8080/api/pauta/{id}/iniciar

@PathVariable - {id} (Você utilizará o ID da pauta)

@RequestBody(required = false)
{
  "duracao" : "600000" -> Representa em milisegundos, deve ser um integer e caso esteja vazio a duração padrão da sessão será de 60000 milisegundos (1 minuto)
}

- Para votar em uma pauta

http://localhost:8080/api/pauta/{id}/votar

@PathVariable - {id} (Você utilizará o ID da pauta)

@RequestBody
{
  "voto":"SIM" (Pode escolher entre SIM e NAO, caso escolha outra opção irá retornar Unthorized)
}

- Para retornar o resultado da votação de uma pauta retornar em uma String (Caso a sessão não esteja finalizada ele não irá retornar)

http://localhost:8080/api/pauta/{id}/resultado

@PathVariable - {id} (Você utilizará o ID da pauta)


Também existe uma classe de Testes para o Service da classe PautaService, na qual pode ser executado para testar os diferentes cenários!

