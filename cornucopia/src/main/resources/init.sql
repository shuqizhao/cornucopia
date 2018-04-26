use cornucopia;

drop PROCEDURE if EXISTS sp_user_login;

CREATE PROCEDURE sp_user_login(var_un varchar(255),var_pwd varchar(255))
BEGIN
select * from sys_user where name=var_un and pwd=var_pwd and is_deleted=0 and is_enabled=1;
end;

