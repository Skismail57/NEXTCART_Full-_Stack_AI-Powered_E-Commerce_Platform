USE nextcart_db;

-- Insert Admin User (role_id=2 for ADMIN)
INSERT INTO users (role_id, first_name, last_name, email, mobile, password, status, email_verified, mobile_verified) VALUES
(2, 'Ismail', 'Shaikh', 'shaikhismail57@example.com', '9876543211', '$2a$12$rSjX5tKmVccsuKsykQTS3uwligTkSdde0f/xXe/bygt55vmZLN.z6', 'ACTIVE', TRUE, TRUE);

-- Insert 7 Customer Users (role_id=1 for CUSTOMER)
INSERT INTO users (role_id, first_name, last_name, email, mobile, password, status, email_verified, mobile_verified) VALUES
(1, 'John', 'Doe', 'john.doe@example.com', '9876543212', '$2a$12$0Xr6jlxbxQAg1H/1WgDrrurbZD4800gCzqecKiQeMaGFT.QK0zmwK', 'ACTIVE', TRUE, TRUE),
(1, 'Jane', 'Smith', 'jane.smith@example.com', '9876543213', '$2a$12$ilu3LC.d2mJO3o5h3ZQTV.dGQJVkJVmIExVWQGhvN3P8tD5wHR4qC', 'ACTIVE', TRUE, TRUE),
(1, 'Bob', 'Johnson', 'bob.johnson@example.com', '9876543214', '$2a$12$AKq8wC0IMTEy9LKG0mmP2OzbcmtiyZCGuH9dd1hOBgz5I7o4V3Ltu', 'ACTIVE', TRUE, TRUE),
(1, 'Alice', 'Williams', 'alice.williams@example.com', '9876543215', '$2a$12$hNkVZkfwzGhKe7XSe3QH6.Hs/2C3Sd42fL0h60IO7FbiDenfV0qga', 'ACTIVE', TRUE, TRUE),
(1, 'Charlie', 'Brown', 'charlie.brown@example.com', '9876543216', '$2a$12$DUnNhiXFspEDvp800wbcaut7.fEHydjNdqIwDS93rAMWEKZ8mB5qe', 'ACTIVE', TRUE, TRUE),
(1, 'Diana', 'Prince', 'diana.prince@example.com', '9876543217', '$2a$12$jq4zU5dz8mZP2tiBGIp.ZeKCTVcCoC.kZ2mVl7CpqjyXu3kNFULJ2', 'ACTIVE', TRUE, TRUE),
(1, 'Ethan', 'Hunt', 'ethan.hunt@example.com', '9876543218', '$2a$12$tV/ZCCaymM7yBMlRkn1TA.WmXIX/KMcLDDdsFrYIpVPQKBMyVZHgK', 'ACTIVE', TRUE, TRUE);
