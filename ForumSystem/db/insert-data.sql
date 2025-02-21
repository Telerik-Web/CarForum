INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (1, 'Todor', 'Angelov', 'todor@example.com', 'todor', 'pass1', true, false);
INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (2, 'Vladi', 'Venkov', 'vladi@example.com', 'vladi', 'pass2', true, false);
INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (3, 'Petar', 'Raykov', 'pesho@example.com', 'pesho', 'pass3', true, false);
INSERT INTO forum.users (user_id, first_name, last_name, email, username, password, isAdmin, isBlocked)
VALUES (4, 'Gosho', 'Goshev', 'gosho@gmail.com', 'gosho', 'gosho123', false, false );

INSERT INTO forum.phone_numbers (phone_number_id, phone_number, user_id)
VALUES (1, '0888 202330', 1);
INSERT INTO forum.phone_numbers (phone_number_id, phone_number, user_id)
VALUES (2, '0888 202430', 2);
INSERT INTO forum.phone_numbers (phone_number_id, phone_number, user_id)
VALUES (3, '0888 202530', 3);

INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (1, 'BMW M3 G80', 'The latest M3 with xDrive and 503 HP.', 1, '2025-01-27 10:00:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (2, 'Audi RS7', 'A blend of luxury and performance.', 2, '2025-01-27 11:30:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (3, 'Mercedes-AMG E63', 'AMG growl with 603 HP.', 3, '2025-01-27 13:45:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (4, 'Porsche 911 GT3', 'Track monster with a naturally aspirated flat-six.', 1, '2025-01-28 08:15:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (5, 'Nissan GT-R R35', 'The legendary Godzilla still holds strong.', 2, '2025-01-28 09:00:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (6, 'Lamborghini Huracán STO', 'Lightweight and track-focused Huracán.', 3, '2025-01-28 10:30:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (7, 'Tesla Model S Plaid', 'Electric power with 1,020 HP.', 1, '2025-01-28 12:00:00');
INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (8, 'Ford Mustang Shelby GT500',
        'The GT500 is an American muscle legend, boasting a supercharged 5.2L V8 producing over 760 HP.
         With aggressive styling, a powerful engine, and track-ready aerodynamics, it stands as one of the
         most iconic Mustangs ever made.',
        2, '2025-01-28 14:00:00');

INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (9, 'Toyota GR Supra',
        'Toyota revived the Supra nameplate with the GR Supra, co-developed with BMW.
         While purists debate its origins, the B58 inline-six delivers thrilling performance,
         and the new manual transmission option adds to the excitement.',
        3, '2025-01-28 15:30:00');

INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (10, 'Chevrolet Corvette C8 Z06',
        'The C8 Z06 is a mid-engine marvel, featuring a naturally aspirated 5.5L flat-plane crank V8
         that revs to 8,600 RPM. It delivers Ferrari-like performance at a fraction of the price,
         making it one of the best American sports cars ever built.',
        1, '2025-01-28 16:45:00');

INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (11, 'McLaren 765LT',
        'McLaren’s track-focused 765LT is an evolution of the 720S, featuring a lighter body,
         enhanced aerodynamics, and 755 HP from its twin-turbo V8. It’s one of the most
         thrilling supercars on the market, with insane acceleration and precise handling.',
        2, '2025-01-28 18:00:00');

INSERT INTO forum.posts (post_id, title, content, user_id, timestamp)
VALUES (12, 'Dodge Challenger SRT Demon 170',
        'The ultimate muscle car. With 1,025 HP running on E85 fuel, the Demon 170 dominates
         the drag strip. Dodge engineered it to be the fastest accelerating production car,
         with a 0-60 MPH time under 2 seconds!',
        3, '2025-01-28 19:30:00');


INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (1, 'BMW always wins in handling.', 1, 2);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (2, 'RS7 looks mean but is heavy.', 2, 1);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (3, 'AMG E63 has the best sound!', 3, 2);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (4, '911 GT3 is perfect for the track.', 4, 3);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (5, 'R35 still holds up after all these years.', 5, 1);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (6, 'STO is too expensive for what it is.', 6, 2);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (7, 'Plaid destroys anything in a straight line!', 7, 3);
INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (8, 'The Shelby GT500 is an absolute beast! The combination of raw power,
             aggressive styling, and a proper dual-clutch transmission makes it a true track weapon.
             However, it’s still quite heavy compared to European rivals.',
        8, 1);

INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (9, 'The GR Supra is a great modern revival, but I wish Toyota had
             done it without BMW’s help. Still, the B58 engine is solid, and the
             aftermarket potential is limitless.',
        9, 2);

INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (10, 'The C8 Z06 is basically a budget Ferrari 458! The naturally aspirated
              5.5L V8 sounds incredible, and the mid-engine layout makes a huge difference
              in handling. Chevy nailed it with this one.',
        10, 3);

INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (11, 'The 765LT is my dream car. Insane power, lightweight design, and
              McLaren’s signature aerodynamics. The only downside? Reliability
              issues and maintenance costs!',
        11, 1);

INSERT INTO forum.comments (comment_id, content, post_id, user_id)
VALUES (12, 'Dodge really went crazy with the Demon 170. Over 1,000 HP from the factory
              is absolutely wild! Can’t wait to see these dominate drag strips.',
        12, 2);


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