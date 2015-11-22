package com.example.zhaorui.dvdcollector.es.data;

public class SimpleSearchCommand {

	private SimpleSearchQuery query;
		
	public SimpleSearchCommand(String query) {
		super();
		this.query = new SimpleSearchQuery(query);
	}

	public SimpleSearchCommand(String query, String[] fields) {
		super();
		throw new UnsupportedOperationException("Fields not yet implemented.");
	}

	public SimpleSearchQuery getQuery() {
		return query;
	}

	public void setQuery(SimpleSearchQuery query) {
		this.query = query;
	}

	static class SimpleSearchQuery {
		private SimpleSearchQueryString queryString;
		
		public SimpleSearchQuery(String query) {
			super();
			this.queryString = new SimpleSearchQueryString(query);
		}

		public SimpleSearchQueryString getQueryString() {
			return queryString;
		}

		public void setQueryString(SimpleSearchQueryString queryString) {
			this.queryString = queryString;
		}

		static class SimpleSearchQueryString {
			private String query;
			
			public SimpleSearchQueryString(String query) {
				super();
				this.query = query;
			}

			public String getQuery() {
				return query;
			}

			public void setQuery(String query) {
				this.query = query;
			}
		}
	}
	/*
	private String query;
	private String[] fields;

	public SimpleSearchCommand(String query) {
		this(query, null);
	}

	public SimpleSearchCommand(String query, String[] fields) {
		this.query = query;
		this.fields = fields;
	}

	public String getJsonCommand() {
		StringBuffer command = new StringBuffer(
				"{\"query\" : {\"query_string\" : {\"query\" : \"" + query
						+ "\"");

		if (fields != null) {
			command.append(", \"fields\":  [");

			for (int i = 0; i < fields.length; i++) {
				command.append("\"" + fields[i] + "\", ");
			}
			command.delete(command.length() - 2, command.length());

			command.append("]");
		}

		command.append("}}}");

		return command.toString();
	}*/
}
