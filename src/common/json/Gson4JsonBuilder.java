package common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * google gson实现
 * @author boyce
 * @version 2014-3-20
 */
public class Gson4JsonBuilder implements JsonBuilder {
	
	//私有构造方法
	private Gson4JsonBuilder() {}
	
	/**
	 * 获取单实例
	 */
	public static JsonBuilder getInstance() {
		return Gson4JsonBuilderGenerateor.JSON_BUILDER;
	}
	
	public String toJson(Object object) {
		return gson().toJson(object);
	}
	
	public <T> T toObject(String json, Class<T> clazz) {
		return gson().fromJson(json, clazz);
	}
	
	//新建一个Gson 基于GsonBuilder
	private static Gson gson() {
		return new GsonBuilder().setDateFormat("MM/dd/yyyy HH:mm:ss").create();
	}
	
	//静态内部类产生单例实例
	private static class Gson4JsonBuilderGenerateor {
		private final static JsonBuilder JSON_BUILDER = new Gson4JsonBuilder();
	}
	
}
