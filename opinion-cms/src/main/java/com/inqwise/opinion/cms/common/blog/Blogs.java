package com.inqwise.opinion.cms.common.blog;

public enum Blogs {
	Undefined(0),
	OfficialInqwiseBlog(1);
	
	private final int id;

	public int getId() {
		return id;
	}
	
	public Integer getIdOrNullWhenUndefined(){
		return Undefined.getId() == id ? null : Integer.valueOf(id);
	}
	
	private Blogs(int value) {
		this.id = value;
	}
	
	public static Blogs fromInt(Integer id){
		return fromInt(null == id ? 0 : id.intValue());
	}
	
	public static Blogs fromInt(int id){
		
		for (Blogs b : Blogs.values()) { 
			if (id == b.id) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}

	public static boolean contains(int id) {
		for (Blogs b : Blogs.values()) {
			if (id == b.id && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
