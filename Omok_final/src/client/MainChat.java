package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * 대기실 메시지 전송 관련 클래스
 * 다른 사람들의 메시지 표시, 서버로 메시지 전송
 */

public class MainChat {
	private Socket clientSocket = null;
	
	public MainChat() {
		
	}
	
	//------------------------------
	//서버로 입력받은 메시지 전송
	//------------------------------
	public void sendMsg(String message,JFrame MainChatFrame)
	{
		//미구현
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
			outToServer.writeBytes("102" + Login.myID + "/" + message + "\n");//프로토콜 + id + 채팅내용
		
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
	//client socket setter
		public void setClientSocket(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
}
