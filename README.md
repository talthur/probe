# Desafio Java

## Pré-requisitos

- `java.17.0.2-zulu` de [SDKMAN](https://sdkman.io/usage);
- `Maven 3.6.3`;
- `Spring 2.6.2`

## Links úteis para monitoramento e teste

- API local:
  - [Health - http://localhost:8080/health](http://localhost:8080/health/);
  - [Api-docs - http://localhost:8080/api-docs](http://localhost:8080/api-docs);
  - [Swagger - http://localhost:8080/](http://localhost:8080/);

  
## Como utilizar
1. Crie um planeta fazendo um post em [/v1/planets](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/Planet%20Controller/createPlanet)
2. Com o ID gerado no passo 1, crie uma probe fazendo um post em [/v1/planets/probes](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/Probe%20Controller/createProbe)
3. Faça um put para movimentá-la em [/v1/planets/probes](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/Probe%20Controller/createProbe)
4. Caso queira conferir a posição da probe, faça um get em [/v1/planets/{planetId}/{probeName}](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/Probe%20Controller/getProbe)
5. Caso queira listar os planetas e seus conteúdos, faça um get em [/v1/planets](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/Planet%20Controller/getPlanets)

As instruções seguem a seguinte orientação, utilizando números positivos:

![Alt text](instruction.png?raw=true "Instruction")
