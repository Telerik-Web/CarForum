INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (1, 'Todor', 'Angelov', 'todor@example.com', 'todor', 'pass1', true, false);
INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (2, 'Vladi', 'Venkov', 'vladi@example.com', 'vladi', 'pass2', true, false);
INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (3, 'Petar', 'Raykov', 'pesho@example.com', 'pesho', 'pass3', true, false);

INSERT INTO forum.phone_numbers (phone_number_id, phone_number, user_id)
VALUES (1, '0888 202330', 1);
INSERT INTO forum.phone_numbers (phone_number_id, phone_number, user_id)
VALUES (2, '0888 202430', 2);
INSERT INTO forum.phone_numbers (phone_number_id, phone_number, user_id)
VALUES (3, '0888 202530', 3);

INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (1, 'BMW M2', 'The best M2', 2, '2025-01-27 16:32:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (2, 'BMW M3', 'The best M3', 1, '2025-01-27 16:32:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (3, 'BMW M4', 'The best M4', 3, '2025-01-27 16:32:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (4, 'BMW M5', 'The best M5', 3, '2025-01-27 16:32:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (5, 'BMW M6', 'The best M6', 1, '2025-01-27 16:32:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (6, 'BMW M8', 'The best M8', 2, '2025-01-27 16:32:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (7, 'BMW M1', 'The best for last!', 2, '2025-01-27 16:32:00');

INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (1, 'Mercedes is better', 1, 1);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (2, 'Jaag - by Jeremy', 2, 1);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (3, 'C63 is better', 3, 1);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (4, 'Glue Sniffer', 3, 2);

INSERT INTO forum.likes (like_id, post_id, user_id)
VALUES (1, 2, 1);
INSERT INTO forum.likes (like_id, post_id, user_id)
VALUES (2, 2, 2);
INSERT INTO forum.likes (like_id, post_id, user_id)
VALUES (3, 2, 3);
INSERT INTO forum.likes (like_id, post_id, user_id)
VALUES (4, 3, 1);
INSERT INTO forum.likes (like_id, post_id, user_id)
VALUES (5, 3, 3);