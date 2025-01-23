INSERT INTO users (first_name, last_name, email, username, password, is_admin)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'johndoe', 'password123', FALSE),
    ('Jane', 'Smith', 'jane.smith@example.com', 'janesmith', 'securepass', FALSE),
    ('Admin', 'User', 'admin.user@example.com', 'adminuser', 'adminpass', TRUE),
    ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicejohnson', 'alice123', FALSE),
    ('Bob', 'Williams', 'bob.williams@example.com', 'bobwilliams', 'bobpass', FALSE);


INSERT INTO admins (id, phone_number)
VALUES
    (3, '123-456-7890');

INSERT INTO posts (user_id, title, content, likes_count)
VALUES
    (1, 'Welcome to My Blog', 'This is the first post on my blog. I hope you enjoy it!', 5),
    (2, 'My Travel Adventures', 'I recently visited Japan, and it was amazing! Here’s what I saw...', 12),
    (4, 'Coding Tips for Beginners', 'Here are some essential tips for anyone starting with programming.', 8),
    (5, 'Best Books I Read This Year', 'In this post, I’ll share my favorite books from the past year.', 3);

INSERT INTO comments (post_id, user_id, content)
VALUES
    (1, 2, 'Great first post, John! Keep it up.'),
    (2, 1, 'Japan is on my bucket list! Thanks for sharing your experience.'),
    (2, 4, 'I love Japan too! Did you try the food there?'),
    (3, 5, 'These tips are super helpful. Thanks, Alice!'),
    (4, 1, 'I’m adding these books to my reading list. Appreciate the recommendations!');

INSERT INTO likes (post_id, user_id)
VALUES
    (1, 2),
    (1, 4),
    (2, 1),
    (2, 4),
    (2, 5),
    (3, 1),
    (3, 2),
    (3, 5),
    (4, 2),
    (4, 4);