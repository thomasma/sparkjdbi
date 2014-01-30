package com.tryout.db;

import java.util.Map;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

public final class DB {
	private static DB thisObject;

	private DataSource dataSouce;

	private DBI dbi;

	private DB() {
		this.dataSouce = JdbcConnectionPool.create("jdbc:h2:mem:test",
				"username", "password");
		this.dbi = new DBI(dataSouce);
		thisObject = this;
	}

	public static void initialize() {
		new DB();

		// create test table for development...
		Handle h = dbi().open();
		h.execute("create table note (id int primary key, note varchar(299))");
		h.execute("create sequence idseq;");
		h.close();

	}

	public static Long nextId() {
		return dbi().withHandle(new HandleCallback<Long>() {
			public Long withHandle(Handle handle) throws Exception {
				Map<String, Object> rs = handle.createQuery(
						"SELECT idseq.nextval AS id FROM dual").first();
				return (Long) rs.get("id");
			}
		});
	}

	public static DBI dbi() {
		return thisObject.dbi;
	}
}
