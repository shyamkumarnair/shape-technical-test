drop table SHAPE if exists;

create table SHAPE(
ENTITY_TYPE	VARCHAR(31)	NOT	NULL,
ID	        BIGINT(19)	NOT	NULL AUTO_INCREMENT,
DESC    	VARCHAR(255),
NAME	        VARCHAR(255)	,
TYPE	        VARCHAR(255)	,
x_Bottom_Left   DECIMAL(12)	,
y_Bottom_Left   DECIMAL(12)	, 
x_Top_Left      DECIMAL(12)	, 
y_Top_Left      DECIMAL(12)	, 
x_Bottom_Right  DECIMAL(12)	, 
y_Bottom_Right  DECIMAL(12)	,
x_Top_Right     DECIMAL(12)	, 
y_Top_Right     DECIMAL(12)	
);