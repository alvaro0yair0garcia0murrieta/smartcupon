

INSERT INTO `smartcupon2`.`empresa`
(`idEmpresa`,
`nombre`,
`rfc`,
`representante`,
`estatus`,
`ciudad`,
`telefono`,
`pagina`,
`codigoP`,
`logo`,
`correo`,
`calle`,
`numero`,
`nombreComercial`)
VALUES
(1,
'Amazon',
'AAA010101AAA',
'Jeff Bezos',
'ACTIVO',
'Ciudad de México',
'55 5555 5555',
'www.amazon.com',
'06600',
'https://www.amazon.com/gp/images/G/01/images/logo/amazon_logo._V1.png',
'info@amazon.com',
'Av. Periférico Sur 4699',
'5',
'Amazon México'),
(2,
'Walmart',
'BBB020202BBB',
'Doug McMillon',
'ACTIVO',
'Monterrey',
'81 8181 8181',
'www.walmart.com.mx',
'64640',
'https://www.walmart.com.mx/content/dam/walmart/images/logo/walmart_logo.svg',
'info@walmart.com.mx',
'Av. Eugenio Garza Sada 2245',
'100',
'Walmart México'),
(3,
'Liverpool',
'CCC030303CCC',
'Gerardo Kuri Breña',
'ACTIVO',
'Guadalajara',
'33 3333 3333',
'www.liverpool.com.mx',
'44100',
'https://www.liverpool.com.mx/content/dam/liverpool/images/logo/liverpool_logo.svg',
'info@liverpool.com.mx',
'Av. Vallarta 2500',
'123',
'Liverpool México'),
(4,
'Soriana',
'DDD040404DDD',
'Luis Miguel Abascal',
'ACTIVO',
'Puebla',
'222 2222 2222',
'www.soriana.com',
'72570',
'https://www.soriana.com/content/dam/soriana/images/logo/soriana_logo.svg',
'info@soriana.com',
'Blvd. Valsequillo 1901',
'56',
'Soriana México'),
(5,
'Coppel',
'EEE050505EEE',
'Rafael Aponte',
'ACTIVO',
'Chihuahua',
'614 6146 1461',
'www.coppel.com',
'31000',
'https://www.coppel.com/content/dam/coppel/images/logo/coppel_logo.svg',
'info@coppel.com',
'Av. Juárez 1500',
'10',
'Coppel México'),
(6,
'Bodega Aurrera',
'FFF060606FFF',
'Manuel Villaseñor',
'ACTIVO',
'León',
'477 4774 7747',
'www.bodegaaurrera.com.mx',
'37170',
'https://www.bodegaaurrera.com.mx/content/dam/bodegaaurrera/images/logo/bodegaaurrera_logo.svg',
'info@bodegaaurrera.com.mx',
'Av. Insurgentes 1234',
'5',
'Bodega Aurrera México');
INSERT INTO `smartcupon2`.`usuario`
(`idUsuario`,
`idEmpresa`,
`nombre`,
`rol`,
`username`,
`contrasena`,
`curp`,
`correo`,
`apellidoP`,
`apellidoM`)
VALUES
(666,
-1,
"Alvaro",
"GENERAL",
"admin",
123,
"GAMA19961003DNO",
"murrieta.alvaro@gmail.com",
"Garcia",
"Murrieta"),
(333,
1,
"Aranza",
"COMERCIAL",
"aranzasinte",
123,
"AACC000923MVZSTRA7",
"a-raccoon19@hotmail.com",
"Mendez",
"");
INSERT INTO `smartcupon2`.`sucursal`
(`idSucursal`,
`nombre`,
`calle`,
`numero`,
`encargado`,
`colonia`,
`ciudad`,
`telefono`,
`codigoP`,
`longitud`,
`latitud`,
`idEmpresa`)
VALUES
(1,
'Bodega Aurrera',
'Av. Antonio Chedraui Caram 208',
'208',
'Juan Pérez',
'Lomas de Casa Blanca',
'Xalapa',
'228 811 7842',
'91164',
-96.159162,
19.120159,
6),
(2,
'Bodega Aurrera Express, Sumidero',
'C. Cam. al Sumidero 3',
'3',
'Marta García',
'Los Pinos',
'Xalapa',
'228 810 6887',
'91164',
-96.160452,
19.118744,
6),
(3,
'Bodega Aurrera Express',
'Av Atenas Veracruzana 105',
'105',
'Pedro López',
'San Rafael',
'Xalapa',
'228 843 6109',
'91164',
-96.158503,
19.121463,
6);

INSERT INTO `smartcupon2`.`sucursal`
(`nombre`,
`calle`,
`numero`,
`encargado`,
`colonia`,
`ciudad`,
`telefono`,
`codigoP`,
`longitud`,
`latitud`,
`idEmpresa`)
VALUES
(
'Soriana Híper',
'Av. Lázaro Cárdenas 1000',
'1000',
'Juana Pérez',
'Centro',
'Xalapa',
'228 818 1111',
'91000',
-96.159284,
19.120256,
2),
(
'Walmart Express',
'Av. Xalapa-Veracruz 100',
'100',
'Pedro López',
'Colonia Moderna',
'Xalapa',
'228 812 1212',
'91000',
-96.158753,
19.121539,
1),
(
'Liverpool',
'Av. Benito Juárez 2000',
'2000',
'Marta García',
'Centro',
'Xalapa',
'228 814 1414',
'91000',
-96.159090,
19.120383,
3),
(
'Coppel',
'Av. Manuel Ávila Camacho 1000',
'1000',
'Juan Pérez',
'Centro',
'Xalapa',
'228 816 1616',
'91000',
-96.158876,
19.120506,
4),
(
'Amazon Fresh',
'C. Cam. al Sumidero 100',
'100',
'Pedro López',
'Los Pinos',
'Xalapa',
'228 817 1717',
'91000',
-96.160267,
19.118878,
5);
INSERT INTO `smartcupon2`.`promocion`
(`idPromocion`,
`imagen`,
`codigo`,
`nombre`,
`descripcion`,
`restriccion`,
`categoria`,
`tipo`,
`estatus`,
`fechaInicio`,
`fechaFin`,
`cuponesMax`,
`idEmpresa`)
VALUES
(1,
'https://i.imgur.com/e69Y493.jpg',
'BOG01',
'3x2 en productos de limpieza',
'Lleva 3 productos de limpieza y paga solo por 2. Válido en presentaciones participantes.',
'Máximo 3 promociones por cliente.',
'Limpieza',
'Oferta',
'Activo',
'2024-01-01',
'2024-01-31',
100,
6),
(2,
'https://i.imgur.com/6753923.jpg',
'BOG02',
'20% de descuento en productos escolares',
'Obtén un 20% de descuento en productos escolares seleccionados. Válido en tiendas físicas y en línea.',
'No acumulable con otras promociones.',
'Escolar',
'Descuento',
'Activo',
'2024-01-01',
'2024-02-28',
200,
6),
(3,
'https://i.imgur.com/5544223.jpg',
'BOG03',
'10% de descuento en carnes',
'Obtén un 10% de descuento en carnes de res, cerdo y pollo. Válido en tiendas físicas y en línea.',
'No acumulable con otras promociones.',
'Alimentos',
'Descuento',
'Activo',
'2024-01-01',
'2024-03-31',
100,
6),
(4,
'https://i.imgur.com/9998887.jpg',
'BOG04',
'30% de descuento en pantallas',
'Obtén un 30% de descuento en pantallas seleccionadas. Válido en tiendas físicas y en línea.',
'No acumulable con otras promociones.',
'Electrónica',
'Descuento',
'Activo',
'2024-01-01',
'2024-04-30',
50,
6),
(5,
'https://i.imgur.com/1112223.jpg',
'BOG05',
'2x1 en juguetes',
'Lleva 2 juguetes y paga solo por 1. Válido en presentaciones participantes.',
'Máximo 3 promociones por cliente.',
'Juguetería',
'Oferta',
'Activo',
'2024-01-01',
'2024-05-31',
100,
6);
INSERT INTO `smartcupon2`.`sucursal`
(`idSucursal`,
`nombre`,
`calle`,
`numero`,
`encargado`,
`colonia`,
`ciudad`,
`telefono`,
`codigoP`,
`longitud`,
`latitud`,
`idEmpresa`)
VALUES
(4,
'Soriana Híper',
'Av. Lázaro Cárdenas 1000',
'1000',
'Juana Pérez',
'Centro',
'Xalapa',
'228 818 1111',
'91000',
-96.159284,
19.120256,
2),
(5,
'Walmart Express',
'Av. Xalapa-Veracruz 100',
'100',
'Pedro López',
'Colonia Moderna',
'Xalapa',
'228 812 1212',
'91000',
-96.158753,
19.121539,
1),
(6,
'Liverpool',
'Av. Benito Juárez 2000',
'2000',
'Marta García',
'Centro',
'Xalapa',
'228 814 1414',
'91000',
-96.159090,
19.120383,
3),
(7,
'Coppel',
'Av. Manuel Ávila Camacho 1000',
'1000',
'Juan Pérez',
'Centro',
'Xalapa',
'228 816 1616',
'91000',
-96.158876,
19.120506,
4),
(8,
'Amazon Fresh',
'C. Cam. al Sumidero 100',
'100',
'Pedro López',
'Los Pinos',
'Xalapa',
'228 817 1717',
'91000',
-96.160267,
19.118878,
5);

