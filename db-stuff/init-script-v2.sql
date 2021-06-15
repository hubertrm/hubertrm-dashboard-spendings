
CONNECT C##HUBERTRM/HUBERTRM;


CREATE TABLE source (
    id NUMBER(10) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name NVARCHAR2(100) NOT NULL,
    creation_date DATE NOT NULL,
    organisation_id NUMBER(10),
    CONSTRAINT source_pk PRIMARY KEY (id),
    CONSTRAINT source_organisation_fk
        FOREIGN KEY (organisation_id)
            REFERENCES organisation(id)
            ON DELETE SET NULL
);
/* Create objects in the schema */
/* Source: https://www.techonthenet.com/oracle/tables/create_table.php */
CREATE TABLE simpleRecord (
    id NUMBER(10) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pay_date DATE NOT NULL,
    amount NUMBER(6,2) NOT NULL,
    category_id NUMBER(10),
    source_id NUMBER(10),
    comments NVARCHAR2(500),
    CONSTRAINT record_pk PRIMARY KEY (id),
    CONSTRAINT record_category_fk
        FOREIGN KEY (category_id)
        REFERENCES category(id)
        ON DELETE SET NULL ,
    CONSTRAINT record_source_fk
        FOREIGN KEY (source_id)
        REFERENCES source(id)
        ON DELETE SET NULL
);

CONNECT / AS SYSDBA;
/* Grant Object Privileges */
/* Source: https://www.techonthenet.com/oracle/grant_revoke.php */
GRANT ALL ON simpleRecord TO c##HUBERTRM;
GRANT ALL ON source TO c##HUBERTRM;

/* Create Synonyms for Objects */
CREATE PUBLIC SYNONYM simpleRecord
FOR C##HUBERTRM.simpleRecord;
CREATE PUBLIC SYNONYM source
FOR C##HUBERTRM.source;

commit;