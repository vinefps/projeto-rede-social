-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 24/11/2023 às 01:14
-- Versão do servidor: 8.2.0
-- Versão do PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `java`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `amigos`
--

DROP TABLE IF EXISTS `amigos`;
CREATE TABLE IF NOT EXISTS `amigos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `idUsuarioAmigo` int NOT NULL,
  `nome_amigo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `solicitacao` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `amigos`
--

INSERT INTO `amigos` (`id`, `idUsuario`, `idUsuarioAmigo`, `nome_amigo`, `solicitacao`) VALUES
(1, 2, 1, 'Guilherme', 1),
(2, 1, 2, 'Misael', 1),
(3, 1, 5, 'Michelle', 6),
(4, 5, 1, 'Guilherme', 6),
(27, 2, 1, 'Guilherme', 4),
(28, 1, 2, 'Misael', 4),
(33, 2, 4, 'Vinicius', 17),
(34, 4, 2, 'Misael', 17),
(35, 2, 4, 'Vinicius', 18),
(36, 4, 2, 'Misael', 18),
(37, 5, 4, 'Vinicius', 20),
(38, 4, 5, 'Michelle', 20),
(39, 2, 14, 'Leonardo', 22),
(40, 14, 2, 'Misael', 22);

-- --------------------------------------------------------

--
-- Estrutura para tabela `mensagem`
--

DROP TABLE IF EXISTS `mensagem`;
CREATE TABLE IF NOT EXISTS `mensagem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `idUsuarioAmigo` int NOT NULL,
  `mensagem` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `mensagem`
--

INSERT INTO `mensagem` (`id`, `idUsuario`, `idUsuarioAmigo`, `mensagem`) VALUES
(1, 2, 1, 'Oi guilherme, tudo bem?'),
(2, 1, 2, 'Oi misael, estou bem e vc?'),
(3, 2, 1, 'Vamos jogar League of legends hoje a noite?'),
(4, 1, 2, 'Claro, vamos sim!'),
(5, 2, 1, 'Bom dia, Guilherme!'),
(6, 2, 1, 'Tudo bem, amigo?'),
(7, 2, 1, 'Quer jogar futebol hoje?'),
(8, 1, 5, 'oiii boa noite!'),
(9, 2, 14, 'Oi, Leonardo'),
(10, 2, 14, 'Tudo bem?'),
(11, 14, 2, 'Beleza');

-- --------------------------------------------------------

--
-- Estrutura para tabela `solicitacao`
--

DROP TABLE IF EXISTS `solicitacao`;
CREATE TABLE IF NOT EXISTS `solicitacao` (
  `IdSolicitacao` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `IdAmigo` int NOT NULL,
  `nome_remetente` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `nome_destinatario` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`IdSolicitacao`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `solicitacao`
--

INSERT INTO `solicitacao` (`IdSolicitacao`, `idUsuario`, `IdAmigo`, `nome_remetente`, `status`, `nome_destinatario`) VALUES
(3, 3, 1, 'Guilherme', 1, 'Arthur'),
(5, 3, 4, 'Vinicius', 1, 'Arthur'),
(8, 1, 4, 'Vinicius', 1, 'Guilherme'),
(9, 1, 4, 'Vinicius', 1, 'Guilherme'),
(10, 3, 4, 'Vinicius', 1, 'Arthur'),
(11, 8, 4, 'Vinicius', 1, 'pedro'),
(12, 3, 2, 'Misael', 1, 'Arthur'),
(14, 3, 2, 'Misael', 1, 'Arthur'),
(19, 1, 4, 'Vinicius', 1, 'Guilherme'),
(21, 1, 14, 'Leonardo', 1, 'Guilherme'),
(23, 3, 14, 'Leonardo', 1, 'Arthur'),
(24, 5, 14, 'Leonardo', 1, 'Michelle'),
(25, 6, 14, 'Leonardo', 1, 'Joao');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `dtaNascimento` date DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`, `dtaNascimento`, `telefone`) VALUES
(1, 'Guilherme', 'Guilherme@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2003-05-12', '3199999999'),
(2, 'Misael', 'Misael@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2003-04-02', '31989898989'),
(3, 'Arthur', 'Arthur@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2003-04-16', '31989898989'),
(4, 'Vinicius', 'Vinicius@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '1985-06-02', '31989898989'),
(5, 'Michelle', 'Michelle@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-16', '31987654321'),
(6, 'Joao', 'joao@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-02', '9111111111'),
(8, 'pedro', 'pedro@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-16', '3199999999'),
(9, 'Maria', 'maria@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-01', '31999999999'),
(10, 'João', 'joao@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-02', '31999999999'),
(11, 'alice', 'alice@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-10-31', '31999999999'),
(12, 'luiz', 'luiz@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-10-31', '31999999999'),
(13, 'carol', 'carol@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-01', '31999999999'),
(14, 'Leonardo', 'leonardo@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-01', '31999999999'),
(15, 'Felipe', 'felipe@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2023-11-01', '31999999999');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
