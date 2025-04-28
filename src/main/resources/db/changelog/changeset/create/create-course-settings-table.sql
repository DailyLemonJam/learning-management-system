CREATE TABLE course_settings (
    id UUID PRIMARY KEY NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    is_public BOOLEAN NOT NULL
);
