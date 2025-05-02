CREATE
    TABLE
        IF NOT EXISTS users(
            id BIGSERIAL NOT NULL CONSTRAINT users_pk PRIMARY KEY,
            user_type VARCHAR(50) NOT NULL,
            user_status VARCHAR(50) NOT NULL,
            email VARCHAR(60) NOT NULL,
            phone_number VARCHAR(60),
            full_name VARCHAR(128),
            avatar_path VARCHAR(256),
            created_by BIGINT,
            updated_by BIGINT,
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW()
        );

CREATE
    TABLE
        IF NOT EXISTS user_roles(
            user_id BIGINT NOT NULL,
            tenant_id BIGINT NOT NULL,
            role_id BIGINT NOT NULL,
            unique_key VARCHAR(128),
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW(),
            CONSTRAINT user_roles_pk PRIMARY KEY(
                user_id,
                tenant_id
            ),
            CONSTRAINT user_roles_user_fk FOREIGN KEY(user_id) REFERENCES users(id)
        );

CREATE
    INDEX IF NOT EXISTS user_roles_role_id_idx ON
    user_roles(role_id);

CREATE
    INDEX IF NOT EXISTS user_roles_unique_key_idx ON
    user_roles(unique_key);

CREATE
    TABLE
        IF NOT EXISTS user_access(
            user_id BIGINT NOT NULL,
            access_type VARCHAR(50) NOT NULL,
            access_data JSON NOT NULL,
            created_at TIMESTAMP(6) DEFAULT NOW(),
            updated_at TIMESTAMP(6) DEFAULT NOW(),
            CONSTRAINT user_access_pk PRIMARY KEY(user_id),
            CONSTRAINT user_access_user_fk FOREIGN KEY(user_id) REFERENCES users(id)
        );
