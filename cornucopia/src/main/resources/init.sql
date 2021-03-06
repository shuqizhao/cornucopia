use cornucopia;

drop PROCEDURE if EXISTS sp_user_login;

CREATE PROCEDURE sp_user_login(var_un varchar(255),var_pwd varchar(255))
BEGIN
select * from sys_user where name=var_un and pwd=var_pwd and is_deleted=0 and is_enabled=1;
end;

drop PROCEDURE if EXISTS sp_white_list_all;

CREATE PROCEDURE sp_white_list_all()
BEGIN
select * from sys_white_list where is_deleted=0 and is_enabled=1;
end;

drop PROCEDURE if EXISTS sp_menu_all;

CREATE PROCEDURE sp_menu_all()
BEGIN
select * from sys_menu where is_deleted=0 and is_enabled=1;
end;

drop PROCEDURE if EXISTS sp_user_get_by_page;

CREATE PROCEDURE sp_user_get_by_page(start INT,length INT)
BEGIN
   declare totalCount int;
   select count(1) into totalCount from sys_user;
	select *,totalCount from sys_user limit start,length;
	
	
END;

drop PROCEDURE if EXISTS sp_role_get_by_page;

CREATE PROCEDURE sp_role_get_by_page(start INT,length INT)
BEGIN
   declare totalCount int;
   select count(1) into totalCount from sys_role;
	select *,totalCount from sys_role limit start,length;
	
	
END;

drop PROCEDURE if EXISTS sp_user_get;

create PROCEDURE sp_user_get(userId int)
BEGIN
	select * from sys_user where id = userId;
end;