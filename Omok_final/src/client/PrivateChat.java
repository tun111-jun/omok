package client;
/*
 * 1대1 채티방 기능 관련 클래스
 * 채팅 전송과 상대편 정보 불러오기 등의 기능
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;

public class PrivateChat {
	private Socket clientSocket = null;
	
	public PrivateChat()
	{
		
	}


	//------------------------------
	//서버로 입력받은 메시지 전송
	//------------------------------
	public void sendMsg(String message)
	{
		System.out.println(message);//테스트용 콘솔 출력
			
		//Socket clientSocket = ClientMain.clientSocket;
		setClientSocket(ClientMain.clientSocket);
			
		if(clientSocket == null) {
			System.out.println("서버 연결 오류 발생");
			return;
		}
			 
		
		try {
			//------------------------------
			//입력버퍼, 출력버퍼 초기화
			//------------------------------
			DataOutputStream outToServer =
					new DataOutputStream(clientSocket.getOutputStream());//서버로 출력
			BufferedReader inFromServer =
					new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream()));//서버로부터 입력
				
			//------------------------------
			//서버로 정보 전송
			//서버에서 readLine으로 받기 때문에 마지막에 꼭 "\n"
			//------------------------------
			outToServer.writeBytes("400" + Login.myID + "/" + message + "\n");//프로토콜 + id + password
			
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
		
	//client socket setter
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
}
	

