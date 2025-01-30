INSERT INTO users (first_name, last_name, email, username, password, isAdmin, isBlocked) VALUES
 ('John', 'Doe', 'john.doe@example.com', 'johndoe', 'password123', 1, 0),
 ('Jane', 'Smith', 'jane.smith@example.com', 'janesmith', 'password456', 0, 0),
 ('Michael', 'Brown', 'michael.brown@example.com', 'mikebrown', 'securepass', 0, 0);

INSERT INTO phone_numbers (phone_number, user_id) VALUES
('0885783123', 1);

INSERT INTO posts (title, content, user_id, timestamp) VALUES
('Top 5 Cars of 2025', 'Let\'s discuss the best cars of 2025. What are your top picks?', 1, CURRENT_TIMESTAMP),
('Electric Cars vs Gasoline Cars', 'Do you think electric cars will completely replace gasoline cars?', 2, CURRENT_TIMESTAMP),
('Tips for Maintaining Your Car', 'Here are some tips to keep your car in top shape.', 3, CURRENT_TIMESTAMP);


INSERT INTO comments (content, post_id, user_id) VALUES
('I think the Tesla Model S is still the best!', 1, 2),
('Electric cars are the future, but we still need better infrastructure.', 2, 3),
('Great tips! I also recommend checking tire pressure regularly.', 3, 1);


INSERT INTO likes (like_id, post_id, user_id) VALUES
(1, 1, 2),
(2, 1, 3),
(3, 2, 1),
(4, 3, 2);