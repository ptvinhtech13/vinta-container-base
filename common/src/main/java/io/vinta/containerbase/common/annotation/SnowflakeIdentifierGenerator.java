package io.vinta.containerbase.common.annotation;

import io.vinta.containerbase.common.idgenerator.Snowflake;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public final class SnowflakeIdentifierGenerator implements IdentifierGenerator {

	private static final Snowflake dbSnowflake = new Snowflake();

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return dbSnowflake.nextId();
	}
}
