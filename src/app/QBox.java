package app;

import java.util.function.Function;

public class QBox {
	
	/** 큐의 메시지 */
	public String msg;
	
	/** obj는 메시지와 함께 보낼 데이터를 저장 */
	public Object obj;
	
	/** Method를 넘겨보자. */
	Function<Object, Object> function;
	
	/** QBox용 빌더 */
	public static class Builder {
		
		private String msg = "";
		private Object obj = null;
		private Function<Object, Object> function = null;
		
		public Builder msg(String msg) {
			this.msg = msg;
			return this;
		}
		
		public Builder obj(Object obj) {
			this.obj = obj;
			return this;
		}
		
		public Builder function(Function<Object, Object> function) {
			this.function = function;
			return this;
		}
		
		public QBox build() {
			return new QBox(this);
		}
	}
	
	private QBox(Builder b) {
		this.msg = b.msg;
		this.obj = b.obj;
		this.function = b.function;
	}
}
