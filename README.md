

# O que é?
Jobs é um Gerenciador de Oportunidades de Empregos.


# Como foi feito?
Jobs possui um API Restful, elaborada utilizando Java 8 e Spring Boot + Spring Data, além de um BD MySQL.
Além da API, também há um outro micro serviço, que utiliza as mesmas tecnologia, mas se faz uso de Bibliotecas de Scheduling, para de tempos em tempos, carregar novas vagas de emprego via arquivo de texto.

### O que eu preciso para executar localmente em minha estação de trabalho?
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) Java JDK 
* [Spring](http://spring.io/) - Framework utilizado (Spring Boot + Spring Data)
* [Maven](https://maven.apache.org/) - Gerenciador de Dependências
* [Postman](https://www.getpostman.com/) - Um client de chamadas Restful, por exemplo, o Postman
* [MySQL](https://www.mysql.com/downloads/) - Gerenciador de Banco de Dados MySQL

E um PC compatível para utilizar tais ferramentas.

## Instruções para uso local:

1. Com o MySQL instalado, aplique o script que está em:
https://github.com/marcoscesarmelo/jobs/jobs.sql
em um banco de dados chamado jobs. Crie o mesmo com login root e senha 1234, ou altere estas propriedades no arquivo application.properties da aplicação.
Veja o Script:
```SQL
drop database jobs;
create database jobs;
use jobs;

create table job (
	partner_id int not null primary key,
	title varchar(100) not null,
	category_id int not null,
	expires_at datetime not null,
	status int not null
) ENGINE=INNODB;

create table user (
	id int not null primary key auto_increment,
	username varchar(10) not null,
	password varchar(10) not null
) ENGINE=INNODB;

insert into user values(default, 'root', 'root');

commit;
```
Ao término desta etapa, o banco de dados já estará OK para uso!.

2. Utilize uma IDE de sua preferência, ou mesmo o maven para compilar os projeto 2 projetos
- jobs 
- jobs-batch

Para cada um dos dois projetos, navegue até a pasta raíz do mesmo e execute comando:

```DOS
java -jar jobs-0.0.1-SNAPSHOT.jar 
```
```DOS
java -jar jobs-batch-0.0.1-SNAPSHOT.jar
```
3. Feito isto, os serviços estarão rodando. Agora basta acessar ao Postman e criar as chamadas para a API

# O que faz o Jobs Batch?
O Poll Batch é um Micro Serviço isolado que fica verificando de tempos em tempos (inicialmente o autor deixou 5 segundos), se alguma nova vaga apareceu. Uma vez aparecendo, ela será carregada para uso da aplicação.

## Contate o Autor:
[Marcos Cesar de Oliveira Melo](https://www.linkedin.com/in/marcoscesarmelo/)

## "Simplicidade: a arte de maximizar a quantidade de trabalho que não precisou ser feito.” (Agile Manifest)
