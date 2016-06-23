import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PersonAPI {

	public static void main(String args[]) throws Exception
	{
		FileInputStream fis = new FileInputStream("d:\\people_keyword_short.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		Crawler crol = new Crawler();
		dbConnection dbConn = new dbConnection();
		int cnt=0;
		JSONObject result[]=null;
		String line="";
		String []nameArray;
		ArrayList<String> names = new ArrayList<>();
		
		while((line = br.readLine()) !=null)
		{
			nameArray = line.split("\t");
			for(String str : nameArray)
				names.add(str);
		}
		int oddCnt=0;
		for(String str : names)
		{	
			if(oddCnt%2==0)
			{
				System.out.print(str);
				result = crol.searchAndGet(str);
				for(JSONObject j : result)
				{
					if(j==null)break;
					else{
						for(int dupCnt=0;dupCnt<100;dupCnt++)
						{
							j.put("duplicate",dupCnt);
							dbConn.inSertJson(j);
						}
					}
				}
			}
			oddCnt++;
		}

	}
}