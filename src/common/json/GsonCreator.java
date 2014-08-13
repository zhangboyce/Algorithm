package common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonCreator {
	public static Gson createGson(GsonBuilderAttributeSetter gsonBuilderAttributeSetter) {
		GsonBuilder builder = new GsonBuilder();
		gsonBuilderAttributeSetter.setAttribute(builder);
		Gson gson = builder.create();
		
		return gson;
	}
	
	public static Gson createGson() {
		return createGson(new DefaultGsonBuilderAttributeSetter());
	}
	
	public static interface GsonBuilderAttributeSetter {
		public void setAttribute(GsonBuilder gsonBuilder);
	}
	
	public static class DefaultGsonBuilderAttributeSetter
												implements GsonBuilderAttributeSetter {
		public void setAttribute(GsonBuilder gsonBuilder) {
			if (null == gsonBuilder)
				return;
			gsonBuilder.setDateFormat("yyyy-MM-dd");
		}
	}
}
