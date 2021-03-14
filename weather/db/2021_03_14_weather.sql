DROP TABLE IF EXISTS wth_city;
CREATE TABLE `wth_city` (
  `id` int(7) NOT NULL AUTO_INCREMENT COMMENT 'city id',
  `sys_reg_dtm` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'regist date',
  `sys_upd_dtm` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT 'update date',
  `name` varchar(100) NOT NULL COMMENT 'city name',
  `longt` decimal(8,6) DEFAULT 0.000000,
  `lat` decimal(9,6) DEFAULT 0.000000,
  `state` varchar(100) DEFAULT NULL COMMENT 'state of city',
  `country` varchar(100) NOT NULL COMMENT 'country of city',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COMMENT='weather city';

DROP TABLE IF EXISTS wth_weather_base;
CREATE TABLE `wth_weather_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `sys_reg_dtm` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'regist date',
  `sys_upd_dtm` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT 'update date',
  `weather_name` varchar(50) DEFAULT NULL COMMENT 'weather name',
  `weather_description` text DEFAULT NULL COMMENT 'weather description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='weather base';

DROP TABLE IF EXISTS wth_weather_details;
CREATE TABLE `wth_weather_details` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'weather details id',
  `sys_reg_dtm` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'regist date',
  `sys_upd_dtm` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT 'update date',
  `weather_dt` date NOT NULL,
  `city_id` int(7) NOT NULL COMMENT 'city id',
  `weather_base_id` int(7) NOT NULL COMMENT 'weather base id',
  `temperature` decimal(5,2) DEFAULT NULL COMMENT 'temperature in celcius',
  `temp_min` decimal(5,2) DEFAULT NULL COMMENT 'temperature min in celcius',
  `temp_max` decimal(5,2) DEFAULT NULL COMMENT 'temperature max in celcius',
  `pressure` decimal(5,2) DEFAULT NULL COMMENT 'pressure in celcius',
  `humidity` decimal(5,2) DEFAULT NULL COMMENT 'humidity max in celcius',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='weather details';









