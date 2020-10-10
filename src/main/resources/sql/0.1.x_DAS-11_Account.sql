INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('Belfius', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='Belfius Bank'));
INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('Belfius Epargne', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='Belfius Bank'));
INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('BNP', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='BNP Paribas S.A'));
INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('BNP PREPAID', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='BNP Paribas S.A'));
INSERT INTO ACCOUNT (name, creation_date) VALUES ('Cash', '10/10/20 23:50:00,000000000');
INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('EdenRed', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='Edenred'));
INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('Eco-cheques', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='Edenred'));
INSERT INTO ACCOUNT (name, creation_date, organisation_id) VALUES ('Revolut', '10/10/20 23:50:00,000000000',
(SELECT id FROM ORGANISATION WHERE name='Revolut Ltd'));

commit;