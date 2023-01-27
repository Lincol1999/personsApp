CREATE TABLE IF NOT EXISTS persondb.persons (
                                  id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                  "name" varchar NULL,
                                  age int4 NULL,
                                  active bool NULL,
                                  CONSTRAINT persons_pk PRIMARY KEY (id)
);