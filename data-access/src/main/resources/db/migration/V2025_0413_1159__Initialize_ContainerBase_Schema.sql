CREATE
    TABLE
        IF NOT EXISTS import_job(
            id VARCHAR(64) NOT NULL CONSTRAINT import_job_pk PRIMARY KEY,
            status VARCHAR(50) NOT NULL,
            sources JSON NOT NULL,
            remark VARCHAR(1000),
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );