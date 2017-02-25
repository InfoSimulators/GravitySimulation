package com.github.infosimulators;

public class IDRegistry {
	
	private static int current = 0;
	
	public static int nextID() {
		return current++;
	}
	
	public static abstract class IDd {

		private int id;
		
		public IDd() {
			id = IDRegistry.nextID();
		}
		
		public int getID() {
			return id;
		}
		
	}
	
}
