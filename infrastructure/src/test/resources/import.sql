
drop table if exists t_pays ;

create table t_pays  (id varchar primary key auto_increment, countryShortName varchar,  countryCodeLen2 varchar, countryCodeLen3 varchar,
                      countryNumCode varchar,                countryLanguage varchar,   language varchar,        countryContinentId varchar,
                      status varchar,                        lang varchar,              code varchar);

INSERT INTO t_pays (id, countryShortName, countryCodeLen2, countryCodeLen3, countryNumCode, countryLanguage, language, countryContinentId, status, lang, code) VALUES ('1', 'China', 'CN', 'CHN', '86', 'CN', 'CN', 'ASIA', '1', 'FR', null);