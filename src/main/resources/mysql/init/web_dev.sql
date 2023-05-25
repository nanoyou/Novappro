/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : web_dev

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 25/05/2023 11:36:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for approval_authority
-- ----------------------------
DROP TABLE IF EXISTS `approval_authority`;
CREATE TABLE `approval_authority`  (
                                       `user_id` int NOT NULL,
                                       `appro_weight` int NOT NULL,
                                       `course_code` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                       PRIMARY KEY (`user_id`, `course_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of approval_authority
-- ----------------------------
INSERT INTO `approval_authority` VALUES (20210004, 100, 'A0801051040');
INSERT INTO `approval_authority` VALUES (20210004, 100, 'A0801053120');
INSERT INTO `approval_authority` VALUES (20210005, 80, 'A0801051040');
INSERT INTO `approval_authority` VALUES (20210005, 80, 'A0801053120');
INSERT INTO `approval_authority` VALUES (20210006, 100, 'A0801040060');
INSERT INTO `approval_authority` VALUES (20210007, 100, 'A0801040070');
INSERT INTO `approval_authority` VALUES (20210010, 100, 'A0801020010');
INSERT INTO `approval_authority` VALUES (20210011, 80, 'A0801020010');
INSERT INTO `approval_authority` VALUES (20210012, 60, 'A0801020010');
INSERT INTO `approval_authority` VALUES (20210013, 40, 'A0801020010');
INSERT INTO `approval_authority` VALUES (20210014, 100, 'A0801050210');

-- ----------------------------
-- Table structure for approval_flow
-- ----------------------------
DROP TABLE IF EXISTS `approval_flow`;
CREATE TABLE `approval_flow`  (
                                  `flow_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `bus_type` enum('LINEAR') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `add_user_id` int NOT NULL,
                                  `add_time` datetime NOT NULL,
                                  `appro_status` enum('LECTURE_TEACHER_EXAMING','SUPERVISOR_TEACHER_EXAMING','REJECTED','APPROVED','SUBMITTED','FINISHED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  PRIMARY KEY (`flow_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of approval_flow
-- ----------------------------

-- ----------------------------
-- Table structure for audit_flow_detail
-- ----------------------------
DROP TABLE IF EXISTS `audit_flow_detail`;
CREATE TABLE `audit_flow_detail`  (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `flow_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      `audit_user_id` int NOT NULL,
                                      `audit_remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `audit_time` datetime NOT NULL,
                                      `audit_status` enum('APPROVED','REJECTED','SUBMITTED','LECTURE_TEACHER_EXAMING','SUPERVISOR_TEACHER_EXAMING') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 292 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of audit_flow_detail
-- ----------------------------

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
                           `code` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `credit` decimal(5, 2) NOT NULL,
                           `serial_number` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `online_contact_way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                           `comment` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                           PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('A0801020010', '软件创新方法与实例', 2.00, 'A061730', '', '');
INSERT INTO `course` VALUES ('A0801040060', '操作系统', 2.25, 'A061733', '', '');
INSERT INTO `course` VALUES ('A0801040070', '计算机网络', 2.50, 'A061735', '', '大课排课');
INSERT INTO `course` VALUES ('A0801050210', '人机交互的软件工程方法', 1.75, 'A061744', '', '大课排课');
INSERT INTO `course` VALUES ('A0801051040', 'Web开发技术', 2.75, 'A057483', 'QQ:809119512', '');
INSERT INTO `course` VALUES ('A0801053120', 'Android开发技术', 2.00, 'A057510', 'QQ:784042380', '');

-- ----------------------------
-- Table structure for courses_application
-- ----------------------------
DROP TABLE IF EXISTS `courses_application`;
CREATE TABLE `courses_application`  (
                                        `flow_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        `add_user_id` int NOT NULL,
                                        `add_time` datetime NOT NULL,
                                        `appro_courses` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        PRIMARY KEY (`flow_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of courses_application
-- ----------------------------

-- ----------------------------
-- Table structure for teaches
-- ----------------------------
DROP TABLE IF EXISTS `teaches`;
CREATE TABLE `teaches`  (
                            `course_code` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `teacher_id` int NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teaches
-- ----------------------------
INSERT INTO `teaches` VALUES ('A0801051040', 20210004);
INSERT INTO `teaches` VALUES ('A0801051040', 20210005);
INSERT INTO `teaches` VALUES ('A0801040060', 20210006);
INSERT INTO `teaches` VALUES ('A0801040070', 20210007);
INSERT INTO `teaches` VALUES ('A0801053120', 20210004);
INSERT INTO `teaches` VALUES ('A0801053120', 20210005);
INSERT INTO `teaches` VALUES ('A0801020010', 20210010);
INSERT INTO `teaches` VALUES ('A0801020010', 20210011);
INSERT INTO `teaches` VALUES ('A0801020010', 20210012);
INSERT INTO `teaches` VALUES ('A0801020010', 20210013);
INSERT INTO `teaches` VALUES ('A0801050210', 20210014);

-- ----------------------------
-- Table structure for upload_file
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file`  (
                                `flow_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                `user_id` int NOT NULL,
                                `file_name` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                PRIMARY KEY (`flow_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of upload_file
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `raw_password` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `type` enum('ADMIN','STUDENT','LECTURE_TEACHER','SUPERVISOR_TEACHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20210037 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (20210000, '张三', 'sdhj1892!?ss', 'STUDENT');
INSERT INTO `user` VALUES (20210001, '李四', 'xioedh28sd!', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210002, '王五', '1234567890', 'STUDENT');
INSERT INTO `user` VALUES (20210003, '张暄郝', '12334sd!!sad', 'STUDENT');
INSERT INTO `user` VALUES (20210004, '于鲲鹏', '73gd98qw!', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210005, '姜琳颖', 'shfd8901!!', 'SUPERVISOR_TEACHER');
INSERT INTO `user` VALUES (20210006, '韩春燕', 'asd89881!!', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210007, '李昕', '9hf8289fj!?', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210008, '安兑兑', '23few2!36f', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210009, '你麻痹', '123456qwe!', 'STUDENT');
INSERT INTO `user` VALUES (20210010, '王蓓蕾', '3shf892fjl!?', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210011, '郭朝鹏', 'shv939jcow!', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210012, '赵相国', '8vf9fjw9d0!?', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210013, '宋杰', 'vj9d0wosd!!', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210014, '赵建喆', '9fh29djs0g!?', 'LECTURE_TEACHER');
INSERT INTO `user` VALUES (20210015, '孟氏博', '123456qwe!', 'STUDENT');
INSERT INTO `user` VALUES (20210016, '大好人', '28fhjasdu88?', 'STUDENT');
INSERT INTO `user` VALUES (20210017, '叫什么', '123456qwe!', 'STUDENT');
INSERT INTO `user` VALUES (20210018, '妮教啥', '2387dhodi!', 'ADMIN');
INSERT INTO `user` VALUES (20210019, '胡一同', '123456asd!', 'SUPERVISOR_TEACHER');
INSERT INTO `user` VALUES (20210020, '沃慧娇', '2387cyuw!!?', 'SUPERVISOR_TEACHER');
INSERT INTO `user` VALUES (20210035, '针不错', 'sdjkf83udnsdj!', 'ADMIN');
INSERT INTO `user` VALUES (20210036, '针不好', 'sdjkf83udnsdj!', 'ADMIN');

SET FOREIGN_KEY_CHECKS = 1;
