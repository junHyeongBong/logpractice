package kr.home.log.category.type;

public enum Status {
	
	ERROR(10),
	SUCCESS(200),
	NOTFOUND(404);
	
	private int value;
	
	Status(int value) {
		this.value = value;
	}
	
	public static Status valueOf(int value) {
		switch (value) {
		case 10:
			return ERROR;
		case 200:
			return SUCCESS;
		case 404:
			return NOTFOUND;
		}
		
		return null;
	}

}
