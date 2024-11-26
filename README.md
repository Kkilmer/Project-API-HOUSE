# Project-API-HOUSE

Inicialmente, estamos trabalhando em um projeto Aluno Online, com toda arquitetura de acessos e informações básicas de Aluno, Professor e Disciplina.

ARQUITERUTA DO PROJETO

Este projeto segue uma arquitetura MVC (Model-View-Controller) adaptada para o uso de serviços (Service Layer). Ele é estruturado em pacotes conforme descrito abaixo, promovendo a separação de responsabilidades e uma melhor organização do código. Além disso, utilizamos DTOs (Data Transfer Objects) para transporte de dados entre camadas e Swagger para documentação da API.

/br.com.alunoonline.api
- Pacotes do Projeto: Componentes personalizados desenvolvidos para atender aos requisitos da aplicação.

##
1. /model (Modelo):
- Este pacote contém as entidades do sistema, que representam as tabelas do banco de dados. Cada classe está mapeada com o uso de JPA (Java Persistence API).

I. Características Comuns:
- Anotações:
@NoArgsConstructor e @AllArgsConstructor: Geram construtores padrão (sem argumentos) e completo (com todos os atributos).
@Data: Cria automaticamente getters, setters e equals.
@Entity: Define as classes como entidades JPA.
@Id e @GeneratedValue: Identificam o atributo id como chave primária, gerada automaticamente.

II. Atributos Compartilhados:
- Todas as classes possuem o atributo id como chave primária.

III. Diferenciais de Cada Classe:

- Aluno
Atributos específicos:
nome, email, cpf (informações pessoais do aluno).

- Disciplina
Atributos específicos:
nome (identificação da disciplina).
professor (ManyToOne): Associação com a entidade Professor.

- MatriculaAluno
Atributos específicos:
Relações ManyToOne:
aluno (associação com a entidade Aluno).
disciplina (associação com a entidade Disciplina).
nota1, nota2 (notas associadas à matrícula).
status (Enum): Representa o estado da matrícula (APROVADO, MATRICULADO...).

- Professor
Atributos específicos:
nome, email, cpf (informações pessoais do professor).

##
2. /repository (Dados):
- Este pacote contém interfaces que interagem diretamente com o banco de dados, implementadas automaticamente pelo Spring Data JPA.

/AlunoRepository.java: 
- Responsável por operações de CRUD na tabela Aluno.

/DisciplinaRepository.java: 
- Gerencia as operações relacionadas às disciplinas.

/MatriculaAlunoRepository.java: 
- Gerencia os dados das matrículas, permitindo buscar informações específicas como matrículas por aluno.

/ProfessorRepository.java: 
- Controla as operações relacionadas aos professores.
##
3. /service (Serviço):
- O pacote de serviço contém a lógica/regra de negócios do sistema, separando as responsabilidades de orquestração e persistência de dados.

/AlunoService.java: 
- Contém os métodos para gerenciar alunos, como cadastro, atualização e emissão de histórico acadêmico.

/DisciplinaServive.java: 
- Gerencia a lógica de criação e atualização de disciplinas, além de vinculá-las a professores.

/MatriculaAlunoService.java: 
- Implementa funcionalidades como matrícula de alunos, atualização de notas e cálculo de médias.

/ProfessorService.java: 
- Lida com operações relacionadas aos professores, como cadastro e gerenciamento de disciplinas ministradas.
##
4. /controller (Orquestrador):
- Os controladores recebem as solicitações HTTP, chamam os serviços e retornam as respostas ao cliente. Aqui também configuramos a integração com o Swagger.

4.1 Características Comuns:

I. Criação (POST):
- Todos os controladores possuem um endpoint para criar um recurso (aluno, disciplina, matrícula ou professor).
- Entrada: Objeto correspondente enviado no corpo da requisição.
- Resposta: Status 201 (Criado).
  
II. Listagem Geral (GET):
- Endpoints para listar todos os recursos de um tipo.
- Resposta: Lista de objetos correspondentes.

III. Busca por ID (GET /{id}):
- Disponível em todos os controladores (exceto MatriculaAlunoController).
- Entrada: ID do recurso como parâmetro de URL.
- Resposta: Objeto correspondente.

IV. Atualização (PUT /{id}):
- Atualiza os dados completos de um recurso pelo ID.
- Entrada: ID como parâmetro de URL e objeto no corpo da requisição.
- Resposta: Status 204 (Sem Conteúdo).

V. Exclusão (DELETE /{id}):
- Remove um recurso pelo ID.
- Entrada: ID como parâmetro de URL.
- Resposta: Status 204 (Sem Conteúdo).

4.2 Características Específicas:

I. AlunoController
Endpoints Comuns: Criação, listagem, busca por ID, atualização e exclusão.

II. DisciplinaController
Diferencial:
- GET /disciplinas/professor/{professorId}: Lista disciplinas associadas a um professor.

III. MatriculaAlunoController
Diferenciais:
- PATCH /matriculas-alunos/trancar/{id}: Tranca uma matrícula existente.
- PATCH /matriculas-alunos/atualiza-notas/{id}: Atualiza notas de uma matrícula.
- GET /matriculas-alunos/emitir-historico/{alunoId}: Emite o histórico acadêmico de um aluno.
  
IV. ProfessorController
Endpoints Comuns: Criação, listagem, busca por ID, atualização e exclusão.

Acesso à Documentação: A interface do Swagger estará disponível em: http://localhost:8080/swagger-ui/

# Tecnologias e Ferramentas:
- Spring Boot: Framework principal para desenvolvimento da aplicação.
- Spring Data JPA: Para mapeamento objeto-relacional e operações no banco de dados.
- Swagger: Documentação da API com a interface interativa gerada automaticamente.
- DTOs (Data Transfer Objects): Para transporte de dados entre as camadas, reduzindo a exposição direta das entidades do banco.
- PostgreSQL: Banco de dados utilizado no projeto.

# Bibliotecas Utilizadas:

- Lombok: Para evitar código boilerplate.
- Spring Boot: Para configuração simplificada do projeto.
- Spring Data JPA: Para mapeamento objeto-relacional (ORM).
- Spring Web: Para manipulação de requisições e respostas HTTP.

# 
