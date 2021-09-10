# Spring Videolocadora

## Sumário
- Sobre
    - O que é projeto
    - Dependências
- Instalação
    - Download
    - Configuração
        - Banco de dados
        - Segurança
- Rodando o projeto

## Sobre

### O que é esse projeto

Esse é um projeto de uma videolocadora. É possível cadastrar clientes, filmes, categorias e controlar as locações realizadas e também
manipular esses dados utilizando a ferramenta de edição ou remoção.

### Dependências

Para realizar esse projeto, algumas dependências foram utilizadas, elas são:


- Java 11 ([https://www.oracle.com/java/technologies/javase-jdk11-downloads.html](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)) - Como linguagem de programação.
- MongoDB ([https://www.mongodb.com/pt-br](https://www.mongodb.com/pt-br)) - Como banco de dados
- Spring Framework ([https://spring.io/](https://spring.io/)) - Como framework para auxiliar na criação do projeto
    - Spring Data MongoDB
    - Spring Web
    - Spring Boot DevTools
    - Spring Security
- Lombok ([https://projectlombok.org/](https://projectlombok.org/)) - Para redução de alguns trechos de códigos
- Thymeleaf ([https://www.thymeleaf.org/](https://www.thymeleaf.org/)) - Como template engine para as páginas HTML
- Bulma ([https://bulma.io/](https://bulma.io/)) - Como framework CSS
- Bean Validator ([https://hibernate.org/validator/](https://hibernate.org/validator/)) - Para validar os campos

## Instalação

### Download

```sh
$ git clone https://github.com/GuilhermeTonello/spring-videolocadora.git
```

### Configuração

**Nota importante**: ***Cuidado ao realizar a configuração, certifique-se que saiba o que está fazendo!***

#### Configurando o banco de dados
1. Abra o arquivo **application.properties** localizado na pasta **src/main/resources**
2. Troque a URI do MongoDB pela sua, na propriedade *spring.data.mongodb.uri*
3. Salve e feche o arquivo

Caso você não saiba qual a URI do MongoDB, acesse o site: [https://docs.mongodb.com/manual/reference/connection-string/](https://docs.mongodb.com/manual/reference/connection-string/)

#### Configurando a segurança
1. Os arquivos relacionas a segurança estão na pasta **src/main/java/com/guilherme/springvideolocadora/security**
    - PasswordEncoderConfiguration.java: Configuração do Hash de senha (O projeto utiliza BCrypt por padrão)
    - UserDetailsImpl.java: Configurando um usuário do framework Spring
    - UserDetailsServiceImpl.java: Carregando um usuário por nome
    - WebSecurityConfig.java: Configurações gerais de segurança
2. O arquivo **UsuarioSeeder.java** na pasta **src/main/java/com/guilherme/springvideolocadora/seeder** contém os usuários
3. Para logar no sistema, utilize os seguintes usuários:
    - Administrador:
        - Login: admin
        - Senha: admin123
    - Funcionário:
        - Login: funcionario
        - Senha: func123
    - Estagiário:
        - Login: estagiario
        - Senha: estag123
4. Caso não queira gerar os usuários padrões, delete o arquivo **UsuarioSeeder.java**, mas cuidado, pois você terá que adicionar manualmente no banco de dados!
5. As roles(papéis/grupos) são:
    - ROLE_ADMIN
    - ROLE_FUNCIONARIO
    - ROLE_ESTAGIARIO

## Rodando o projeto

1. Após configurar o projeto, você poderá iniciá-lo com as seguintes instruções
2. Abra um terminal ou prompt de comando na pasta raíz do projeto
3. Digite o seguinte no terminal:
    - MacOS/Linux
		```sh
		$ ./mvnw spring-boot:run
		```
    - Windows
		```sh
		$ mvnw spring-boot:run
		```
4. Abra um navegador e digite localhost:8080.
5. Caso dê erro ao iniciar o projeto por causa da porta 8080, abra o arquivo **application.properties**
6. Adicione a seguinte linha: **server.port=PORTA**, por exemplo, **server.port=8081**
7. Digite novamente o comando no terminal ou prompt de comando para executar o projeto
8. Abra um navegador em localhost:NOVA PORTA, no exemplo dado, localhost:8081
9. Para parar o projeto, aperta **Ctrl + C** no terminal ou prompt de comando
