## people-search-api  _이재영
    제공된 인물 키워드를 바탕으로 네이버 인물 정보 영역을 크롤링합니다.
    요청된 인물 정보에 맞는 인물 리스트와 정보를 JSON 형식으로 Response 해줍니다.
  
## 작동 방식
    GET /searchApi/name/이름
    GET /searchApi/job/직업     
    GET /searchApi/old/나이     
    GET /searchApi/nameAndJob/이름/직업
    GET /searchApi/nameAndOld/이름/나이
    

## 사용 예 
    /searchAPi/name/강성수
    /searchApi/job/교육인
    /searchApi/old/63
    /searchApi/nameAndJob/강성수/영화배우   
    /searchApi/nameAndOld/강성수/31
    
    각각의 결과는 name.png, job.png, old,png, nameAndJob.png, nameAndOld.png 에서 확인 가능합니다. 

## 데이터
    올려주신 people_keyword_short.txt 를 사용하였습니다.

## 개발 환경
    Java(크롤링)
    Node.js(서비스)
    Mysql 5.7

## 동작방식 
    #크롤링
      1. 제공된 이름 리스트를 읽어들여 통합검색에 해당 내용을 검색합니다. 
      2. 나온 결과를 바탕으로 동명이인들의 인물검색 페이지의 경로를 얻어옵니다. 
      3. 각 인물의 페이지에서 정보를 크롤링하여 JSONObject의 배열로 만듭니다. 
      4. JSONObject 배열 속 내용을 DB에 저장합니다.
    #서비스
      1. 각 요청에 맞춰 DB에 있는 duplicate가 0(dummy 데이터 제외)인 JSON 객체만 읽어와 response 해줍니다.
  
