package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * 로그인 후 접속하는 대기방 레이아웃 관련 클래스
 */

public class MainChatFrame extends JFrame implements Runnable{
	private static final int FRAME_WIDTH = 1100;
	private static final int FRAME_HEIGHT = 720;
	private MainChat mainChat = new MainChat();
	
	private JTextArea chatField = null;	
	private JList<String> userList = null;
	
	public UserInfoFrame userInfoFrame = null;
	
	Thread t = null;
	
	public MainChatFrame() {
		super("Main");
		
		//Frame 기본 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);//화면 가운데 정렬
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		//chatField.setBounds(20, 20, 800, 550);
		chatField.setEditable(false);
		//배경색상설정
		chatField.setOpaque(true);
		chatField.setBackground(Color.white);
		//글자크기 변경
		chatField.setFont(chatField.getFont().deriveFont(15.0f));
		JScrollPane scrollPane=new JScrollPane(chatField,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20,20,800,550);
		//테스트 메시지
		chatField.setText("");
		
		//------------------------------
		//채팅창
		//------------------------------
		JTextField chatMsg = new JTextField();
		chatMsg.setBounds(20, 600, 700, 40);
		
		//------------------------------
		//전송버튼
		//------------------------------
		JButton sendBtn = new JButton("전송");
		sendBtn.setBounds(730, 600, 90, 40);
		
		//전송 클릭시
		ActionListener sendListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainChat.sendMsg(chatMsg.getText(),thisFrame);//서버로 메시지 전송
				chatMsg.setText("");//입력값 비우기
			}
		};
		sendBtn.addActionListener(sendListener);
		
		//------------------------------
		//참여자 목록
		//------------------------------
		DefaultListModel<String> model = new DefaultListModel<String>();
		userList = new JList<String>(model);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//한번에 한명의 유저만 클릭만 허용
		userList.setBounds(850, 20, 200, 550);
		
		//참여자 클릭시
		ListSelectionListener userListListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!userList.getValueIsAdjusting()) { //없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
					try {
						DataOutputStream outToServer =
								new DataOutputStream(ClientMain.clientSocket.getOutputStream());//서버로 출력
						outToServer.writeBytes("104" + "openInfoRequest" + "\n");//유저정보 창 열기 요청
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println(userList.getSelectedValue());//눌린 참여자 닉네임 출력
					
				}
			}
		};
		userList.addListSelectionListener(userListListener);
		
		//this.add(chatField);
		this.add(scrollPane);
		this.add(chatMsg);
		this.add(sendBtn);
		this.add(userList);		
	}
	
	public void update() {
		
	}

	@Override
	public void run() {

		int check = 0;
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
					
					if(chatContent[0].equals("300"))//채팅 요청
						chatField.append(chatContent[1] + "> " + chatContent[2] + "\n");
					else if(chatContent[0].equals("301")) {
						userInfoFrame = new UserInfoFrame(userList.getSelectedValue());//유저정보 창 열기
					}
					else if(chatContent[0].equals("201"))//신규 로그인
					{
						DefaultListModel<String> model = (DefaultListModel<String>) userList.getModel();
						model.clear();
						for(int i = 1; i < chatContent.length; i++)//목록 업데이트
							model.addElement(chatContent[i]);
						userList.updateUI();
					}
					else if(chatContent[0].equals("203"))//대결 요청을 받음
					{
						if(chatContent[1].equals(Login.myID))
						{
							check = 1;
							new matchRequestFrame(chatContent[2]);
						}
					}
					else if(chatContent[0].equals("204")) {//대결 요청을 수락한 경우
						String[] userInfoStrList = UserInfo.loadUserInfo(userInfoFrame.opponentNickName);
						String userInfoStr = "<html>";
						for(int i = 0; i < userInfoStrList.length; i++)
							userInfoStr += userInfoStrList[i] + "<br/>";
						userInfoStr += "</html>";
						final String userInfoStrTemp = userInfoStr;
						
						PrivateChatFrame privateChatFrame = new PrivateChatFrame(userInfoFrame.opponentNickName, userInfoFrame, userInfoStrTemp);//1대1 대기실로 이동
						while(privateChatFrame.getComponents() != null) {
							Thread.sleep(100);
						}
						
					}
					else if(chatContent[0].equals("205")){//대결 요청을 거부한 경우
						if(check == 0)
							JOptionPane.showMessageDialog(null, "상대가 대결요청을 거부하였습니다.");
						check = 0;
					}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
