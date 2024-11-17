-- insert_data.sql

-- Insert into companies table
INSERT INTO companies (name)
VALUES ('Example Corp'),
       ('Tech Innovations'),
       ('Creative Solutions');

-- Insert into users_standard table
INSERT INTO users_standard (username, role, company_id)
VALUES ('john_doe', 'STANDARD', 1),
       ('jane_admin', 'COMPANY_ADMIN', 1),
       ('admin_super', 'SUPER_USER', 2);

-- Insert into task table
INSERT INTO task (title, description, status, user_id)
VALUES ('Task 1', 'Complete the project report', 'START', 1),
       ('Task 2', 'Review the new proposals', 'IN_PROGRESS', 2),
       ('Task 3', 'Final review of the marketing plan', 'COMPLETE', 1),
       ('Task 4', 'Update the website content', 'IN_PROGRESS', 3);
