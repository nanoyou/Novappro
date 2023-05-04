START TRANSACTION;
DELETE FROM approval_flow;
DELETE FROM audit_flow_detail;
DELETE FROM courses_application;
DELETE FROM upload_file;
COMMIT;