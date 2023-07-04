-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;
create table if not exists workspace_state_events (
    `offset` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `state` int,
    `workspace` binary(16) not null,
    PRIMARY KEY (`offset`),
    FOREIGN KEY (`workspace`) REFERENCES `workspaces` (`uuid`)
) ENGINE=innodb;
