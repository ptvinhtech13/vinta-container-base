CREATE
    TABLE
        IF NOT EXISTS dashboards(
            id BIGSERIAL NOT NULL CONSTRAINT dashboard_pk PRIMARY KEY,
            dashboard_type VARCHAR(50) NOT NULL, -- DASHBOARD & REPORT
            name VARCHAR(256) NOT NULL,
            description VARCHAR(512),
            status VARCHAR(50) NOT NULL,
            metabase_id BIGINT,
            access_policy JSONB NOT NULL DEFAULT '{}',
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );

CREATE
    INDEX idx_dashboard_access_policy ON
    dashboards
        USING GIN(
        access_policy jsonb_path_ops
    );