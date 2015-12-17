# example-integration-spring-boot
Repositório para apresentar a integração do spring boot

### Sobre o projeto ###
Aplicação Java Web. Além de código fonte e configurações, segue também a documentação descrevendo como o projeto foi desenvolvido, e o que foi adotado para implementar os exercícios solicitados.

Essa aplicação foi desenvolvida seguindo os modelos de arquitetura MVC e REST. Acrescentei também na solução um banco de dados e ambiente de execução embutidos.

### Tecnologias Utilizadas ###

* Java versão 8.
* Google Guava: Utilizada para definir pre-condições para as validações e geração de Equals / Hashcode.
* JPA / Hibernate: mapeamento de entidades persistentes em pojos de domínio.
* Bean Validations: framework para definição de regras de validação em entidades JPA via anotações.
* Logback: geração de logs.
* Spring Data JPA: Tecnologia utilizada gerar parte do código relacionado a camada de persistência. Na aplicação foi escrito os contratos de persistência, que realizam a criação dos comandos de manipulação (CRUD), consultas simples e complexas.
* Spring Web MVC: framework web usado como solução MVC para a definição de componentes seguindo o modelo de arquitetura REST
* Jackson: API para converter os dados Java em Json e vice-versa.
* Thymeleaf: engine para geração de páginas Web baseadas na definição de templates e fragmentos, Utilizo para fazer a  integraçao ao Spring Web MVC. Uso essa tecnologia para criar páginas HTML da aplicação.
* JQuery: acesso a camada REST via Ajax, responsável pela manipulação dos dados na estrutura HTML. Esse acesso Ajax é feito de forma stateless, 
* Bootstrap: solução CSS para facilitar a construção do layout das páginas HTML.

### Considerações ###

* As labels das páginas foram definidas no arquivo src/main/resources/messages_pt_BR.properties.
* A integração das páginas com os dados ocorre de forma assíncrona, sempre fazendo acesso aos serviços REST disponibilizados.

### Camadas e pacotes ###

* br.com.dca.boot: Pacote com as configurações necessárias para o start da aplicação.
* br.com.dca.domain: Pacote contendo as entidades persistentes, mapeadas com anotações JPA.
* br.com.dca.exception: Pacote contendo a exceção customizada.
* br.com.dca.repository: Pacote contendo as interfaces de persistência.
* br.com.dca.service: Pacote contendo os componentes de negócio, que são responsáveis por orquestrar os componentes de acesso a dados, transação com banco de dados e eventuais validações.
* br.com.dca.controller: Pacote contendo os componentes Controller e serviços REST.
* br.com.dca.stream: Pacote contendo a interface e o componente de validação de stream de caracteres
* br.com.dca.util:  Pacote contendo as classes utilitárias para formatação de cep e de leitura de mensagens de erro 
* src/main/resources/templates: Definição do layout principal e das páginas do projeto.
* src/main/resources/static: Arquivos css, js, fonts e ico.
* br.com.dca.controller.tests: Pacote contendo as classes responsáveis pelos testes dos controllers
* br.com.dca.stream.test: Pacote contendo a classe responsável pelo teste do validador de stream de caracteres

### Tecnologias Adicionais ###

* Banco de dados: HSQLDB embutido na aplicação. O banco é criado durante o startup da aplicação. Disponibilizo o arquivo src/main/resources/data.sql onde temos o script para inserção de dados nas tabelas do banco. Esses dados inseridos são utilizados nos componentes de testes (mock) e ficam disponíveis para as páginas web. No fim da execução o banco é destruído.
* Testes: os testes são definidos como Use Case do JUnit. Os testes dos serviços REST contam com: Spring Web MVC para mock da infra-estrutura web; JsonPath e hamcrest para acesso e assertions no conteúdo Json. Os testes foram disponibilizados na estrutura src/test/java.
* Spring Boot: tecnologia utilizada para criar um ambiente embutido de execução, utilizei essa tecnologia para simplificar o uso do Spring e para controlar o escopo do banco. No arquivo src/main/resources/application.properties constam algumas propriedades do Spring Boot para o projeto.
* Tomcat embutido: disponibilizado pelo Spring Boot.
* Maven: gestão de ciclo de vida e build do projeto.

No diretório raiz do projeto disponibilizo os arquivos: 
* Questao1.txt
* Questao2.txt
* Questao3.txt
* Questao4.txt

### Pré-requisitos ###

* JDK - versão 1.8 do Java;
* Qualquer IDE Java com suporte ao Maven;
* Maven - para build e dependências.

Após baixar os fontes, para executar a aplicação execute o comando maven:

$ mvn clean package spring-boot:run

Para visualizar a aplicação abra o browser de sua preferência e digite:

http://localhost:8080/

