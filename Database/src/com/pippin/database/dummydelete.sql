SET SQL_SAFE_UPDATES = 0;
DELETE FROM groups WHERE groups_id <> -1;
DELETE FROM group_members WHERE user_id <> -1;
DELETE FROM user_businessinfo_connections WHERE connections_id <> -1;
DELETE FROM user_homeinfo_connections WHERE connections_id <> -1;
DELETE FROM user_college_connections WHERE connections_id <> -1;
DELETE FROM user_high_school_connections WHERE connections_id <> -1;

DELETE FROM connections WHERE connections_id <> -1;
DELETE FROM additional_numbers WHERE user_id <> -1;
DELETE FROM additional_emails WHERE user_id <> -1;
DELETE FROM social_media WHERE user_id <> -1;
DELETE FROM user_businessinfo WHERE user_id <> -1;
DELETE FROM user_homeinfo WHERE user_id <> -1;
DELETE FROM user_high_school WHERE user_id <> -1;
DELETE FROM user_college WHERE user_id <> -1;
DELETE FROM social_media WHERE user_id <> -1;
DELETE FROM users WHERE user_id <> -1;
