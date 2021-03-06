SET GLOBAL query_cache_size = 0;

SET GLOBAL query_cache_type = 0;

DROP DATABASE IF EXISTS cashaccounts;

CREATE DATABASE cashaccounts;

USE cashaccounts;

CREATE TABLE cashsummary (
id bigint(20) NOT NULL,
accountname VARCHAR(255),
accountnumber VARCHAR(255),
avalbalance DOUBLE,
currentbalance DOUBLE,
memberid VARCHAR(255)
);

CREATE TABLE cashdetail (
id bigint(20) NOT NULL,
memberid VARCHAR(255),
accountnumber VARCHAR(255),
monthmulti MEDIUMINT,
txndate VARCHAR(255),
txndescription VARCHAR(255),
txnamount DOUBLE,
runbalance DOUBLE,
txnorder MEDIUMINT
);



DROP DATABASE IF EXISTS loanaccounts;

CREATE DATABASE loanaccounts;

USE loanaccounts;

CREATE TABLE loansummary (
id bigint(20) NOT NULL,
accountname VARCHAR(255),
accountnumber VARCHAR(255),
currentbalance DOUBLE,
paymentdueamt DOUBLE,
paymentduedate VARCHAR(255),
memberid VARCHAR(255),
accounttype VARCHAR(255)
);


CREATE TABLE loandetail (
id bigint(20) NOT NULL,
memberid VARCHAR(255),
accountnumber VARCHAR(255),
monthmulti MEDIUMINT,
txndate VARCHAR(255),
txndescription VARCHAR(255),
txnamount DOUBLE,
runbalance DOUBLE,
txnorder MEDIUMINT
);


CREATE TABLE loandtail (
id bigint(20) NOT NULL,
memberid VARCHAR(255),
accountnumber VARCHAR(255),
monthmulti MEDIUMINT,
txndate VARCHAR(255),
txndescription VARCHAR(255),
txnamount DOUBLE,
runbalance DOUBLE,
txnorder MEDIUMINT
);


CREATE TABLE loanapplication (
loanid VARCHAR(255),
accountnumber VARCHAR(255),
loancategory VARCHAR(30),
email VARCHAR(255),
firstname VARCHAR(255),
lastname VARCHAR(255),
orgbank VARCHAR(255),
loantype VARCHAR(255),
loanamount VARCHAR(255),
creditcheck VARCHAR(255),
citylocation VARCHAR(255),
msappsubdone VARCHAR(10),
msdocsdone VARCHAR(10),
mscreditdone VARCHAR(10),
msunderdone VARCHAR(10),
msapprovaldone VARCHAR(10),
msstepcount VARCHAR(10)
);




