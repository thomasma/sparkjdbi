package com.tryout.routes;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

import com.google.gson.Gson;
import com.tryout.RouteRegistrar;
import com.tryout.db.DB;
import com.tryout.domain.Note;

public class NotesRoutes implements RouteRegistrar {
	public void routes() {
		get("/note/:id", (request, response) -> {
			Note note = DB.dbi().withHandle(new HandleCallback<Note>() {
				public Note withHandle(Handle handle) throws Exception {
					Map<String, Object> rs = handle
							.createQuery("select * from note where id = :id")
							.bind("id", new Long(request.params(":id")))
							.first();
					Note note = null;

					if (rs != null && rs.size() > 1) {
						note = new Note();
						note.setId(((Integer) rs.get("id")).longValue());
						note.setNote((String) rs.get("note"));
					}
					return note;
				}
			});

			if (note != null) {
				return note;
			}

			return new AppError("Note not found");
		}, new JsonTransformer());

		get("/note", (request, response) -> {
			return DB.dbi().withHandle(new HandleCallback<List<Note>>() {
				public List<Note> withHandle(Handle handle) throws Exception {
					List<Note> list = new ArrayList<Note>();
					List<Map<String, Object>> rs = handle.createQuery(
							"select * from note").list();
					if (rs != null && rs.size() > 1) {
						for (Map<String, Object> record : rs) {
							Note note = new Note();
							note.setId(((Integer) record.get("id")).longValue());
							note.setNote((String) record.get("note"));
							list.add(note);
						}
					}
					return list;
				}
			});
		}, new JsonTransformer());

		post("/note",
				(request, response) -> {
					Gson gson = new Gson();
					final Note note = gson.fromJson(request.body(), Note.class);
					System.out.println(note);

					// add into database
					Note insertedNote = DB.dbi().withHandle(
							new HandleCallback<Note>() {
								public Note withHandle(Handle handle)
										throws Exception {
									long id = DB.nextId();
									handle.createStatement(
											"insert into note (id, note) values (:id, :note)")
											.bind("id", DB.nextId())
											.bind("note", note.getNote())
											.execute();
									note.setId(id);
									return note;
								}
							});

					// return response
					response.status(200);
					return insertedNote;
				});

	}
}
