package msc.br.msc.cdnt;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileWork {

	private String file_name = "answer.json";
	private String path;
	private String path_file;

	public FileWork() {
		path = System.getProperty("user.dir");
		path_file = path + "/docs/" + file_name;
	}

	public void GenerateFile() throws IOException {
		String path = System.getProperty("user.dir");
		File file = new File(path + "/docs/" + file_name);

		if (file.createNewFile()) {
			System.out.println("File created: " + file.getName());
		} else {
			System.out.println("File already exists.");
		}

		//System.out.println(file.getAbsolutePath());
	}

	public void WriteFile(String sentence) throws IOException {
		String path = System.getProperty("user.dir");
		String file = path + "/docs/" + file_name;
		FileWriter myWriter = new FileWriter(file);
		myWriter.write(sentence);
		myWriter.close();
		System.out.println("Successfully wrote to the file.");
	}

	public JSONObject ReadJsonFile() throws Exception 
	{
		JSONObject jsonObj;
		JSONParser jsonP = new JSONParser();
		jsonObj = (JSONObject) jsonP.parse(new FileReader(path_file));
		return jsonObj;
	}

	public void setDecrifradoJSON(String stringDecifrado) throws Exception 
	{
		JSONObject json_file = this.ReadJsonFile();
		json_file.replace("decifrado", stringDecifrado);
		this.WriteFile(json_file.toString());
		System.out.println(json_file);
	}

	public void setResumeEncrypted(String resumeEnc) throws Exception 
	{
		JSONObject json_file = this.ReadJsonFile();
		json_file.replace("resumo_criptografico", resumeEnc);
		this.WriteFile(json_file.toString());
	}

	public long GetSkipedFields() throws Exception 
	{
		JSONObject json_file = this.ReadJsonFile();
		long numberOfFilds = (long) json_file.get("numero_casas");
		return numberOfFilds;
	}

	public String GetEncryptedPhrase() throws Exception 
	{
		JSONObject json_file = this.ReadJsonFile();
		String phrase = json_file.get("cifrado").toString();
		return phrase;
	}

	public File getFileToSendPost()
	{
		File file = new File(this.path_file);
		return file;
	}

}
