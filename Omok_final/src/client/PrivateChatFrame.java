package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.*;

/*
 * 1대1 채팅방 Frame class
 */

public class PrivateChatFrame extends JFrame implements Runnable{
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private String opponentNickName;//상대편 정보를 불러오기 위한 index number
	private PrivateChat privateChat = new PrivateChat();
	private String userInfoStr;
	
	private JTextArea chatField = null;
	Thread t = null;
	
	public PrivateChatFrame(String opponentNickName, JFrame userInfoFrame, String userInfoStr) {
		super("Private Chat");
		this.opponentNickName = opponentNickName;
		this.userInfoStr = userInfoStr;
		
		//유저정보 창 닫기
		userInfoFrame.setEnabled(false);
		userInfoFrame.setVisible(false);
		
		//Frame 기본 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);//화면 가운데 정렬
		
		//Frame 내부 배치 설정
		containerSetup();
		
		//창 보이기
		setVisible(true);
		
		t = new Thread(this);
		t.start();
	}
	
	//------------------------------
	//Frame 내부 배치 설정
	//------------------------------
	private void containerSetup() {
		Container mainCon = this.getContentPane();
		JFrame thisFrame = this;
		mainCon.setLayout(null);
		
		//------------------------------
		//채팅 내용
		//------------------------------
		chatField = new JTextArea();
		chatField.setBounds(20, 20, 500, 450);
		chatField.setEditable(false);
		//배경색상설정
		chatField.setOpaque(true);
		chatField.setBackground(Color.white);
		//글자크기 변경
		chatField.setFont(chatField.getFont().deriveFont(15.0f));
				
		//------------------------------
		//채팅창
		//------------------------------
		JTextField chatMsg = new JTextField();
		chatMsg.setBounds(20, 480, 400, 40);
				
		//------------------------------
		//전송버튼
		//------------------------------
		JButton sendBtn = new JButton("전송");
		sendBtn.setBounds(430, 480, 90, 40);
				
		//전송 클릭시
		ActionListener sendListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				privateChat.sendMsg(chatMsg.getText());//서버로 메시지 전송
				chatMsg.setText("");//입력값 비우기
			}
		};
		sendBtn.addActionListener(sendListener);
		
		//------------------------------
		//대결상대 정보
		//------------------------------
		JLabel oppInfo = new JLabel();
		oppInfo.setBounds(550, 20, 200, 450);
		//배경색상설정
		oppInfo.setOpaque(true);
		oppInfo.setBackground(Color.white);
		//글자정렬, 글자크기 변경
		oppInfo.setVerticalAlignment(JLabel.TOP);
		oppInfo.setFont(chatField.getFont().deriveFont(12.0f));	
		//테스트 메시지
		oppInfo.setText(userInfoStr);
		
		//------------------------------
		//대결준비 버튼
		//------------------------------
		JButton readyBtn = new JButton("준비");
		readyBtn.setBounds(550, 480, 200, 40);
		
		this.add(chatField);
		this.add(chatMsg);
		this.add(sendBtn);
		this.add(oppInfo);
		this.add(readyBtn);
	}

	@Override
	public void run() {
		while(true) {
			Socket clientSocket = ClientMain.clientSocket;
			if(clientSocket == null)
				return;
			
			try {
				BufferedReader inFromServer =
						new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream()));//서버로부터 입력

				
					String chatFromServer = inFromServer.readLine();
					String[] chatContent = chatFromServer.split("/");
					System.out.println(chatContent[1]);
					
					if(chatContent[0].equals("401") && (chatContent[1].equals(opponentNickName) || chatContent[3].equals(Login.myID))){//상대방 혹은 나의 메시지인 경우 출력
						chatField.append(chatContent[1] + "> " + chatContent[2] + "\n");
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
