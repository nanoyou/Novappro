SELECT
    `teacher_id`,
    group_concat( `username` SEPARATOR ', ' ) AS `teachers`,
    `code`,
    `name`,
    `credit`,
    `serial_number`,
    `online_contact_way`,
    `comment`
FROM
    ( SELECT `teacher_id`, `username`, `course_code` FROM `user` INNER JOIN `teaches` ON `user`.`id` = `teaches`.teacher_id ) AS teacher_name_table
        INNER JOIN course ON course.`code` = `course_code`
GROUP BY
    `course_code`;