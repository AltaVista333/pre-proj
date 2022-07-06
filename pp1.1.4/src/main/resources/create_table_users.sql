CREATE TABLE `Users`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(15) NOT NULL,
    `last_name` VARCHAR(15) NOT NULL,
    `age`       TINYINT     NOT NULL,
    PRIMARY KEY (`id`)
);