# Rede Social em JavaFX

Esta é uma aplicação JavaFX simples que demonstra uma rede social, com sistema de login e registro usando um banco de dados MySQL. Ela fornece funcionalidades básicas como registro de usuário, login e adicionar amigos e enviar mensagens.

## Funcionalidades

- Registro de usuário com hash de senha seguro
- Login de usuário com verificação de senha
- Interface gráfica simples em JavaFX para registro, login, envio de solicitação de amizade e envio de mensagens.00

## Bibliotecas Utilizadas

- JavaFX: Biblioteca Java para construção de aplicações de GUI interativas.
- MySQL Connector/J: Driver JDBC para conectar aplicativos Java a bancos de dados MySQL.

## Pré-requisitos

- JDK (Java Development Kit): Versão utilizada foi o jdk 20. 
- MySQL: Certifique-se de ter um banco de dados MySQL configurado.

## Como Usar

1. Abra o projeto em seu IDE Java favorito (IntelliJ IDEA, Eclipse, etc.). A IDE utilizada foi o IntelliJ IDEA. 

2. Configure seu banco de dados MySQL:
   - Crie um novo banco de dados.
   - Atualize os detalhes de conexão do banco de dados na classe `Database.java`.

4. Execute a aplicação:
   - Localize o arquivo `HelloApplication.java`.
   - Execute o método `main`.

5. Utilize a aplicação:
   - Registre um novo usuário.
   - Faça login com o usuário registrado.
   - Explore o painel simples.

## Configuração do Banco de Dados

Atualize os detalhes de conexão do banco de dados na classe `Database.java` para corresponder à sua configuração do MySQL:

java
private static final String URL = "jdbc:mysql://localhost:3306/seubanco";
private static final String USER = "seuusuario";
private static final String PASSWORD = "suasenha";

## Banco de dados utilizado:

-- Estrutura para tabela `amigos`

CREATE TABLE IF NOT EXISTS `amigos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `idUsuarioAmigo` int NOT NULL,
  `nome_amigo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `solicitacao` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estrutura para tabela `mensagem`

CREATE TABLE IF NOT EXISTS `mensagem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `idUsuarioAmigo` int NOT NULL,
  `mensagem` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estrutura para tabela `solicitacao`

CREATE TABLE IF NOT EXISTS `solicitacao` (
  `IdSolicitacao` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `IdAmigo` int NOT NULL,
  `nome_remetente` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `nome_destinatario` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`IdSolicitacao`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estrutura para tabela `usuario`

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `dtaNascimento` date DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
