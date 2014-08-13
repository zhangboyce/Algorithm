package common.json;


/**
 * 
 * @author boyce
 * @version 2014-3-20
 */
public final class JsonBuilderExecutor implements JsonBuilder {
	
	private JsonBuilder jsonBuilder = Gson4JsonBuilder.getInstance();
	private JsonBuilderExecutor() {}
	
	public String toJson(Object object) {
		return jsonBuilder.toJson(object);
	}
	
	public <T> T toObject(String json, Class<T> clazz) {
		return jsonBuilder.toObject(json, clazz);
	}
	
	private static class JsonExecutorGenerateor {
		private final static JsonBuilder JSON_BUILDER = new JsonBuilderExecutor();
	}
	
	public static JsonBuilder getInstance() {
		return JsonExecutorGenerateor.JSON_BUILDER;
	}
}
