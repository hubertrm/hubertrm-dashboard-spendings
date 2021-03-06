/* Source: https://www.techonthenet.com/oracle/tablespaces/create_tablespace.php */
/* Tablespace creation */

/* CREATE TABLESPACE statement that creates a permanent tablespace that will extend when more space is required */
CREATE TABLESPACE tbs_perm_01
  DATAFILE 'tbs_perm_01.dat'
    SIZE 20M
    REUSE
    AUTOEXTEND ON NEXT 10M MAXSIZE 200M;

/* CREATE TABLESPACE statement that creates a temporary tablespace */
CREATE TEMPORARY TABLESPACE tbs_temp_01
  TEMPFILE 'tbs_temp_01.dbf'
    SIZE 5M
    AUTOEXTEND ON;

/* CREATE TABLESPACE statement that creates an undo tablespace */
CREATE UNDO TABLESPACE tbs_undo_01
  DATAFILE 'tbs_undo_01.f'
    SIZE 5M
    AUTOEXTEND ON
  RETENTION GUARANTEE;

commit;
/* Source https://www.techonthenet.com/oracle/schemas/create_schema.php */
/* Schema-User creation */

/* Create the user that will serve as schema */
CREATE USER c##HUBERTRM
    IDENTIFIED BY HUBERTRM
    DEFAULT TABLESPACE tbs_perm_01
    TEMPORARY TABLESPACE tbs_temp_01
    QUOTA 20M on tbs_perm_01;

/* Assign SYSTEM privileges to new user in Oracle */
GRANT create session TO c##HUBERTRM;
GRANT create table TO c##HUBERTRM;
GRANT create view TO c##HUBERTRM;
GRANT create any trigger TO c##HUBERTRM;
GRANT create any procedure TO c##HUBERTRM;
GRANT create sequence TO c##HUBERTRM;
GRANT create synonym TO c##HUBERTRM;

CONNECT C##HUBERTRM/HUBERTRM;

/* Create objects in the schema */
/* Source: https://www.techonthenet.com/oracle/tables/create_table.php */
CREATE TABLE spending (
    id NUMBER(10) NOT NULL,
    date TIMESTAMP NOT NULL,
    amount NUMBER(6) NOT NULL,
    category_id NUMBER(10) NOT NULL,
    account_id NUMBER(10) NOT NULL,
    comment NVARCHAR2(500),
    CONSTRAINT spending_pk PRIMARY KEY (id),
    CONSTRAINT category_fk
        FOREIGN KEY (category_id)
        REFERENCES category(id),
    CONSTRAINT account_fk
        FOREIGN KEY (account_id)
        REFERENCES account(id)
);

CREATE TABLE category (
    id NUMBER(10) NOT NULL,
    name NVARCHAR2(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE account (
    id NUMBER(10) NOT NULL,
    name NVARCHAR2(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    organisation_id NUMBER(10),
    CONSTRAINT category_pk PRIMARY KEY (id),
    CONSTRAINT organisation_fk
        FOREIGN KEY (organisation_id)
        REFERENCES organisation(id)
);

CREATE TABLE organisation (
    id NUMBER(10) NOT NULL,
    name NVARCHAR2(100) NOT NULL,
    CONSTRAINT organisation_pk PRIMARY KEY (id)
)

CONNECT / AS SYSDBA;
/* Grant Object Privileges */
/* Source: https://www.techonthenet.com/oracle/grant_revoke.php */
GRANT ALL ON spending TO c##HUBERTRM;
GRANT ALL ON category TO c##HUBERTRM;
GRANT ALL ON account TO c##HUBERTRM;
GRANT ALL ON organisation TO c##HUBERTRM;

/* Create Synonyms for Objects */
CREATE PUBLIC SYNONYM spending
FOR C##HUBERTRM.spending;
CREATE PUBLIC SYNONYM category
FOR C##HUBERTRM.category;
CREATE PUBLIC SYNONYM account
FOR C##HUBERTRM.account;
CREATE PUBLIC SYNONYM organisation
FOR C##HUBERTRM.organisation;

commit;