create database smartcupon;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellidoM` varchar(45) DEFAULT NULL,
  `apellidoP` varchar(45) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `correo` varchar(45) NOT NULL,
  `calle` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  `nacimiento` date DEFAULT NULL,
  `contrasena` varchar(45) NOT NULL,
  PRIMARY KEY (`idCliente`)
);

INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Juan', 'Perez', 'Gomez', '1234567890', 'juan.perez@gmail.com', 'Calle 1', '10', '1980-01-01', 'password1');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Maria', 'Lopez', 'Martinez', '0987654321', 'maria.lopez@gmail.com', 'Calle 2', '20', '1981-02-02', 'password2');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Carlos', 'Hernandez', 'Rodriguez', '1122334455', 'carlos.hernandez@gmail.com', 'Calle 3', '30', '1982-03-03', 'password3');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Ana', 'Gonzalez', 'Ramirez', '2233445566', 'ana.gonzalez@gmail.com', 'Calle 4', '40', '1983-04-04', 'password4');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Pedro', 'Torres', 'Sanchez', '3344556677', 'pedro.torres@gmail.com', 'Calle 5', '50', '1984-05-05', 'password5');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Laura', 'Vargas', 'Castillo', '4455667788', 'laura.vargas@gmail.com', 'Calle 6', '60', '1985-06-06', 'password6');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Jorge', 'Rivera', 'Guerrero', '5566778899', 'jorge.rivera@gmail.com', 'Calle 7', '70', '1986-07-07', 'password7');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Sofia', 'Mendoza', 'Aguilar', '6677889900', 'sofia.mendoza@gmail.com', 'Calle 8', '80', '1987-08-08', 'password8');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Ricardo', 'Romero', 'Peña', '7788990011', 'ricardo.romero@gmail.com', 'Calle 9', '90', '1988-09-09', 'password9');
INSERT INTO cliente (nombre, apellidoM, apellidoP, telefono, correo, calle, numero, nacimiento, contrasena) VALUES ('Isabel', 'Diaz', 'Reyes', '8899001122', 'isabel.diaz@gmail.com', 'Calle 10', '100', '1989-10-10', 'password10');

CREATE TABLE `empresa` (
  `idEmpresa` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `rfc` varchar(45) DEFAULT NULL,
  `representante` varchar(45) DEFAULT NULL,
  `estatus` varchar(45) NOT NULL,
  `ciudad` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `pagina` varchar(45) DEFAULT NULL,
  `codigoP` varchar(45) DEFAULT NULL,
  `logo` longblob,
  `correo` varchar(45) DEFAULT NULL,
  `calle` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  `nombreComercial` varchar(45) NOT NULL,
  PRIMARY KEY (`idEmpresa`)
) 

