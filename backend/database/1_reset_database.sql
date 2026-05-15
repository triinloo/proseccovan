-- Kustutab public schema (mis põhimõtteliselt kustutab kõik tabelid)
DROP SCHEMA IF EXISTS proseccovan CASCADE;
-- Loob uue public schema vajalikud õigused
CREATE SCHEMA proseccovan;
-- taastab vajalikud andmebaasi õigused
GRANT ALL ON SCHEMA proseccovan TO postgres;
GRANT ALL ON SCHEMA proseccovan TO PUBLIC;