-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: smartcupon
-- Source Schemata: smartcupon2
-- Created: Wed Jan  3 20:55:28 2024
-- Workbench Version: 8.0.34
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema smartcupon
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `smartcupon2` ;
CREATE SCHEMA IF NOT EXISTS `smartcupon2` ;
SELECT * FROM smartcupon2.cliente;
-- ----------------------------------------------------------------------------
-- Table smartcupon.cliente
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcupon`.`cliente` (
  `idCliente` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidoM` VARCHAR(45) NULL DEFAULT NULL,
  `apellidoP` VARCHAR(45) NULL DEFAULT NULL,
  `telefono` VARCHAR(10) NULL DEFAULT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `calle` VARCHAR(45) NULL DEFAULT NULL,
  `numero` INT(11) NULL DEFAULT NULL,
  `nacimiento` DATE NULL DEFAULT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table smartcupon.empresa
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcupon2`.`empresa` (
  `idEmpresa` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `rfc` VARCHAR(45) UNIQUE NOT NULL,
  `representante` VARCHAR(45) NULL DEFAULT NULL,
  `estatus` VARCHAR(45) NOT NULL,
  `ciudad` VARCHAR(45) NULL DEFAULT NULL,
  `telefono` VARCHAR(45) NULL DEFAULT NULL,
  `pagina` VARCHAR(45) NULL DEFAULT NULL,
  `codigoP` VARCHAR(45) NULL DEFAULT NULL,
  `logo` LONGBLOB NULL DEFAULT NULL,
  `correo` VARCHAR(45) UNIQUE NOT NULL,
  `calle` VARCHAR(45) NULL DEFAULT NULL,
  `numero` INT(11) NULL DEFAULT NULL,
  `nombreComercial` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEmpresa`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;



-- ----------------------------------------------------------------------------
-- Table smartcupon.promocion
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcupon2`.`promocion` (
  `idPromocion` INT(11) NOT NULL AUTO_INCREMENT,
  `imagen` LONGBLOB NULL DEFAULT NULL,
  `codigo` VARCHAR(8) NOT NULL,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `descripcion` VARCHAR(255) NULL DEFAULT NULL,
  `restriccion` VARCHAR(45) NULL DEFAULT NULL,
  `categoria` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `valorTipo` INT(11) NOT NULL,
  `estatus` VARCHAR(45) NOT NULL,
  `fechaInicio` DATE NOT NULL,
  `fechaFin` DATE NOT NULL,
  `cuponesMax` INT(11) NOT NULL,
  `idEmpresa` INT(11) NOT NULL,
  PRIMARY KEY (`idPromocion`),
   FOREIGN KEY (`idEmpresa`) REFERENCES `empresa`(`idEmpresa`) ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table smartcupon.sucursal
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcupon2`.`sucursal` (
  `idSucursal` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `calle` VARCHAR(45) NULL DEFAULT NULL,
  `numero` INT(11) NULL DEFAULT NULL,
  `encargado` VARCHAR(45) NULL DEFAULT NULL,
  `colonia` VARCHAR(45) NULL DEFAULT NULL,
  `ciudad` VARCHAR(45) NULL DEFAULT NULL,
  `telefono` VARCHAR(45) NULL DEFAULT NULL,
  `codigoP` VARCHAR(45) NULL DEFAULT NULL,
  `longitud` VARCHAR(45) NULL DEFAULT NULL,
  `latitud` VARCHAR(45) NULL DEFAULT NULL,
  `idEmpresa` INT(11) NOT NULL,
  PRIMARY KEY (`idSucursal`),
   FOREIGN KEY (`idEmpresa`) REFERENCES `empresa`(`idEmpresa`) ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table smartcupon.promo_sucu
-- ----------------------------------------------------------------------------
CREATE TABLE `promo_sucu` (
  `idPromocion` INT UNSIGNED NOT NULL,
  `idSucursal` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idPromocion`, `idSucursal`));

-- ----------------------------------------------------------------------------
-- Table smartcupon.usuario
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcupon2`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `idEmpresa` INT(11) NOT NULL,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `rol` VARCHAR(45) NULL DEFAULT NULL,
  `username` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  `curp` VARCHAR(45) NULL DEFAULT NULL,
  `correo` VARCHAR(45) NULL DEFAULT NULL,
  `apellidoP` VARCHAR(45) NULL DEFAULT NULL,
  `apellidoM` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
   FOREIGN KEY (`idEmpresa`) REFERENCES `empresa`(`idEmpresa`) ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;

