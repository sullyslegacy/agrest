package com.nhl.link.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.EJBQLQuery;
import org.apache.cayenne.query.SQLSelect;
import org.apache.cayenne.query.SQLTemplate;
import org.junit.Before;
import org.junit.Test;

import com.nhl.link.rest.unit.JerseyTestOnDerby;
import com.nhl.link.rest.unit.cayenne.E2;
import com.nhl.link.rest.unit.cayenne.E3;

public class LinkRestService_InContainer_DELETE_Related_Test extends JerseyTestOnDerby {

	private ObjectContext context;

	@Before
	public void before() {

		context = runtime.newContext();

		context.performGenericQuery(new EJBQLQuery("delete from E4"));
		context.performGenericQuery(new EJBQLQuery("delete from E3"));
		context.performGenericQuery(new EJBQLQuery("delete from E2"));
		context.performGenericQuery(new EJBQLQuery("delete from E5"));
		context.performGenericQuery(new EJBQLQuery("delete from E6"));
		context.performGenericQuery(new EJBQLQuery("delete from E7"));
		context.performGenericQuery(new EJBQLQuery("delete from E9"));
		context.performGenericQuery(new EJBQLQuery("delete from E8"));
	}

	@Test
	public void testDelete_All_ToMany() {

		// make sure we have e3s for more than one e2 - this will help us
		// confirm that relationship queries are properly filtered.

		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e8 (id, name) values (1, 'xxx')"));
		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e8 (id, name) values (2, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e7 (id, e8_id, name) values (7, 2, 'zzz')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e7 (id, e8_id, name) values (8, 1, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e7 (id, e8_id, name) values (9, 1, 'zzz')"));

		assertEquals(Integer.valueOf(2),
				SQLSelect.scalarQuery(Integer.class, "SELECT COUNT(1) FROM utest.e7 WHERE e8_id = 1")
						.selectOne(context));
		Response r1 = target("/lr/related/e8/1/e7s").request().delete();

		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		assertEquals("{\"success\":true}", r1.readEntity(String.class));

		assertEquals(Integer.valueOf(0),
				SQLSelect.scalarQuery(Integer.class, "SELECT COUNT(1) FROM utest.e7 WHERE e8_id = 1")
						.selectOne(context));
	}

	@Test
	public void testDelete_ValidRel_ToMany() {

		// make sure we have e3s for more than one e2 - this will help us
		// confirm that relationship queries are properly filtered.

		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e2 (id, name) values (1, 'xxx')"));
		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e2 (id, name) values (2, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (7, 2, 'zzz')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (8, 1, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (9, 1, 'zzz')"));

		assertEquals(1, SQLSelect.scalarQuery(E3.class, "SELECT e2_id FROM utest.e3 WHERE id = 9").selectOne(context));

		Response r1 = target("/lr/related/e2/1/e3s/9").request().delete();

		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		assertEquals("{\"success\":true}", r1.readEntity(String.class));

		assertEquals(null, SQLSelect.scalarQuery(E3.class, "SELECT e2_id FROM utest.e3 WHERE id = 9")
				.selectOne(context));
	}

	@Test
	public void testDelete_ValidRel_ToOne() {

		// make sure we have e3s for more than one e2 - this will help us
		// confirm that relationship queries are properly filtered.

		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e2 (id, name) values (1, 'xxx')"));
		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e2 (id, name) values (2, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (7, 2, 'zzz')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (8, 1, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (9, 1, 'zzz')"));

		assertEquals(1, SQLSelect.scalarQuery(E3.class, "SELECT e2_id FROM utest.e3 WHERE id = 9").selectOne(context));

		Response r1 = target("/lr/related/e3/9/e2/1").request().delete();

		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		assertEquals("{\"success\":true}", r1.readEntity(String.class));

		assertEquals(null, SQLSelect.scalarQuery(E3.class, "SELECT e2_id FROM utest.e3 WHERE id = 9")
				.selectOne(context));
	}

	@Test
	public void testDelete_ValidRel_ToOne_All() {

		// make sure we have e3s for more than one e2 - this will help us
		// confirm that relationship queries are properly filtered.

		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e2 (id, name) values (1, 'xxx')"));
		context.performGenericQuery(new SQLTemplate(E2.class, "INSERT INTO utest.e2 (id, name) values (2, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (7, 2, 'zzz')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (8, 1, 'yyy')"));
		context.performGenericQuery(new SQLTemplate(E3.class,
				"INSERT INTO utest.e3 (id, e2_id, name) values (9, 1, 'zzz')"));

		assertEquals(1, SQLSelect.scalarQuery(E3.class, "SELECT e2_id FROM utest.e3 WHERE id = 9").selectOne(context));

		Response r1 = target("/lr/related/e3/9/e2").request().delete();

		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		assertEquals("{\"success\":true}", r1.readEntity(String.class));

		assertEquals(null, SQLSelect.scalarQuery(E3.class, "SELECT e2_id FROM utest.e3 WHERE id = 9")
				.selectOne(context));
	}

	@Test
	public void testDelete_InvalidRel() {
		Response r1 = target("/lr/related/e2/1/dummyRel/9").request().delete();

		assertEquals(Status.BAD_REQUEST.getStatusCode(), r1.getStatus());
		assertEquals("{\"success\":false,\"message\":\"Invalid relationship: 'dummyRel'\"}",
				r1.readEntity(String.class));
	}

	@Test
	public void testDelete_NoSuchId_Source() {
		Response r1 = target("/lr/related/e2/22/e3s/9").request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), r1.getStatus());

		String responseEntity = r1.readEntity(String.class).replaceFirst("\\'[\\d]+\\'", "''");
		assertEquals("{\"success\":false,\"message\":\"No object for ID '' and entity 'E2'\"}", responseEntity);
	}
}
