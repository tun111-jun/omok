package client;

import java.net.*;
import java.io.*;

/*
 * Client의 Main 파일
 * Login창 열기와 서버와의 연결기능을 담당
 */

public class ClientMain {
	public static Socket clientSocket = null;//클라이언트 소켓
	
	//------------------------------
	//Client Main
	//------------------------------
	public static void main(String[] args) {
		//------------------------------
		//서버접속
		//------------------------------
		try {
			connectServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//------------------------------
	//서버접속
	//------------------------------
	public static void connectServer() throws IOException {
		//------------------------------
		//서버정보
		//------------------------------
		String serverIP = "127.0.0.1";//IP
		int nPort = 5274;//port
		
		//------------------------------
		//서버연결 시도
		//------------------------------
		clientSocket = new Socket(serverIP, nPort);
		
		//------------------------------
		//로그인 창 열기
		//------------------------------
		new LoginFrame();
		
	}
}
