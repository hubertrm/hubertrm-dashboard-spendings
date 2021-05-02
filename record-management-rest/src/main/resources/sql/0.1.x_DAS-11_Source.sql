INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('Belfius', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='Belfius Bank'));
INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('Belfius Epargne', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='Belfius Bank'));
INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('BNP', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='BNP Paribas S.A'));
INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('BNP PREPAID', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='BNP Paribas S.A'));
INSERT INTO SOURCE (name, creation_date) VALUES ('Cash', TO_DATE('10/10/20','dd/MM/yy'));
INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('EdenRed', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='Edenred'));
INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('Eco-cheques', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='Edenred'));
INSERT INTO SOURCE (name, creation_date, organisation_id) VALUES ('Revolut', TO_DATE('10/10/20','dd/MM/yy'),
(SELECT id FROM ORGANISATION WHERE name='Revolut Ltd'));

commit;