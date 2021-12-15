package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * User Info Frame의 관련 기능 클래스
 * user 정보를 불러오는 기능
 */

public class UserInfo {
	
	public UserInfo() {
		
	}
	
	//------------------------------
	//유저정보를 불러온 후 string 배열로 리턴
	//------------------------------
	static public String[] loadUserInfo(String userNickName) {
		Socket clientSocket = ClientMain.clientSocket;
		
		if(clientSocket == null) {
			System.out.println("서버 연결 오류 발생");
			return null;
		}
		
		//------------------------------
		//서버로 닉네임 전송
		//데이터 불러오기
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
			outToServer.writeBytes("200" + userNickName + "\n");//프로토콜 + 닉네임
			
			//------------------------------
			//서버로부터 데이터 받기
			//------------------------------
			String tempStr = inFromServer.readLine();
			String[] resultStr = tempStr.split("/");
			String[] indexStr = {"닉네임", "ID", "이름", "이메일", "SNS", "접속 횟수", "마지막 접속 IP", "마지막 접속 시간", "이긴 횟수", "비긴 횟수", "진 횟수"};
			for(int i = 0; i < resultStr.length; i++)
				indexStr[i] += ": " + resultStr[i];
			
			return indexStr;
			
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
