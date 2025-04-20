CREATE
    TABLE
        IF NOT EXISTS shedlock(
            name VARCHAR(64),
            lock_until TIMESTAMP(3) WITH TIME ZONE,
            locked_at TIMESTAMP(3) WITH TIME ZONE,
            locked_by VARCHAR(255),
            PRIMARY KEY(name)
        )