package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class matchRequestFrame extends JFrame{
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 600;
	private String opponentNickName;//상대편 정보를 불러오기 위한 index number
	private UserInfo userInfo = new UserInfo();
	
	public matchRequestFrame(String opponentNickName) {
		super("User Info");
		this.opponentNickName = opponentNickName;
		
		//Frame 기본 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);//화면 가운데 정렬
		
		//Frame 내부 배치 설정
		containerSetup();
		
		//창 보이기
		setVisible(true);
	}
	
	//------------------------------
	//Frame 내부 배치 설정
	//------------------------------
	private void containerSetup() {
		Container mainCon = this.getContentPane();
		JFrame thisFrame = this;
		mainCon.setLayout(null);
		
		//------------------------------
		//유저정보
		//------------------------------
		JLabel userInfoF = new JLabel();
		userInfoF.setBounds(10, 10, 360, 360);
		//배경색상설정
		userInfoF.setOpaque(true);
		userInfoF.setBackground(Color.white);
		//글자정렬, 글자크기 변경
		userInfoF.setVerticalAlignment(JLabel.TOP);
		userInfoF.setFont(userInfoF.getFont().deriveFont(12.0f));	
		//유저정보
		String[] userInfoStrList = userInfo.loadUserInfo(opponentNickName);
		String userInfoStr = null;
		userInfoStr = "<html>";
		for(int i = 0; i < userInfoStrList.length; i++)
			userInfoStr += userInfoStrList[i] + "<br/>";
		userInfoStr += "</html>";
		userInfoF.setText(userInfoStr);
		final String userInfoStrTemp = userInfoStr;
		
		//------------------------------
		//수락 버튼
		//------------------------------
		JButton matchBtn = new JButton("수락");
		matchBtn.setBounds(10, 400, 360, 40);
		
		//수락 버튼 클릭시
		ActionListener matchListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DataOutputStream outToServer =
							new DataOutputStream(ClientMain.clientSocket.getOutputStream());//서버로 출력
					outToServer.writeBytes("204" + "\n");
					new PrivateChatFrame(opponentNickName, thisFrame, userInfoStrTemp);//1대1 대기실로 이동
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}								
					
				}
		};
		matchBtn.addActionListener(matchListener);
		
		//------------------------------
		//거절 버튼
		//------------------------------
		JButton rejectionBtn = new JButton("거절");
		rejectionBtn.setBounds(10, 450, 360, 40);
		
		//거절 버튼 클릭시
		ActionListener rejectionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DataOutputStream outToServer =
							new DataOutputStream(ClientMain.clientSocket.getOutputStream());//서버로 출력
					outToServer.writeBytes("205"+ "\n");
					dispose();
								
					} catch (IOException e1) {
						e1.printStackTrace();
					}								
							
				}
		};
		rejectionBtn.addActionListener(rejectionListener);
		
		this.add(userInfoF);
		this.add(matchBtn);
		this.add(rejectionBtn);
	}
}
