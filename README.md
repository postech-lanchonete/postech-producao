# 🍔 Lanchonete do Bairro - Acompanhamento da Produção

##### versão 1.0.0-POC

<img src="https://img.shields.io/badge/Java-17-important.svg?logo=java" alt="shield referente a versao do java"><img src="https://img.shields.io/badge/Build Tool-Gradle-green.svg?logo=gradle" alt="shield referente ao uso de gradle">[<img src="https://img.shields.io/badge/dockerhub-images-blue.svg?logo=docker" alt="shield referente ao docker hub">](https://hub.docker.com/repository/docker/danielmaria/postech-producao/general) <img src="https://img.shields.io/badge/Orchestrated by-Kubernetes-green.svg?logo=kubernetes" alt="shield referente ao uso de kubernetes">
[<img src="https://img.shields.io/badge/dockerhub-images-blue.svg?logo=docker" alt="shield referente ao docker hub">](https://hub.docker.com/repository/docker/danielmaria/postech-producao/general)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-producao&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-producao)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-producao&metric=coverage)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-producao)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-producao&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-producao)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-producao&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-producao)


____

<p align="justify">
Este microserviço desempenha um papel central na gestão da produção de pedidos, fornecendo funcionalidades essenciais para a lanchonete. Sua principal responsabilidade é buscar os pedidos que estão em produção, 
permitindo uma visão clara do status de cada pedido em tempo real. Além disso, oferece a capacidade de adicionar novos pedidos à fila de produção e modificar os status existentes, garantindo uma gestão eficiente e ágil do fluxo de trabalho da lanchonete.
</p>

## Pipeline
<p align="justify">
    Quando um desenvolvedor faz um commit no GitHub, a plataforma identifica se a ação deve ser acionada. Se sim, a pipeline inicia com o CI, que executa testes unitários. Se os testes forem bem-sucedidos, a imagem Docker é construída e enviada para o Docker Hub. Após a conclusão bem-sucedida do CI, o CD entra em ação. Ele começa baixando a imagem Docker, remove o contêiner em execução na instância EC2 e substitui-o pela nova imagem. Esse fluxo garante que seu software seja testado automaticamente e implantado de forma eficiente, melhorando a qualidade e a entrega contínua de novas funcionalidades.
</p>

<p align="center">
  <img src="https://github.com/postech-lanchonete/postech-producao/assets/20681811/418dadbe-1c19-4489-8bf9-11d90a6d165e" />
</p>


## Arquitetura limpa
<p align="justify">
  A arquitetura limpa é um conceito de design de software que promove a separação de responsabilidades em camadas bem definidas. A arquitetura limpa visa manter a independência das camadas, facilitando a manutenção, teste e evolução do software. Ela foi utilizada neste projeto pois permite que este seja eveluido de forma mais simples futuramente.
</p>
A estrutura geral do projeto pode ser vista a seguir:

```sh
├── main
|  ├── java
|  |  └── br
|  |     └── com
|  |        └── postech.pagamento
|  |           ├── business <- 2. Application Business Rules
|  |           |  ├── exceptions
|  |           |  └── usecases
|  |           ├── core <- 1. Enterprise Business Rules
|  |           |  ├── entities
|  |           |  └── enums
|  |           ├── drivers <- 4. Frameworks & Drivers
|  |           |  └── web
|  |           └── adapters <- 3. Interface Adapters
|  |              ├── adapter
|  |              ├── controllers
|  |              ├── dto
|  |              ├── gateways
|  |              ├── handler
|  |              └── repositories
```
<details>
  <summary>Explicação das camadas</summary>
As camadas do projeto foram divididas seguindo a logica proposta pela arquitetura limpa, sendo 4 as prinipais assinaladas na árvore de estrutura acima.

1. **Core:** Esta camada contém regras de negócios de alto nível e princípios que são específicos para a organização ou domínio de negócios.
2. **Business**: Aqui, você encontra as regras de negócios da aplicação, que são específicas para a aplicação em si, independentemente do domínio de negócios. É onde a lógica de negócios central é implementada.
3. **Adapters**: Esta camada lida com a interação entre a aplicação e o mundo exterior. Inclui adaptadores para interfaces de usuário, bancos de dados, serviços externos e qualquer outra coisa que não seja parte da aplicação em si.
4. **Drivers**: É onde você integra frameworks, bibliotecas e drivers externos necessários para executar a aplicação. Isso inclui o banco de dados, frameworks web, ferramentas de comunicação e assim por diante. É a fronteira entre a aplicação e o ambiente externo.

<details>
  <summary>Explicação das camadas</summary>
As camadas do projeto foram divididas seguindo a logica proposta pela arquitetura limpa, sendo 4 as prinipais assinaladas na árvore de estrutura acima.

1. **enterpriserules:** Esta camada contém regras de negócios de alto nível e princípios que são específicos para a organização ou domínio de negócios.
2. **Application Business Rules**: Aqui, você encontra as regras de negócios da aplicação, que são específicas para a aplicação em si, independentemente do domínio de negócios. É onde a lógica de negócios central é implementada.
3. **Interface Adapters**: Esta camada lida com a interação entre a aplicação e o mundo exterior. Inclui adaptadores para interfaces de usuário, bancos de dados, serviços externos e qualquer outra coisa que não seja parte da aplicação em si.
4. **Frameworks & Drivers**: É onde você integra frameworks, bibliotecas e drivers externos necessários para executar a aplicação. Isso inclui o banco de dados, frameworks web, ferramentas de comunicação e assim por diante. É a fronteira entre a aplicação e o ambiente externo.
</details>

## OpenAPI

<p align="justify">
  O OpenAPI é uma especificação que define um padrão para descrever APIs RESTful de maneira padronizada, independente de linguagem e de plataforma. Com o OpenAPI é possível documentar de forma estruturada todos os *endpoints*, parâmetros, respostas e demais detalhes de uma API, facilitando a compreensão e a utilização por parte de desenvolvedores e consumidores. Já o Swagger é uma ferramenta que utiliza a especificação OpenAPI para gerar automaticamente a documentação interativa da API. Através do Swagger UI é possível acessar a documentação da API em um formato amigável, que permite explorar os <b>endpoints</b>, testar as requisições e visualizar as respostas esperadas. O uso do OpenAPI e do Swagger traz diversos benefícios, como a padronização da documentação, a facilidade de integração entre sistemas e a promoção de uma documentação atualizada e de qualidade. Para acessar a documentação gerada pelo Swagger, basta acessar a URL específica fornecida pelo projeto que é <code>/swagger-ui/index.html#/</code>
</p>

## Verificação de integridade (Health Check)

<p align="justify">
  O Health Check é um mecanismo que permite verificar o estado de saúde de um sistema ou serviço em tempo real. É utilizado para monitorar e validar se o sistema está funcionando corretamente, fornecendo informações sobre sua disponibilidade e desempenho.
</p>
<p align="justify">
  No contexto deste projeto, o Health Check foi implementado para garantir a integridade e a estabilidade do sistema, permitindo que os administradores ou ferramentas de monitoramento verifiquem a saúde da aplicação de forma automatizada. O projeto utiliza o Health Check para fornecer um endpoint específico, <code>/actuator/health</code>, que retorna informações sobre o estado do sistema, incluindo o status atual. Isso é útil para identificar problemas e tomar ações corretivas, garantindo a disponibilidade e a qualidade do serviço. Ao utilizar o Health Check, o projeto busca facilitar o monitoramento e o diagnóstico de problemas, contribuindo para uma melhor experiência do usuário e um ambiente mais confiável.
</p>

<details>
  <summary>Alguns endpoints úteis</summary>

```sh
/actuator/health: Informações de saúde da aplicação, incluindo o status do banco de dados.
```

```sh
/actuator/info: Informações gerais sobre a aplicação, incluindo informações sobre o banco de dados.
```

```sh
/actuator/metrics: Métricas da aplicação, como tempo de execução de consultas no banco de dados.
```

</details>


## Swagger

A documentação técnica da API pode ser acessada na URI [/swagger-ui/index.html](http://localhost:31001/swagger-ui/index.html), quando o sistema está rodando.

