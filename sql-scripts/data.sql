INSERT INTO tour (name, location, recommended_months, description, booking_count, view_count)
VALUES ('Mountain Trek', 'Rocky Mountains', 6, 'Enjoy a scenic trek through the Rocky Mountains.', 100, 500),
       ('Beach Retreat', 'Hawaii', 12, 'Relax on the beautiful beaches of Hawaii.', 50, 1000),
       ('City Tour', 'New York City', 4, 'Explore the bustling streets of NYC.', 200, 800);

INSERT INTO review (tour_id, date, author, text)
VALUES (1, '2023-08-15', 'John Doe', 'Amazing experience, breathtaking views!'),
       (1, '2023-09-20', 'Alice Smith', 'Highly recommend this tour!'),
       (2, '2023-07-10', 'Emily Jones', 'Had a fantastic time at the beach retreat.'),
       (3, '2023-10-05', 'Michael Brown', 'Great tour, lots to see and do.');

INSERT INTO booking (tour_id, date, phone_number, number_of_participants, comment)
VALUES (1, '2024-06-01', '123-456-7890', 2, 'Excited for the trek!'),
       (2, '2024-08-20', '987-654-3210', 4, 'Looking forward to relaxing on the beach.'),
       (3, '2024-05-10', '555-555-5555', 3, 'Can''t wait to explore NYC.');
