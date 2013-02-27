-- Test fixture users.  Note how we stay well away from the autogenerated ID space.
insert into serviceuser(id, active, challenge, email, login) values(1000000, true, 'password', 'admin@headmaster.test', 'admin');
insert into userrole(id, rolename, login) values(1000000, 'HEADMASTER', 'admin');

insert into serviceuser(id, active, challenge, email, login) values(1000001, true, 'password-noroles', 'noroles@headmaster.test', 'noroles');

-- Test fixture students.
insert into student(id, firstname, lastname, active, expectedgraduationyear, transferStudent) values(1000000, 'Tim', 'Berners-Lee', true, 2016, true);
insert into student(id, firstname, lastname, active, expectedgraduationyear, transferStudent) values(1000001, 'Vint', 'Cerf', false, 2015, false);
insert into student(id, firstname, lastname, active, expectedgraduationyear, transferStudent) values(1000002, 'Don', 'Knuth', true, 2015, true);
insert into student(id, firstname, lastname, active, expectedgraduationyear, transferStudent) values(1000003, 'Ivan', 'Sutherland', false, 2012, false);
insert into student(id, firstname, lastname, active, expectedgraduationyear, transferStudent) values(1000004, 'Alan', 'Kay', true, 2014, true);

insert into student(id, firstname, lastname, active, cumulativegpa, transferStudent) values(1000005, 'Turd', 'Ferguson', true, 2.0, true);
insert into student(id, firstname, lastname, active, cumulativegpa, transferStudent) values(1000006, 'Trevor', 'McBean', true, 2.5, true);
insert into student(id, firstname, lastname, active, cumulativegpa, transferStudent) values(1000007, 'Nestor', 'Rwende', true, 4.0, false);
insert into student(id, firstname, lastname, active, cumulativegpa, transferStudent) values(1000008, 'Rihanna', 'Streisand', true, 3.9, true);
insert into student(id, firstname, lastname, active, cumulativegpa, transferStudent) values(1000009, 'Kevin', 'Taggart', true, 3.5, true);

insert into major(id, collegeorschool, degree, discipline) values(1000000, 'Engineering', 'BS', 'Computer Science');
insert into major(id, collegeorschool, degree, discipline) values(1000001, 'Science','BA', 'Mathematics');

insert into student_major(student_id, majors_id, majors_order) values(1000002, 1000000, 0);
insert into student_major(student_id, majors_id, majors_order) values(1000002, 1000001, 1);

insert into student_minors(student_id, minors, minors_order) values(1000002, 'Music', 0);

-- Test fixture events.
insert into event(id, datetime, description, title) values(1000000, '2012-07-28 10:31:03', 'The big one', 'Summit');

insert into event_student(event_id, student_id, attendees_order) values(1000000, 1000002, 0);
insert into event_student(event_id, student_id, attendees_order) values(1000000, 1000000, 1);
insert into event_student(event_id, student_id, attendees_order) values(1000000, 1000001, 2);

-- Test fixture grades.
insert into gpa(id, gpa, term, year) values(1000000, 3.5, 0, 2016);
insert into gpa(id, gpa, term, year) values(1000001, 3.8, 2, 2015);
insert into gpa(id, gpa, term, year) values(1000002, 3.9, 2, 2012);
insert into gpa(id, gpa, term, year) values(1000003, 3.7, 2, 2012);
insert into gpa(id, gpa, term, year) values(1000004, 4.0, 2, 2012);

insert into student_gpa(student_id, grades_id) values(1000002, 1000000);
insert into student_gpa(student_id, grades_id) values(1000002, 1000001);
insert into student_gpa(student_id, grades_id) values(1000005, 1000002);
insert into student_gpa(student_id, grades_id) values(1000006, 1000003);
insert into student_gpa(student_id, grades_id) values(1000007, 1000004);

-- Test fixture grants.
insert into researchgrant(id, amount, facultymentor, title, awarded, presented) values(1000000, 10000, 'Leonard Kleinrock', 'The Worldwide Web', 1, true);
insert into researchgrant(id, amount, facultymentor, title, awarded, presented) values(1000001, 10000, 'The Dondi', 'The New DB', 0, false);
insert into researchgrant(id, amount, facultymentor, title, awarded, presented) values(1000002, 10000, 'Alan Turing', 'Binary Good', 2, false);

insert into researchgrant_student(grant_id, student_id, students_order) values(1000000, 1000000, 0);
insert into researchgrant_student(grant_id, student_id, students_order) values(1000000, 1000001, 1);
insert into researchgrant_student(grant_id, student_id, students_order) values(1000001, 1000002, 0);
insert into researchgrant_student(grant_id, student_id, students_order) values(1000001, 1000003, 1);
insert into researchgrant_student(grant_id, student_id, students_order) values(1000002, 1000000, 0);
insert into researchgrant_student(grant_id, student_id, students_order) values(1000002, 1000004, 1);

-- Test fixture for courses
insert into course(id, instructor, classlength, term, year) values(100001, 'Dr. Dondi', 'PT3000S', 1, 2014);
insert into course(id, instructor, term, year) values(100002, 'Prof. X', 2, 2013);
insert into course(id, discipline, term, year) values(100003, 'Computer Science', 1, 2013);

insert into course_classtimes(course_id, classtimes) values(100001, '2013-02-18 11:00:00');
insert into course_classtimes(course_id, classtimes) values(100001, '2013-02-20 11:00:00');
insert into course_classtimes(course_id, classtimes) values(100001, '2013-03-22 11:00:00');

insert into course_student(course_id, student_id) values(100001, 1000000);