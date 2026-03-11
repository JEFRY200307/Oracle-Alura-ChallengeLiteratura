CREATE TABLE IF NOT EXISTS authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    birth_year INTEGER,
    death_year INTEGER
);

CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    language VARCHAR(10) NOT NULL,
    download_count INTEGER,
    author_id BIGINT NOT NULL REFERENCES authors(id)
);

