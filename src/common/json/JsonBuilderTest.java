package common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonBuilderTest {
	public static void main(String[] args) {
		User user = new User();
		
		System.out.println(JsonBuilderExecutor.getInstance().toJson(user));
		
	}
	
	public void test () {
//		Gson gson = GsonCreator.createGson(new GsonBuilderAttributeSetter() {
//			public void setAttribute(GsonBuilder gsonBuilder) {
//				gsonBuilder.setDateFormat("yyyy-MM-dd");
//				gsonBuilder.setPrettyPrinting();
//			}
//		});
		
		//String json = gson.toJson(obj);
	}
	
	private static class User {
		private Integer _int;
		private Double _double;
		private String[] array;
		private List<String> list;
		private Map<String, String> map;
		private Date date;
		private List<Map<String, String>> list_map;
		private String string = "";
		
		public String getString() {
			return string;
		}
		public void setString(String string) {
			this.string = string;
		}
		public Integer get_int() {
			return null;
		}
		public void set_int(Integer _int) {
			this._int = _int;
		}
		public Double get_double() {
			return _double;
		}
		public void set_double(Double _double) {
			this._double = _double;
		}
		public String[] getArray() {
			return array;
		}
		public void setArray(String[] array) {
			this.array = array;
		}
		public List<String> getList() {
			return list;
		}
		public void setList(List<String> list) {
			this.list = list;
		}
		public Map<String, String> getMap() {
			return map;
		}
		public void setMap(Map<String, String> map) {
			this.map = map;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public List<Map<String, String>> getList_map() {
			return list_map;
		}
		public void setList_map(List<Map<String, String>> list_map) {
			this.list_map = list_map;
		}
		
		
	}
}
