package client;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import java.net.*;




public class SignUp {
	
	
	
	public SignUp() {
		
	}
	
	public void SignUp_pro(JFrame signUpFrame,int check, String id, String pw,String name,String nickName,String email,String SNS)
	{
		
		Socket clientSocket = ClientMain.clientSocket;
		
		if(clientSocket == null) {
			System.out.println("서버 연결 오류 발생");
			return;
		}
		
	
		try {
			
			DataOutputStream outToServer =
					new DataOutputStream(clientSocket.getOutputStream());//서버로 출력
			BufferedReader inFromServer =
					new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));//서버로부터 입력
			
		
			outToServer.writeBytes("101" + id + "/" + pw + "/" +name + "/"+nickName + "/"+email + "/"+SNS+ "\r\n");//프로토콜 + id + password
			
			//------------------------------
			//서버에서 답변받기.
			//------------------------------
			int result = inFromServer.read();
			if(result == 1)//회원가입 성공
			{
				
				JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다.");
				signUpFrame.setEnabled(false);
				signUpFrame.setVisible(false);
			}
			else//회원가입 실패
			{
				
				JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
				System.out.println("잘못된 정보");
				//outToServer.writeBytes("101" + id + "/" + pw + "/" +name + "/"+email + "\r\n");//프로토콜 + id + password + name + email
				
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}		
     }
     
	}
