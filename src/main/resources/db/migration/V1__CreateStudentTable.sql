CREATE TABLE If NOT EXISTS student (
    student_id UUID PRIMARY KEY NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    gender VARCHAR(6) NOT NULL CHECK(
                                    gender= 'MALE'   OR
                                    gender= 'FEMALE' OR
                                    gender= 'male'   OR
                                    gender= 'female' OR
                                    gender= 'F' OR
                                    gender= 'M'
                                    )
);