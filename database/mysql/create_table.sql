CREATE TABLE `nomenclature`.`t_pays` (
  `id` VARCHAR(20) NOT NULL,
  `countryShortName` VARCHAR(45) NULL,
  `countryCodeLen2` VARCHAR(45) NULL,
  `countryCodeLen3` VARCHAR(45) NULL,
  `countryNumCode` VARCHAR(45) NULL,
  `countryLanguage` VARCHAR(45) NULL,
  `language` VARCHAR(45) NULL,
  `countryContinentId` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
);
