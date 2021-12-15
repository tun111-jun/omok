package server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/*
 * Sever의 Main 파일
 * Client와 초기 연결 담당
 */

public class ServerMain {
	static ServerSocket welcomeSocket = null;
	static ArrayList<Socket> list=new ArrayList<Socket>();
	private static ExecutorService executorService;
	static UserDataList userDataList = new UserDataList();
	static String connectedUser = "";
	
	//------------------------------
	//Server Main
	//------------------------------
	public static void main(String[] args) {
		//------------------------------
		//Client 연결
		//------------------------------
		try {
			connectClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//------------------------------
	//Client 연결 메서드
	//------------------------------
	public static void connectClient() throws IOException{	
		//------------------------------
		//포트 설정
		//------------------------------
		int nPort;
		nPort = 5274;
		
		//------------------------------
		//welcome socket 개방
		//------------------------------
		welcomeSocket = new ServerSocket(nPort);
		//System.out.println("Here"+welcomeSocket);
		
		//------------------------------
		// 스레드를 이용하기 위해서 스레드를 담을 스레드 풀이 필요함
		// ExecutorService 인터페이스 구현객체 Executors 정적메서드를 통해 최대 스레드 개수가 10인 스레드 풀 생성
		//------------------------------
		final int MAX_POOL = 10;//풀 최대 개수(최대 연결 가능 client)
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_POOL);
        
        //------------------------------
        // MAX_POOL개의 스레드 생성(MAX_POOL개의 client 연결 받기)
        //------------------------------
        for(int i = 0; i < MAX_POOL; i++){
        	//스레드 생성
        	Runnable runnable = new Runnable() {
        		@Override
        		public void run() {
        			//------------------------------
        			//스레드 내부에서 동작할 내용
        			//------------------------------
        			try {
        				//------------------------------
        				//연결 기다리기
        				//------------------------------
        				System.out.println("Server start.. (port#=" + nPort + ")\n ");  
        				Socket connectionSocket;
        				
						connectionSocket = welcomeSocket.accept();//클라이언트와 연결
						
						//System.out.println("Here"+connectionSocket);

						//------------------------------
        				//입력버퍼, 출력버퍼 설정
        				//------------------------------
						BufferedReader inFromClient = new BufferedReader(
	        					new InputStreamReader(connectionSocket.getInputStream()));//클라이언트로부터 입력
	        			DataOutputStream outToClient =
	        					new DataOutputStream(connectionSocket.getOutputStream());//클라이언트로 출력
	        			String clientRequest;
	        			
	        			//------------------------------
        				//클라이언트 요청 내용 확인
        				//------------------------------
	        			while(true)
	        			{
	        				clientRequest = "";
	        				clientRequest = inFromClient.readLine();
	        				
	        				//요청코드와 세부내용 나누기
	        				String requestCode = clientRequest.substring(0, 3);
	        				String dataFromUser = clientRequest.substring(3);	        				
	        				
	        				//------------------------------
	        				//요청코드(프로토콜) 확인
	        				//------------------------------
	        				if(requestCode.equals("100"))//로그인 ID 입력 프로토콜(나중에 프로토콜 정의 작성예정)
	        				{
	        					
	        					File file=new File("Members.txt");
	        					Scanner scan=new Scanner(file);
	        					String[] array=dataFromUser.split("/");
	        					boolean flag=false;
	        					
	        					
	        					
	        				
	        						while(scan.hasNextLine())//로그인 정보 확인
	        						{
	        							
	        							System.out.println("This line");
	        							String temp=scan.nextLine();
	        							String[] temp_array=temp.split("/");
	        							System.out.println(temp);
	        							
	        							
	        							
	        							if(array[0].equals(temp_array[0]))
	        							{
	        								for(int i=0;i<array.length;++i)
	        								{
	        									if(array[i].equals(temp_array[i]))
	        									{
	        										flag=true;
	        									}
	        									else
	        										{
	        											flag=false;
	        											break;
	        										}
	        									
	        								}
	        								
	        							}
	        							
	        						}
	        						if(flag)
	        							{
	        								outToClient.writeByte(1);//로그인 정보 일치
	        								list.add(connectionSocket);
	        								
	        								if(connectedUser.equals(""))
	        									connectedUser += userDataList.getUserNickName(array[0]);
	        								else
	        									connectedUser += "/" + userDataList.getUserNickName(array[0]);
	        								
	        								for(int i=0;i<list.size();i++) {
		        								outToClient = new DataOutputStream(list.get(i).getOutputStream());
		        								outToClient.writeBytes("201" + "/" + connectedUser + "\n");//신규 로그인 정보 전체 전송
		        							}
	        							}
	        						else
	        							outToClient.writeByte(0);//로그인 정보 불일치
	        							
	        					}
	        			
	        				
	        				else if(requestCode.equals("101"))//회원가입 입력 프로토콜
	        				{
	        					
	        					 String s = "";
	        				
	        		             boolean isOk = false;
	        					
	        		            FileOutputStream output=new FileOutputStream("Members.txt",true);
	        		            OutputStreamWriter writer=new OutputStreamWriter(output,"UTF-8");
	        		             
	        					BufferedWriter bw=new BufferedWriter(writer);
	        			
	        					BufferedReader br = new BufferedReader(new FileReader("Members.txt"));
	        					
	        					
	        					
	        						
	        					
	        					File file=new File("Members.txt");
	        					Scanner scan=new Scanner(file);
	        					String[] array=dataFromUser.split("/");
	        					boolean flag=true;
	        					
	        					if(!scan.hasNextLine())//첫 회원 입력시 필요조건
	        					{
	        						bw.write(dataFromUser);
	        						bw.newLine();
        							outToClient.writeByte(1); 
	        						flag=false;
	   
	        					}
	        					else
	        					{
	        						while(scan.hasNextLine())//ID 중복확인
	        						{
	        							
	        							System.out.println("This line");
	        							String temp=scan.nextLine();
	        							String[] temp_array=temp.split("/");
	        							
	        							
	        							if(array[0].equals(temp_array[0]))
	        							{
	        								isOk=true;
	        								outToClient.writeByte(0);
	        								break;
	        							}
	        							
	        						}
	        					}
	        					
	        					
	        
	        						
	        						if(!isOk&&flag==true)//ID 중복 없을 시 조건		
	        						{
	        							System.out.println(dataFromUser);
	        							//data + 접속 횟수, 마지막 접속 ip, 마지막 접속 시간, 승, 무, 패
	        							bw.write(dataFromUser + "/0/000.000.000.000/00:00/0/0/0");
	        							bw.newLine();
	        							outToClient.writeByte(1);
	        							userDataList.readData();
	        							
	        							
	        	
	        						}
	        								
	        								
	        							
	        							
	        							scan.close();
	        							bw.close();
	        							br.close();
	        					
	        				}
	        				
	        				else if(requestCode.equals("102"))//전체 채팅 받기, 전체 전송
	        				{
	        					String[] tempData = dataFromUser.split("/");
	        					String userID = tempData[0];
	        					String userMsg = tempData[1];
	        					String userNickName = userDataList.getUserNickName(userID);
	        					
	        					System.out.println(list.get(0));
	        					
	        					for(int i=0;i<list.size();i++)
	        					{
	        						outToClient = new DataOutputStream(list.get(i).getOutputStream());
	        						outToClient.writeBytes("300" + "/" + userNickName + "/" + userMsg + "\n");
	        					}
	        				}
	        				else if(requestCode.equals("103"))//로그인 정보 업데이트 요청
	        				{
	        					String[] temp = dataFromUser.split("/");
	        					userDataList.changeLoginInfo(temp[0], temp[1], temp[2]);
	        				}
	        				else if(requestCode.equals("104"))//유저정보 창 열기 요청
	        				{
	        					outToClient.writeBytes("301" + "/" + "none" + "\n");
	        				}
	        				else if(requestCode.equals("200"))//유저 정보 요청
	        				{
	        					String nickName = dataFromUser;
	        					String userInfo = userDataList.userInfo(nickName);
	        					outToClient.writeBytes(userInfo + "\n");
	        				}
	        				else if(requestCode.equals("202"))//대결 신청
	        				{
	        					String[] temp = dataFromUser.split("/");
	        					String id = temp[0];
	        					String nickName = temp[1];
	        					for(int i=0;i<list.size();i++) {
	        						outToClient = new DataOutputStream(list.get(i).getOutputStream());
	        						outToClient.writeBytes("203" + "/" + userDataList.getUserID(nickName) + "/"  + userDataList.getUserNickName(id)  + "\n");
	        					}
	        				}
	        				else if(requestCode.equals("204"))
	        				{
	        					for(int i=0;i<list.size();i++) {
	        						outToClient = new DataOutputStream(list.get(i).getOutputStream());
	        						outToClient.writeBytes("204" + "/" + "none" + "\n");
	        					}
	        				}
	        				else if(requestCode.equals("205"))
	        				{
	        					for(int i=0;i<list.size();i++) {
	        						outToClient = new DataOutputStream(list.get(i).getOutputStream());
	        						outToClient.writeBytes("205" + "/" + "none" + "\n");
	        					}
	        				}
	        				else if(requestCode.equals("400"))//1대1 채팅 받기, 보대기
	        				{
	        					String[] tempData = dataFromUser.split("/");
	        					String userID = tempData[0];
	        					String userMsg = tempData[1];
	        					String userNickName = userDataList.getUserNickName(userID);
	        					
	        					for(int i=0;i<list.size();i++)
	        					{
	        						outToClient = new DataOutputStream(list.get(i).getOutputStream());
	        						outToClient.writeBytes("401" + "/" + userNickName + "/" + userMsg + "/" + userID + "\n");
	        					}
	        				}
	        				else
	        				{
	        					System.out.println("Unknown request");
	        				}
	        				
	        			}
					
        			}
        			catch(IOException e) {
        				System.out.println(e);
        			}
        		}
        	};
        	
        	//------------------------------
        	//스레드풀에게 작업 처리 요청
        	//------------------------------
        	executorService.execute(runnable);  
        }
        
      //------------------------------  
      //스레드풀 종료
      //------------------------------
      executorService.shutdown();
	}
}
