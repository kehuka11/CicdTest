-- PDBの新規作成(pdb$seedから作成)
CREATE PLUGGABLE DATABASE pdb01 ADMIN USER pdb01admin IDENTIFIED BY passw0rd
file_name_convert = ('/opt/oracle/oradata/XE/pdbseed/','/opt/oracle/oradata/XE/pdb01/');

-- PDBの起動
ALTER PLUGGABLE DATABASE pdb01 OPEN;

-- PDBの自動起動設定
ALTER PLUGGABLE DATABASE pdb01 SAVE STATE;

-- PDB切り替え（これがないと、コンテナ再起動時に「ORA-01109: database not open」が発生）
ALTER SESSION SET CONTAINER=pdb01;

-- ロール権限付与 
GRANT DBA TO PDB_DBA;

-- 表領域作成
CREATE TABLESPACE users
DATAFILE '/opt/oracle/oradata/XE/pdb01/users01.dbf' SIZE 3000M
AUTOEXTEND ON NEXT 500K MAXSIZE UNLIMITED;