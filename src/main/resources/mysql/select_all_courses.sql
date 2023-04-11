SELECT
    ANY_VALUE ( `teacher_id` ) AS `teacher_id`,
    group_concat( `username` SEPARATOR ', ' ) AS `teachers`,
    ANY_VALUE ( `code` ) AS `code`,
    ANY_VALUE ( `name` ) AS `name`,
    ANY_VALUE ( `credit` ) AS `credit`,
    ANY_VALUE ( `serial_number` ) AS `serial_number`,
    ANY_VALUE ( `online_contact_way` ) AS `online_contact_way`,
    ANY_VALUE ( `comment` ) AS `comment`
FROM
    ( SELECT `teacher_id`, `username`, `course_code` FROM `user` INNER JOIN `teaches` ON `user`.`id` = `teaches`.teacher_id ) AS teacher_name_table
        INNER JOIN course ON course.`code` = `course_code`
GROUP BY
    `course_code`;