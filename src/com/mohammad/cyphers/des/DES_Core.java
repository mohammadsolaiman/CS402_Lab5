package com.mohammad.cyphers.des;

import java.util.ArrayList;
import java.util.List;

public class DES_Core {

	private KeyGenerator KG ;
	private String key;
	public List<String> subkeys;
	

	private Tools tools;
	private DebugLogger Debug;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if(key.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: DES_Core, KEY length must be "+DES_CONSTANTS.input_plain_length);
		this.key = key;
		KG = new KeyGenerator(key);
		subkeys = KG.generate();
		tools = new Tools();
	}

	public DES_Core(String key){
		if(key.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: DES_Core, KEY length must be "+DES_CONSTANTS.input_plain_length);
		this.key = key;
		KG = new KeyGenerator(key);
		subkeys = KG.generate();
		tools = new Tools();
		Debug = new DebugLogger();
	}

	public List<String> getSubkeys() {
		return subkeys;
	}
	
	private String F(String Ri_1, String Ki){
		return tools.apply_P(tools.SubstitutionChoise_S_Box(tools.Xor(tools.Exp_Permutation(Ri_1), Ki)));
	}
	
	public List<String> LR_ = new ArrayList<String>();
	private String run_round_enc(int round_nb, String _64bitStr){
		String Li_1 = _64bitStr.substring(0, _64bitStr.length()/2);
		String Ri_1 = _64bitStr.substring( _64bitStr.length()/2);
		
		String Li = Ri_1;
		String Ri = tools.Xor(Li_1, F(Ri_1, subkeys.get(round_nb)));
		LR_.add("L{"+(round_nb+1)+"}   "+Li+"\t\tR{"+(round_nb+1)+"}   "+Ri);
		//System.out.println("Enc: Round "+round_nb+"\t"+subkeys.get(round_nb));
		return Li+Ri;
	}
	
	private String run_round_dec(int round_nb, String _64bitStr){
		String Li_1 = _64bitStr.substring(0, _64bitStr.length()/2);
		String Ri_1 = _64bitStr.substring( _64bitStr.length()/2);
		
		String Li = Ri_1;
		String Ri = tools.Xor(Li_1, F(Ri_1, subkeys.get(DES_CONSTANTS.number_of_rounds-round_nb-1)));
		//System.out.println("Dec: Round "+round_nb+"\t"+subkeys.get(DES_CONSTANTS.number_of_rounds-round_nb-1));

		LR_.add("L{"+(round_nb+1)+"}   "+Li+"\t\tR{"+(round_nb+1)+"}   "+Ri);
		return Li+Ri;
	}
	
	public String Encrypt(String _64bitStr){
		LR_.clear();
		if (_64bitStr.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: Tools.apply_P error length of string " + _64bitStr.length() + " != "
					+ DES_CONSTANTS.input_plain_length);
		
		String init = tools.apply_IP(_64bitStr), round_out = init;
		
		for(int round = 0;round < DES_CONSTANTS.number_of_rounds;round++){
			round_out = run_round_enc(round, round_out);
		}
		String L = round_out.substring(0, round_out.length()/2);
		String R = round_out.substring( round_out.length()/2);
		
		return tools.apply_invIP(R+L);
	}
	
	public String Decrypt(String _64bitStr){
		LR_.clear();
		if (_64bitStr.length() != DES_CONSTANTS.input_plain_length)
			System.err.println("ERROR: Tools.apply_P error length of string " + _64bitStr.length() + " != "
					+ DES_CONSTANTS.input_plain_length);
		
		String init = tools.apply_IP(_64bitStr), round_out = init;
		
		for(int round = 0;round < DES_CONSTANTS.number_of_rounds;round++){
			round_out = run_round_dec(round, round_out);
		}
		String L = round_out.substring(0, round_out.length()/2);
		String R = round_out.substring( round_out.length()/2);
		
		return tools.apply_invIP(R+L);
	}

}
