import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.*;

import org.json.simple.JSONArray;
//to Handle json 
import org.json.simple.JSONObject;
//HTML parser jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawler {
	
	public Crawler(){}
	
	public JSONObject[] searchAndGet(String inName) throws Exception
	{
		//public static void main(String[] args) {
    	
		//String name= "%EA%B9%80%EB%AF%BC%ED%9D%AC"; //�����
    	//String name= "%EC%9D%B4%EC%9E%AC%EC%98%81"; //���翵
    	//String name = "%EA%B9%80%ED%83%9C%ED%9D%AC"; //������
    	String name = URLEncoder.encode(inName,"UTF-8");
    	try{

    		//���հ˻� 
            String potalSearchUrl = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&oquery="+name+"&ie=utf8&query="+name;
    		
            
            Document potalSearch =Jsoup.connect(potalSearchUrl).get();
        
            // ������ �ι��� ��� �ϳ��� ����� ũ�� ��ȯ
        	String mainLink = potalSearch.select("div.people_info.section div.go_relate a.btn_txt_more").attr("abs:href");
        	// ����� ������ �ι��� ������ ��ȯ
        	Elements sameList1 = potalSearch.select("ul.same_list2.v2 li div.temp a");
        	// ���� ���� ����Ʈ 
        	Elements sameList2 = potalSearch.select("ul.same_list li div.temp span a");
        	
        	// �������� �ִ� 10��, 30 ���� ����
        	String sameListLink[] = new String[30];
        	
        	// ���������� �� 
        	int sameCnt=0;
        	if(mainLink!="")sameListLink[sameCnt++] = mainLink;
        	
        	//���а˻����� ����Ǵ� �������� ����Ʈ�� �ι��˻���ũ�� ��ȯ
        	for(Element e : sameList1)
        	{
        		sameListLink[sameCnt++]= Jsoup.connect(e.attr("abs:href")).get().select("div.people_info.section div.go_relate a.btn_txt_more").attr("abs:href");
        	}
        	for(Element e : sameList2)
        	{
        		sameListLink[sameCnt++]= Jsoup.connect(e.attr("abs:href")).get().select("div.people_info.section div.go_relate a.btn_txt_more").attr("abs:href");
        	}
        
        	//�� ���� ũ�Ѹ��Ͽ� json �������� ����
        	JSONObject toInsert[] = new JSONObject[30];
        	
        	for(int i=0;i<sameCnt;i++)
        	{
        		Elements detailInfo = Jsoup.connect(sameListLink[i]).get().select("div.people_wrap");        	
        		JSONObject detail = new JSONObject(); 
            	//�̸�, ����, ����(���ڸ�, ��)
        		detail.put("name",detailInfo.select("div.profile_dsc dl.who dt.name").text());
            	detail.put("job",detailInfo.select("div.profile_dsc dl.who dd.sub").text());
            	detail.put("old",detailInfo.select("div.profile_dsc dd.dft").text());
            	
            	//��Ÿ ����
            	Elements profileName = detailInfo.select("div.profile_dsc dl.dsc dt");
            	Elements profileValue = detailInfo.select("div.profile_dsc dl.dsc dd");
            	
            	for(int k=0;k<profileName.size();k++)
            	{
            		detail.put(profileName.get(k).text(),profileValue.get(k).text());
            	}
 //           		detail.put(profileName.remove(0).text(), profileValue.remove(0).text().split("\""));
            	
            	/*
            	//��ȭ
            	String movieList = detailInfo.select("div.workact_dsc").text();
            	System.out.println(movieList);
            	detail.put("movieList", movieList);
            	*/
            	toInsert[i]=detail;
        	}
        	return toInsert;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    	
    }
}

