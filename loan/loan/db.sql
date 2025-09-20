INSERT INTO users (username, password, role) VALUES
('admin1', 'admin123', 'ADMIN'),
('customer1', 'cust123', 'CUSTOMER'),
('customer2', 'cust456', 'CUSTOMER');


select * from users;

INSERT INTO loan_applications (id, user_id, amount, term_in_months, status, application_date, purpose) VALUES
(1, 2, 5000.00, 12, 'PENDING', '2025-05-01', 'Home Renovation'),
(2, 2, 15000.00, 24, 'APPROVED', '2025-04-15', 'Car Purchase'),
(3, 3, 7000.00, 18, 'REJECTED', '2025-04-20', 'Medical Expenses');

select * from loan_applications;


select * from repayments;
