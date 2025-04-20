CREATE
    TABLE
        IF NOT EXISTS export_job(
            id BIGSERIAL NOT NULL CONSTRAINT export_job_pk PRIMARY KEY,
            export_form_id VARCHAR(64),
            status VARCHAR(64),
            total_container INTEGER DEFAULT 0,
            total_page INTEGER DEFAULT 0,
            total_exported_container INTEGER DEFAULT 0,
            last_exported_page INTEGER DEFAULT 0,
            filter_container JSON,
            file_output_path VARCHAR(255),
            remark VARCHAR(1000),
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );
