package com.mohammad.cyphers.des;

import java.util.Scanner;

import javax.annotation.processing.SupportedSourceVersion;

public class Tools {

	public String C_LeftShift(String st_bits, int nb_rot) {
		String out = st_bits.substring(nb_rot);
		out += st_bits.substring(0, nb_rot);
		return out;
	}

	public String C_RightShift(String st_bits, int nb_rot) {
		int len = st_bits.length();
		String out = st_bits.substring(0, len - nb_rot);
		out += st_bits.substring(len - nb_rot);
		return out;
	}

	public String apply_PC2(String CD) {
		if (CD.length() != DES_CONSTANTS.key_length)
			System.err.println("ERROR: KeyGenerator Tools.apply_PC2 error length of key " + CD.length() + " != "
					+ DES_CONSTANTS.key_length);
		String pc2_result = "";

		for (int i = 0; i < DES_CONSTANTS.PC_2.length; i++) {
			for (int j = 0; j < DES_CONSTANTS.PC_2[0].length; j++) {
				pc2_result += CD.charAt(DES_CONSTANTS.PC_2[i][j] - 1);
			}
		}
		return pc2_result;
	}

	public String apply_PC1(String CD) {
		if (CD.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: KeyGenerator Tools.apply_PC1 error length of key " + CD.length() + " != "
					+ DES_CONSTANTS.input_plain_length);
		String pc1_result = "";

		for (int i = 0; i < DES_CONSTANTS.PC_1.length; i++) {
			for (int j = 0; j < DES_CONSTANTS.PC_1[0].length; j++) {
				pc1_result += CD.charAt(DES_CONSTANTS.PC_1[i][j] - 1);
			}
		}
		return pc1_result;
	}

	public int binary2decimal(String bit_str) {
		int result = 0;
		int ctr = 0;
		for (int i = bit_str.length() - 1; i >= 0; i--) {
			if (bit_str.charAt(ctr) != '0' && bit_str.charAt(ctr) != '1')
				System.err.println("ERROR: Tools.binary2decimal input value is not binary!");
			result += Integer.parseInt("" + bit_str.charAt(ctr++)) * (int) Math.pow(2, i);
		}

		// System.out.println("bin_to_dec:: "+bit_str+"\t"+result);
		return result;
	}

	public String ascii2binary(char c) {
		int ascii_val = (int) c;
		String binary = "";
		while (ascii_val > 0) {
			int rem = ascii_val % 2;
			ascii_val = ascii_val / 2;
			binary = rem + binary;
		}
		while (binary.length() < 8) {
			binary = "0" + binary;
		}
		return binary;
	}

	public String decimal2hex(int dec) {
		String hex = "";
		int val = dec;
		while (val > 0) {
			int rem = val % 16;
			val = val / 16;
			hex = DES_CONSTANTS.conversion_table[rem][1] + hex;
		}
		while (hex.length() < 2) {
			hex = "0" + hex;
		}
		return hex;
	}

	public String hex2binary(char hex){
		for(int i=0;i<DES_CONSTANTS.conversion_table.length;i++){
			if(DES_CONSTANTS.conversion_table[i][1].equals(""+hex))
			{
				return DES_CONSTANTS.conversion_table[i][2];
			}
		}
		
		return null;
	}
	public String binary2hex(String bitStr) {
		int decVal = this.binary2decimal(bitStr);
		return this.decimal2hex(decVal);
	}

	public void TextConversion(StringBuilder inputText, StringBuilder binary_out, StringBuilder hex_out,
			StringBuilder ascii_out) {
		if (inputText.length() % 8 != 0) {
			for (int i = inputText.length() % 8; i < 8; i++)
				inputText.append(' ');
		}

		char c;
		int i;
		String binary_tmp, hex_tmp, ascii_tmp;
		for (i = 0; i < inputText.length(); i++) {
			c = inputText.charAt(i);
			binary_tmp = this.ascii2binary(c);
			hex_tmp = this.binary2hex(binary_tmp);
			ascii_tmp = (int) c + "";
			binary_out.append(binary_tmp + "  ");
			hex_out.append(hex_tmp + "  ");

			while (ascii_tmp.length() < 3) {
				ascii_tmp = "0" + ascii_tmp;
			}

			ascii_out.append(ascii_tmp + "  ");

		}
	}
	public void HexConversion(StringBuilder input, StringBuilder binary_out, StringBuilder text_out,
			StringBuilder ascii_out) {
		
		String hex_in = input.toString().replaceAll(" ", "").replaceAll("\n", "");
		
		int ctr = 0;
		for(char c : hex_in.toCharArray()){
			binary_out.append(this.hex2binary(c));
			ctr++;
			if(ctr == 2){
				binary_out.append(' ');
				ctr = 0;
			}
		}

		this.BinaryConversion(binary_out, text_out, new StringBuilder(), ascii_out);
	}
	
	public void BinaryConversion(StringBuilder inputBits, StringBuilder text_out, StringBuilder hex_out,
			StringBuilder ascii_out) {
		String rem_bits = inputBits.toString().replaceAll(" ", "").replaceAll("\n", "");;
		
		
		while(rem_bits.length()>0){
			String nextByte = rem_bits.substring(0, 8);
			rem_bits = rem_bits.substring(8);
			int ascii = this.binary2decimal(nextByte);
			String hex = this.decimal2hex(ascii);
			
			String ascii_st = ascii+"";
			while (ascii_st.length() < 3) {
				ascii_st = "0" + ascii_st;
			}
			
			text_out.append(Character.toChars(ascii));
			hex_out.append(hex+" ");
			ascii_out.append(ascii_st+" ");
					
		}
		
	}

	public String _64bitdistriputeString(String st) {
		// assume that the string st contain multiple of 8 words
		Scanner sc = new Scanner(st);
		String out = "";

		while (sc.hasNext()) {
			for (int i = 0; i < 8; i++) {
				try {
					out += sc.next() + " ";

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			out += "\n";
		}

		return out;
	}

	public String Xor(String st1, String st2) {
		String out = "";

		if (st1.length() != st2.length())
			System.err.println("ERROR: Xor strings must be with the same length!");

		for (int i = 0; i < st1.length(); i++) {
			if (st1.charAt(i) == st2.charAt(i))
				out += '0';
			else
				out += '1';
		}
		return out;
	}

	public String Exp_Permutation(String bit_str) {
		if (bit_str.length() != DES_CONSTANTS.input_plain_length / 2)
			System.err.println("ERROR: Exp_Permutation Tools.Exp_Permutation error length of key " + bit_str.length()
					+ " != " + DES_CONSTANTS.input_plain_length / 2);
		String result = "";

		for (int i = 0; i < DES_CONSTANTS.E.length; i++) {
			for (int j = 0; j < DES_CONSTANTS.E[0].length; j++) {
				result += bit_str.charAt(DES_CONSTANTS.E[i][j] - 1);
			}
		}
		return result;
	}

	public String SubstitutionChoise_S_Box(String _48bit_str) {
		if (_48bit_str.length() != DES_CONSTANTS.generated_key_length)
			System.err.println("ERROR: Tools.SubstitutionChoise_S_Box error length of key " + _48bit_str.length()
					+ " != " + DES_CONSTANTS.generated_key_length);

		String output = "", rem = _48bit_str;

		int nb_bits = DES_CONSTANTS.generated_key_length / DES_CONSTANTS.sbox_number;

		for (int i = 0; i < DES_CONSTANTS.sbox_number; i++) {
			String Si = rem.substring(0, nb_bits);
			rem = rem.substring(nb_bits);
			int row = binary2decimal(Si.charAt(0) + "" + Si.charAt(Si.length() - 1));
			int col = binary2decimal(Si.substring(1, Si.length() - 1));

			int val = DES_CONSTANTS.S_Boxes[i][row][col];
			// System.out.println(row+"\t"+col+"\t"+val+"\t"+Si);
			output += DES_CONSTANTS.conversion_table[val][2];
		}

		return output;
	}

	public String apply_P(String _32bitStr) {
		if (_32bitStr.length() != DES_CONSTANTS.input_plain_length / 2)
			System.err.println("ERROR: Tools.apply_P error length of string " + _32bitStr.length() + " != "
					+ DES_CONSTANTS.input_plain_length / 2);
		String result = "";

		for (int i = 0; i < DES_CONSTANTS.P.length; i++) {
			for (int j = 0; j < DES_CONSTANTS.P[0].length; j++) {
				result += _32bitStr.charAt(DES_CONSTANTS.P[i][j] - 1);
			}
		}
		return result;
	}

	public String apply_IP(String _64bitStr) {
		if (_64bitStr.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: Tools.apply_P error length of string " + _64bitStr.length() + " != "
					+ DES_CONSTANTS.input_plain_length);
		String result = "";

		for (int i = 0; i < DES_CONSTANTS.IP.length; i++) {
			for (int j = 0; j < DES_CONSTANTS.IP[0].length; j++) {
				result += _64bitStr.charAt(DES_CONSTANTS.IP[i][j] - 1);
			}
		}
		return result;
	}

	public String apply_invIP(String _64bitStr) {
		if (_64bitStr.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: Tools.apply_P error length of string " + _64bitStr.length() + " != "
					+ DES_CONSTANTS.input_plain_length);
		String result = "";

		for (int i = 0; i < DES_CONSTANTS.invIP.length; i++) {
			for (int j = 0; j < DES_CONSTANTS.invIP[0].length; j++) {
				result += _64bitStr.charAt(DES_CONSTANTS.invIP[i][j] - 1);
			}
		}
		return result;
	}
}
