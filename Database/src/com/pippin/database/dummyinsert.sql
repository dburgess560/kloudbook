SET SQL_SAFE_UPDATES = 0;
ALTER TABLE users AUTO_INCREMENT = 1;
INSERT INTO users (primary_email,password) VALUES ('','etse');
SET @user_id = LAST_INSERT_ID();
INSERT INTO user_college (user_id) values (@user_id);
INSERT INTO user_high_school (user_id) values (@user_id);
INSERT INTO user_businessinfo (user_id) values (@user_id);
INSERT INTO user_homeinfo (user_id) values (@user_id);
INSERT INTO social_media (user_id) values (@user_id);
INSERT INTO additional_emails (user_id,email) values (@user_id,'test1');
INSERT INTO additional_emails (user_id,email) values (@user_id,'test2');
INSERT INTO additional_emails (user_id,email) values (@user_id,'test3');
INSERT INTO additional_emails (user_id,email) values (@user_id,'test4');
INSERT INTO additional_emails (user_id,email) values (@user_id,'test5');
INSERT INTO additional_emails (user_id,email) values (@user_id,'test6');
INSERT INTO additional_numbers(user_id,number) values (@user_id,'1');
INSERT INTO additional_numbers(user_id,number) values (@user_id,'1');
INSERT INTO additional_numbers(user_id,number) values (@user_id,'1');
INSERT INTO additional_numbers(user_id,number) values (@user_id,'1');
INSERT INTO additional_numbers(user_id,number) values (@user_id,'1');
INSERT INTO social_media(user_id) values (@user_id);
INSERT INTO social_media(user_id) values (@user_id);
INSERT INTO social_media(user_id) values (@user_id);
INSERT INTO social_media(user_id) values (@user_id);
INSERT INTO social_media(user_id) values (@user_id);
INSERT INTO social_media(user_id) values (@user_id);

/* above is the "public user"*/
/* User Franz*/
INSERT INTO users (primary_email,first_name, last_name,middle_name,gender,hometown,mobile_number,mobile_number_ext,mobile_country_code,registered,birthday,password) VALUES ('test@gmail.com','Franz', 'Jorge Paco','Ferdinand','F','Amherst','7775554444','44444','1','2008-7-07','1991-07-04','');
SET @user_id1 = LAST_INSERT_ID();
INSERT INTO user_college (user_id,college) values (@user_id1,'Hampshire College');
INSERT INTO user_high_school (user_id,high_school) values (@user_id1,'Holyoke High');
INSERT INTO user_businessinfo (user_id,company) values (@user_id1,'Amazon');
INSERT INTO user_homeinfo (user_id,current_city) values (@user_id1,'Amherst');
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id1,1,1,1,1,1,1,1,1,1,1,1);
SET @connections_id = LAST_INSERT_ID();
INSERT INTO user_college_connections (connections_id,college_access) VALUES (@connections_id,1);
INSERT INTO user_high_school_connections (connections_id,high_school_access) VALUES (@connections_id,1);
INSERT INTO user_homeinfo_connections (connections_id,home_address1_access, home_address2_access, home_city_access, home_state_access, home_zip_access, home_number_access, current_city_access) VALUES (@connections_id,1,1,1,1,1,1,1);
INSERT INTO user_businessinfo_connections (connections_id,business_address1_access, business_address2_access, business_city_access, business_state_access, business_zip_access, business_number_access, company_access) VALUES (@connections_id,1,1,1,1,1,1,1);
/* User Javier*/
INSERT INTO users (primary_email,first_name, last_name,middle_name,gender,hometown,mobile_number,mobile_number_ext,mobile_country_code,registered,birthday,password) VALUES ('test1@gmail.com','Javier','Angelis Encinas','Bardem','M','New York','7775567444','44444','1','2076-7-04','1993-07-04','');
SET @user_id2 = LAST_INSERT_ID();
INSERT INTO user_college (user_id,college) values (@user_id2,'UMass');
INSERT INTO user_high_school (user_id,high_school) values (@user_id2,'Amherst High');
INSERT INTO user_businessinfo (user_id,company) values (@user_id2,'Google');
INSERT INTO user_homeinfo (user_id,current_city) values (@user_id2,'Amherst');
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id2,1,1,1,1,1,1,1,1,1,1,1);
SET @connections_id = LAST_INSERT_ID();
INSERT INTO user_college_connections (connections_id,college_access) VALUES (@connections_id,1);
INSERT INTO user_high_school_connections (connections_id,high_school_access) VALUES (@connections_id,1);
INSERT INTO user_homeinfo_connections (connections_id,home_address1_access, home_address2_access, home_city_access, home_state_access, home_zip_access, home_number_access, current_city_access) VALUES (@connections_id,1,1,1,1,1,1,1);
INSERT INTO user_businessinfo_connections (connections_id,business_address1_access, business_address2_access, business_city_access, business_state_access, business_zip_access, business_number_access, company_access) VALUES (@connections_id,1,1,1,1,1,1,1);
/* User Tom*/
INSERT INTO users (primary_email,first_name, last_name,middle_name,gender,hometown,mobile_number,mobile_number_ext,mobile_country_code,registered,birthday,password) VALUES ('test2@gmail.com','Tom',NULL,'Cruise','M','Chicago','7775567444','44444','1','2076-7-04','1993-07-04','');
SET @user_id3 = LAST_INSERT_ID();
INSERT INTO additional_emails (user_id,email) values (@user_id3,'321312@gmail.com');
INSERT INTO user_college (user_id,college) values (@user_id3,'Amherst College');
INSERT INTO user_high_school (user_id,high_school) values (@user_id3,'Holyoke High');
INSERT INTO user_businessinfo (user_id,company) values (@user_id3,'IBM');
INSERT INTO user_homeinfo (user_id,current_city) values (@user_id3,'Hadley');
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id3,1,1,1,1,1,1,1,1,1,1,1);
SET @connections_id = LAST_INSERT_ID();
INSERT INTO user_college_connections (connections_id,college_access) VALUES (@connections_id,1);
INSERT INTO user_high_school_connections (connections_id,high_school_access) VALUES (@connections_id,1);
INSERT INTO user_homeinfo_connections (connections_id,home_address1_access, home_address2_access, home_city_access, home_state_access, home_zip_access, home_number_access, current_city_access) VALUES (@connections_id,1,1,1,1,1,1,1);
INSERT INTO user_businessinfo_connections (connections_id,business_address1_access, business_address2_access, business_city_access, business_state_access, business_zip_access, business_number_access, company_access) VALUES (@connections_id,1,1,1,1,1,1,1);
/* User Margaret */
INSERT INTO users (primary_email,first_name, last_name,middle_name,gender,hometown,mobile_number,mobile_number_ext,mobile_country_code,registered,birthday,password) VALUES ('test3@gmail.com','Margaret','Jay','Thatcher','F','Moscow','7775567444','44444','1','2076-7-04','1993-07-04','');
SET @user_id4 = LAST_INSERT_ID();
INSERT INTO additional_emails (user_id,email) values (@user_id4,'321312@gmail.com');
INSERT INTO user_college (user_id,college) values (@user_id4,'Boston College');
INSERT INTO user_high_school (user_id,high_school) values (@user_id4,'Boston High');
INSERT INTO user_businessinfo (user_id,company) values (@user_id4,'Google');
INSERT INTO user_homeinfo (user_id,current_city) values (@user_id4,'Boston');
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id4,1,1,1,1,1,1,1,1,1,1,1);
SET @connections_id = LAST_INSERT_ID();
INSERT INTO user_college_connections (connections_id,college_access) VALUES (@connections_id,1);
INSERT INTO user_high_school_connections (connections_id,high_school_access) VALUES (@connections_id,1);
INSERT INTO user_homeinfo_connections (connections_id,home_address1_access, home_address2_access, home_city_access, home_state_access, home_zip_access, home_number_access, current_city_access) VALUES (@connections_id,1,1,1,1,1,1,1);
INSERT INTO user_businessinfo_connections (connections_id,business_address1_access, business_address2_access, business_city_access, business_state_access, business_zip_access, business_number_access, company_access) VALUES (@connections_id,1,1,1,1,1,1,1);
/* User Tommy */
INSERT INTO users (primary_email,first_name, last_name,middle_name,gender,hometown,mobile_number,mobile_number_ext,mobile_country_code,registered,birthday,password) VALUES ('test4@gmail.com','Tommy','Jay','Cruise','F','Moscow','7775567444','44444','1','2076-7-04','1992-07-04','');
SET @user_id5 = LAST_INSERT_ID();
INSERT INTO additional_emails (user_id,email) values (@user_id5,'321224312@gmail.com');
INSERT INTO user_college (user_id,college) values (@user_id5,'Boston University');
INSERT INTO user_high_school (user_id,high_school) values (@user_id5,'Boston Prep');
INSERT INTO user_businessinfo (user_id,company) values (@user_id5,'Ebay');
INSERT INTO user_homeinfo (user_id,current_city) values (@user_id5,'Arlington');
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id5,1,1,1,1,1,1,1,1,1,1,1);
SET @connections_id = LAST_INSERT_ID();
INSERT INTO user_college_connections (connections_id,college_access) VALUES (@connections_id,1);
INSERT INTO user_high_school_connections (connections_id,high_school_access) VALUES (@connections_id,1);
INSERT INTO user_homeinfo_connections (connections_id,home_address1_access, home_address2_access, home_city_access, home_state_access, home_zip_access, home_number_access, current_city_access) VALUES (@connections_id,1,1,1,1,1,1,1);
INSERT INTO user_businessinfo_connections (connections_id,business_address1_access, business_address2_access, business_city_access, business_state_access, business_zip_access, business_number_access, company_access) VALUES (@connections_id,1,1,1,1,1,1,1);


/* Test Friends */
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id1,@user_id2,1,1,1,1,1,1,1,1,1,1);
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id3,@user_id1,1,1,1,1,1,1,1,1,1,1);
INSERT INTO connections (user1_id,user2_id,primary_email_access,first_name_access,middle_name_access, last_name_access,gender_access,hometown_access,mobile_number_access,picture_access,registered_access,birthday_access) values(@user_id1,@user_id4,1,1,1,1,1,1,1,1,1,1);

ALTER TABLE groups AUTO_INCREMENT = 1;
INSERT INTO groups(name,description,shared_info) VALUES('kbGroup1', 'first kb group',1);
INSERT INTO groups(name,description,shared_info) VALUES('kbGroup2', 'second kb group',1);
SET @groups_id = LAST_INSERT_ID();
ALTER TABLE group_members AUTO_INCREMENT = 1;
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id1,1);
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id2,0);
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id3,0);
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id4,0);

INSERT INTO groups(name,description,shared_info) VALUES('kbGroup3', 'third kb group',1);
SET @groups_id = LAST_INSERT_ID();
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id1,1);
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id2,0);
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id3,0);
INSERT INTO group_members(group_id,user_id,access) VALUES (@groups_id,@user_id4,-1);