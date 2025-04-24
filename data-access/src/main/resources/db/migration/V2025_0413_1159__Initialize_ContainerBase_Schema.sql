CREATE
    TABLE
        IF NOT EXISTS import_jobs(
            id VARCHAR(64) NOT NULL CONSTRAINT import_jobs_pk PRIMARY KEY,
            status VARCHAR(50) NOT NULL,
            file_form_id VARCHAR(64) NOT NULL,
            uploaded_file_path VARCHAR(300) NOT NULL,
            actual_schema JSON,
            metrics JSON,
            consolidated_error_messages TEXT,
            remark TEXT,
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );

CREATE
    TABLE
        IF NOT EXISTS import_records(
            id BIGSERIAL NOT NULL CONSTRAINT import_records_pk PRIMARY KEY,
            import_job_id VARCHAR(64) NOT NULL,
            record_index INTEGER NOT NULL,
            record_type VARCHAR(50) NOT NULL,
            record_status VARCHAR(50) NOT NULL,
            error_message TEXT,
            stacktrace TEXT,
            data_values JSON,
            raw_data_values TEXT,
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );

CREATE
    TABLE
        IF NOT EXISTS containers(
            id BIGSERIAL NOT NULL CONSTRAINT containers_pk PRIMARY KEY,
            import_iob_id VARCHAR(64),
            container_number VARCHAR(64),
            iso_equipment_code VARCHAR(64),
            equipment_reference VARCHAR(128),
            transport_equipment_type VARCHAR(64),
            tare_weight_kg INTEGER DEFAULT 0,
            max_gross_weight_kg INTEGER DEFAULT 0,
            payload_weight_kg INTEGER DEFAULT 0,
            is_reefer BOOLEAN DEFAULT FALSE,
            state VARCHAR(64),
            damage_description VARCHAR(500),
            booking_reference VARCHAR(255),
            shipment_reference VARCHAR(255),
            contents_description VARCHAR(500),
            seal_number VARCHAR(128),
            seal_source VARCHAR(64),
            owner_shipping_line_code VARCHAR(64),
            owner_shipping_scac VARCHAR(64),
            owner_name VARCHAR(128),
            owner_address VARCHAR(128),
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );

CREATE
    INDEX IF NOT EXISTS containers_import_iob_id_idx ON
    containers(import_iob_id);

CREATE
    INDEX IF NOT EXISTS containers_container_number_idx ON
    containers(container_number);
