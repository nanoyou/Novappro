-- 用于清空以下的表
-- 系统将恢复到没有进行注册的状态

START TRANSACTION;
DELETE FROM approval_flow;
DELETE FROM audit_flow_detail;
DELETE FROM courses_application;
DELETE FROM upload_file;
COMMIT;