drop table T_WINDPARK if exists;

create table T_WINDPARK (ID bigint identity primary key, NUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER));
                        
ALTER TABLE T_WINDPARK ALTER COLUMN BALANCE SET DEFAULT 0.0;
