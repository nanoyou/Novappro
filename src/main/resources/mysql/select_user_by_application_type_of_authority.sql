SELECT
	`id`,
	`username`,
	`raw_password`,
	`type` 
FROM
	( SELECT * FROM has_authority AS ha WHERE ha.application_type = ? ) AS tmp
	INNER JOIN `user` AS u ON tmp.user_id = u.id;