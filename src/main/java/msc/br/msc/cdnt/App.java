package msc.br.msc.cdnt;

public class App {
	public static void main(String[] args) throws Exception {	

		try {
			
			// Get Data From CDNT
			HttpCustomClient cli_web = new HttpCustomClient();
			String resultBodyToSaveJsonFile = cli_web.getResultBody();
						
			
			// Save Response to File
			FileWork fl = new FileWork();
			fl.GenerateFile();
			fl.WriteFile(resultBodyToSaveJsonFile);
			
			
			CdntStringUtil cd = new CdntStringUtil();
			
			// Decrypted "cifrado"			
			String sentenceDecrypted = cd.Decript();			
						
			
			// criar o SH1
			String hashSh1 = cd.GenerateHash(sentenceDecrypted);
			
			
			// Update JSON File with sh1 and decrypted sentence
			fl.setDecrifradoJSON(sentenceDecrypted);
			fl.setResumeEncrypted(hashSh1);

			
			// Send Post
			cli_web.sendPost();
			
			System.out.println("Project ended.");


		} catch (Exception e) {
			String em = e.getMessage();
			System.out.println(em);
		}

	}

}
