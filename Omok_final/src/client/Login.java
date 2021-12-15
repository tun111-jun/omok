package client;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

/*
 * 로그인 관련 정보를 처리를 하는 클래스
 * 아이디, 비밀번호 전송 및 확인
 * 확인 후 대기방으로 넘기기
*/

public class Login {
	
	static String myID = null;
	
	public Login()
	{	
		
	}
	
	//------------------------------
	//id, 비밀번호 확인
	//맞다면 대기실로 이동
	//틀리다면 경고출력, 로그인 창으로 이동
	//------------------------------
	public void checkLoginInfo(JFrame loginFrame, String id, char[] password)
	{
		Socket clientSocket = ClientMain.clientSocket;
		
		if(clientSocket == null) {
			System.out.println("서버 연결 오류 발생");
			return;
		}
		
		//------------------------------
		//서버로 아이디와 비밀번호 전송
		//올바른 로그인인지 확인
		//------------------------------
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
			outToServer.writeBytes("100" + id + "/" + String.valueOf(password) + "\n");//프로토콜 + id + password
			
			//------------------------------
			//서버에서 답변 받기
			//------------------------------
			int result = inFromServer.read();
			if(result == 1)//로그인 성공
			{
				//------------------------------
				//로그인 정보 전송
				//------------------------------
				myID = id;
				
				// InetAddress 틀래스의 인스턴스 를 생성
				InetAddress myIP = InetAddress.getLocalHost();			
				// getHostAddress() 사용중인 PC의 IP주소를 얻어온다.
				String strIPAddress = myIP.getHostAddress();
				
				LocalDateTime currentDateTime = LocalDateTime.now();
				outToServer.writeBytes("103" + myID + "/" + strIPAddress + "/" + currentDateTime.toString() + "\n");
				
				//기존 login frame 종료
				loginFrame.setEnabled(false);
				loginFrame.setVisible(false);
				
				//대기방으로 이동
				new MainChatFrame();
					
				
			}
			else//로그인 실패
			{
				//미구현 로그인 실패창 띄우기
				
				JOptionPane.showMessageDialog(null, "로그인에 실패했습니다.");
				System.out.println("잘못된 정보");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
