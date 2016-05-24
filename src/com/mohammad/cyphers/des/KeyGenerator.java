package com.mohammad.cyphers.des;

import java.util.ArrayList;
import java.util.List;

public class KeyGenerator {

	private String key;

	private List<String> allKeys;

	private int ptr;

	private Tools tools;

	public KeyGenerator(String key) {
		tools = new Tools();
		this.key = tools.apply_PC1(key);
		allKeys = new ArrayList<String>();
		ptr = 0;
		
	}

	public KeyGenerator() {
		allKeys = new ArrayList<String>();
		ptr = 0;
		tools = new Tools();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if (key.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: KeyGenerator setKey error length of key " + key.length() + " != "
					+ DES_CONSTANTS.input_plain_length);
		this.key =tools.apply_PC1(key);
	}

	public List<String> generate() {

		String C = key.substring(0, DES_CONSTANTS.key_length / 2), D = key.substring(DES_CONSTANTS.key_length / 2);
		for (int round = 0; round < DES_CONSTANTS.number_of_rounds; round++) {
			int nb_rot = DES_CONSTANTS.left_shift_schedule[round];
			C =tools.C_LeftShift(C, nb_rot);
			D =tools.C_LeftShift(D, nb_rot);
			allKeys.add(tools.apply_PC2(C+D));
		}
		return allKeys;
	}

	public void display_allKeys(){
		for(String s : allKeys)
			System.out.println(s);
	}
}
