package msc.br.msc.cdnt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

public class CdntStringUtil {
	private String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", 
			"j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z" };

	private long casas;
	private String cifrado;
	private List<Character> cifrado_list;


	public CdntStringUtil() throws Exception {
		FileWork fw = new FileWork();
		casas = fw.GetSkipedFields();
		cifrado = fw.GetEncryptedPhrase();
		cifrado_list = this.convertStringToCharList(cifrado);
	}

	public List<Character> convertStringToCharList(String str) {

		// Create an empty List of character
		List<Character> chars = str

				// Convert to String to IntStream
				.chars()

				// Convert IntStream to Stream<Character>
				.mapToObj(e -> (char) e)

				// Collect the elements as a List Of Characters
				.collect(Collectors.toList());

		// return the List
		return chars;
	}

	public String Decript() {
		String newSentence = "";
		
		for (Character c : this.cifrado_list) {
			
			boolean isCharacter = Character.isLetter(c);
			
			if (isCharacter) {
				String valueToInsert = inversor(c);
				newSentence = newSentence.concat(valueToInsert);		
			}

			else {
				newSentence = newSentence.concat(c.toString());
			}
		}

		return newSentence;
	}

	private String inversor(Character c) {
		long index = ArrayUtils.indexOf(this.alpha, c.toString());
		long skp_field = index - this.casas;

		String valueToReplace = "";	
		
		if (skp_field < 0) 
		{
			int alphaSize = ArrayUtils.getLength(this.alpha);
			long skp =  alphaSize - (skp_field*-1);			
			valueToReplace = this.alpha[(int)skp];			
		} 
		else 
		{
			valueToReplace = this.alpha[(int) skp_field];
		}

		return valueToReplace;
	}
	
	public String GenerateHash (String sentence) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(sentence.getBytes("utf8"));
		String hash = String.format("%040x", new BigInteger(1, digest.digest()));

		return hash;
	}
}
