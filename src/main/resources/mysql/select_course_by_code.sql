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
    (
        SELECT
            *
        FROM
            ( SELECT * FROM course AS c WHERE c.`code` = ? ) AS tmp
                INNER JOIN teaches AS tc ON tmp.`code` = tc.course_code
    ) AS course_tmp
        INNER JOIN `user` AS u ON u.id = course_tmp.`teacher_id`
GROUP BY
    `code`;