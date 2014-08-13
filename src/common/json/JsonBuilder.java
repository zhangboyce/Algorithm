package common.json;

/**
 * JsonBuilder
 * @author boyce
 * @version 2014-3-20
 */
public interface JsonBuilder {
	public String toJson(Object object);
	public <T> T toObject(String json, Class<T> clazz);
}
