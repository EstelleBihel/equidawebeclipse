-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : dim. 26 oct. 2025 à 20:44
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `equida`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorievente`
--

CREATE TABLE `categorievente` (
  `code` int(11) NOT NULL,
  `libelle` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `cheval`
--

CREATE TABLE `cheval` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `date_naissance` date NOT NULL,
  `sexe` varchar(10) NOT NULL,
  `sire` varchar(50) NOT NULL,
  `taille` decimal(4,2) NOT NULL,
  `poids` int(11) NOT NULL,
  `race_id` int(11) NOT NULL,
  `robe_id` int(11) NOT NULL,
  `proprietaire_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `cheval`
--

INSERT INTO `cheval` (`id`, `nom`, `date_naissance`, `sexe`, `sire`, `taille`, `poids`, `race_id`, `robe_id`, `proprietaire_id`) VALUES
(1, 'Eclipse', '2017-03-12', 'M', '204929040', 99.99, 681, 4, 0, 2),
(2, 'Aztec', '2019-07-04', 'M', '900452657', 99.99, 657, 4, 0, 3),
(3, 'orion', '2015-05-23', 'M', '187476192', 99.99, 544, 5, 0, NULL),
(4, 'Tempête de Feu', '2017-03-12', 'F', '836023020', 99.99, 649, 1, 0, NULL),
(5, 'Éclair Noir', '2019-07-04', 'M', '817686549', 99.99, 611, 2, 0, NULL),
(6, 'Vent du Nord', '2015-05-23', 'M', '580363717', 99.99, 407, 3, 0, NULL),
(7, 'Comète', '2018-01-01', 'F', '348758963', 99.99, 406, 4, 0, NULL),
(8, 'Silver Snow', '2020-11-17', 'M', '802703692', 99.99, 407, 5, 0, NULL),
(9, 'Caramel', '2016-06-30', 'F', '167241601', 99.99, 421, 6, 0, NULL),
(10, 'Storm', '2021-10-10', 'M', '128096955', 99.99, 482, 1, 0, NULL),
(11, 'Mustang', '2014-08-03', 'M', '938760003', 99.99, 449, 2, 0, NULL),
(12, 'Rising Sun', '2019-04-22', 'F', '609509176', 99.99, 697, 3, 0, NULL),
(13, 'Phantom', '2016-12-05', 'F', '131265898', 99.99, 537, 4, 0, NULL),
(14, 'Pompom', '2025-07-13', 'F', '527801992', 99.99, 495, 2, 0, NULL),
(15, 'Fleur du désert', '2023-06-30', 'M', '345212294', 99.99, 465, 6, 0, NULL),
(16, 'Ulysse', '0000-00-00', 'M', '123ABC', 2.00, 500, 1, 1, NULL),
(17, 'Ulysse', '2018-05-12', 'M', '123ABC', 2.00, 500, 1, 1, NULL),
(18, 'Tornado', '0000-00-00', '', '', 0.00, 0, 1, 1, 1),
(19, 'Tornado', '0000-00-00', '', '', 0.00, 0, 1, 1, 1),
(20, 'Eclair', '0000-00-00', '', '', 0.00, 0, 1, 1, 2),
(21, 'Tornado', '0000-00-00', '', '', 0.00, 0, 1, 1, 1),
(22, 'Eclair', '0000-00-00', '', '', 0.00, 0, 1, 1, 2),
(23, 'Tornado', '0000-00-00', '', '', 0.00, 0, 1, 1, 1),
(24, 'Eclair', '0000-00-00', '', '', 0.00, 0, 1, 1, 2),
(25, 'Tornado', '0000-00-00', '', '', 0.00, 0, 1, 1, 1),
(26, 'Eclair', '0000-00-00', '', '', 0.00, 0, 1, 1, 2),
(27, 'Tornado', '2018-05-10', 'M', 'Sire A', 1.62, 480, 1, 1, 1),
(28, 'Eclair', '2019-03-22', 'M', 'Sire B', 1.59, 470, 1, 1, 3),
(29, 'Diva', '2020-06-15', 'F', 'Sire C', 1.57, 450, 1, 1, 10);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `numeroRue` int(11) DEFAULT NULL,
  `rue` varchar(255) DEFAULT NULL,
  `ville` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `numeroRue`, `rue`, `ville`) VALUES
(1, 'Dupont', 'Jean', 12, 'rue des Fleurs', 'Caen'),
(2, 'Dupont', 'Jean', 12, 'rue des Fleurs', 'Deauville'),
(3, 'Martin', 'Claire', 8, 'avenue des Écuries', 'Deauville'),
(4, 'Dupont', 'Jean', 12, 'rue des Fleurs', 'Deauville'),
(5, 'Martin', 'Claire', 8, 'avenue des Écuries', 'Deauville'),
(6, 'Dupont', 'Jean', 12, 'rue des Fleurs', 'Deauville'),
(7, 'Martin', 'Claire', 8, 'avenue des Écuries', 'Deauville'),
(8, 'Dupont', 'Jean', 12, 'rue des Fleurs', 'Deauville'),
(9, 'Martin', 'Claire', 8, 'avenue des Écuries', 'Chantilly'),
(10, 'Durand', 'Paul', 5, 'boulevard des Courses', 'La Teste'),
(11, 'Legrand', 'Sophie', 22, 'rue des Cavaliers', 'Saint-Cloud'),
(12, 'Bernard', 'Luc', 45, 'rue des Boxes', 'Cagnes-sur-Mer');

-- --------------------------------------------------------

--
-- Structure de la table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `nom` varchar(150) NOT NULL,
  `dateCourse` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `encheres`
--

CREATE TABLE `encheres` (
  `numero` int(11) NOT NULL,
  `montant` int(11) NOT NULL,
  `lot_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

CREATE TABLE `lieu` (
  `id` int(11) NOT NULL,
  `ville` varchar(255) DEFAULT NULL,
  `nb_de_boxes` int(11) DEFAULT NULL,
  `commentaires` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `lieu`
--

INSERT INTO `lieu` (`id`, `ville`, `nb_de_boxes`, `commentaires`) VALUES
(1, 'Deauville', 120, 'Grand hippodrome, très fréquenté.'),
(2, 'Chantilly', 80, 'Proche de Paris, installations modernes.'),
(3, 'La Teste', 60, 'Ambiance conviviale, proche de l\'océan.'),
(4, 'Saint-Cloud', 90, 'Hippodrome historique, facile d\'accès.'),
(5, 'Cagnes-sur-Mer', 70, 'Climat agréable, infrastructure récente.');

-- --------------------------------------------------------

--
-- Structure de la table `lot`
--

CREATE TABLE `lot` (
  `id` int(11) NOT NULL,
  `prixDepart` int(11) NOT NULL,
  `vente_id` int(11) NOT NULL,
  `cheval_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `lot`
--

INSERT INTO `lot` (`id`, `prixDepart`, `vente_id`, `cheval_id`) VALUES
(1, 5000, 1, 1),
(2, 5000, 1, 1),
(3, 6500, 1, 2),
(4, 5000, 1, 1),
(5, 6500, 1, 2),
(6, 5000, 1, 1),
(7, 6500, 1, 2),
(8, 5000, 1, 18),
(9, 6500, 1, 20),
(10, 4200, 1, 29),
(11, 5200, 1, 27),
(12, 6400, 1, 28);

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

CREATE TABLE `pays` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `codePostal` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `race`
--

CREATE TABLE `race` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `race`
--

INSERT INTO `race` (`id`, `nom`) VALUES
(1, 'Pur-sang'),
(2, 'Quarter Horse'),
(3, 'Frison'),
(4, 'Andalou'),
(5, 'Lipizzan'),
(6, 'Mustang');

-- --------------------------------------------------------

--
-- Structure de la table `robe`
--

CREATE TABLE `robe` (
  `id` int(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `description` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `robe`
--

INSERT INTO `robe` (`id`, `nom`, `description`) VALUES
(0, 'Alezan', NULL),
(1, 'Bai', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `vente`
--

CREATE TABLE `vente` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `lieu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `vente`
--

INSERT INTO `vente` (`id`, `nom`, `date`, `lieu_id`) VALUES
(1, 'Vente mixte de février', '2024-02-15', 1),
(2, 'Vente d\'été', '2024-07-10', 2),
(3, 'Vente d\'automne', '2024-10-05', 3),
(4, 'Vente d\'élevage', '2024-12-01', 4),
(5, 'Vente de yearlings', '2024-08-20', 1),
(6, 'Vente de chevaux à l\'entraînement', '2024-03-22', 5);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorievente`
--
ALTER TABLE `categorievente`
  ADD PRIMARY KEY (`code`);

--
-- Index pour la table `cheval`
--
ALTER TABLE `cheval`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_race` (`race_id`),
  ADD KEY `fk_robe` (`robe_id`),
  ADD KEY `fk_cheval_client` (`proprietaire_id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `encheres`
--
ALTER TABLE `encheres`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `fk_encheres_lot` (`lot_id`),
  ADD KEY `fk_encheres_client` (`client_id`);

--
-- Index pour la table `lieu`
--
ALTER TABLE `lieu`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `lot`
--
ALTER TABLE `lot`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_lot_vente` (`vente_id`),
  ADD KEY `fk_lot_cheval` (`cheval_id`);

--
-- Index pour la table `pays`
--
ALTER TABLE `pays`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `race`
--
ALTER TABLE `race`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `robe`
--
ALTER TABLE `robe`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vente`
--
ALTER TABLE `vente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `lieu_id` (`lieu_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `categorievente`
--
ALTER TABLE `categorievente`
  MODIFY `code` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `cheval`
--
ALTER TABLE `cheval`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `encheres`
--
ALTER TABLE `encheres`
  MODIFY `numero` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `lieu`
--
ALTER TABLE `lieu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `lot`
--
ALTER TABLE `lot`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `pays`
--
ALTER TABLE `pays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `race`
--
ALTER TABLE `race`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `vente`
--
ALTER TABLE `vente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `cheval`
--
ALTER TABLE `cheval`
  ADD CONSTRAINT `fk_cheval_client` FOREIGN KEY (`proprietaire_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `fk_race` FOREIGN KEY (`race_id`) REFERENCES `race` (`id`),
  ADD CONSTRAINT `fk_robe` FOREIGN KEY (`robe_id`) REFERENCES `robe` (`id`);

--
-- Contraintes pour la table `encheres`
--
ALTER TABLE `encheres`
  ADD CONSTRAINT `fk_encheres_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_encheres_lot` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `lot`
--
ALTER TABLE `lot`
  ADD CONSTRAINT `fk_lot_cheval` FOREIGN KEY (`cheval_id`) REFERENCES `cheval` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_lot_vente` FOREIGN KEY (`vente_id`) REFERENCES `vente` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `vente`
--
ALTER TABLE `vente`
  ADD CONSTRAINT `vente_ibfk_1` FOREIGN KEY (`lieu_id`) REFERENCES `lieu` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
