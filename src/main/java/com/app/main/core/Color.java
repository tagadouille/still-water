package com.app.main.core;

public enum Color {


    RED(0),
    GREEN(1),
    PURPLE(2),
    BLUE(3);

    private final int id;

    Color(int id) {this.id = id;}

    public int getType() {
		return this.id;
	}

    public static Color fromValue(int value) {
		for (Color type : values()) {
			if (type.id == value) {
				return type;
			}
		}
		return null;
	}
    
}
