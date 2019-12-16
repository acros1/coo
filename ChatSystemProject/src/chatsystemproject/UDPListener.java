/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
package chatsystemproject;

import java.net.*;
import java.util.ArrayList;

public class UDPListener implements Runnable {

	private ListenerThread listenerThread = null;
	private ClientThread clientThread = null; 

	public UDPListener(ListenerThread listenerThread, ClientThread clientThread) {
		this.listenerThread = listenerThread;
		this.clientThread = clientThread;
	}

	public void run() {
		try {
                    InetAddress ownAddr = InetAddress.getLocalHost();
                    DatagramSocket dgramSocket = new DatagramSocket(4000);
                    byte[] buffer = new byte[256];
                    DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                    while(true) {
                            String number = null;
                            // New user is connected, datagram reception
                            dgramSocket.receive(inPacket);
                            String data = new String(inPacket.getData(), 0, inPacket.getLength());
                            InetAddress clientAddr = inPacket.getAddress();
                            // If host receive is own broadcast packet, nothing to do
                            if (ownAddr != clientAddr) {
                                // If message is "alert", send number of user and own pseudo
                                if (data.equals("alert")) {
                                    String response = Integer.toString( listenerThread.getClientList().size() ) + "," + clientThread.getMainUserName();
                                    DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
                                    dgramSocket.send(outPacket);
                                }
                                // If the message is number and pseudo, register user until number = 0
                                else if ( data.indexOf(",") != (-1) ) {
                                    int separator = data.indexOf(",");
                                    String pseudo = null;
                                    // Only for the first message
                                    if (number == null) {
                                    // Finding number of users
                                        for (int i = 0 ; i < separator ; i++) {
                                            number += data.charAt(i);
                                        }
                                    }
                                    // Finding pseudo
                                    for (int i = (separator + 1) ; i <= data.length() ; i++ ) {
                                        pseudo += data.charAt(i);
                                    }
                                    // Add the user to the list
                                    sendUser(pseudo, clientAddr);
                                }
                                // Else message is a pseudo, if it's a not registered pseudo, add it to the users list
                                else if (isUserRegistered(data) == false) {
                                        sendUser(data, clientAddr);
                                        // Answering the new connection alert
                                        String response = clientThread.getMainUserName();
                                        DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
                                        dgramSocket.send(outPacket);
                                }
                            }
                    }
		} catch (Exception e) {
			e.printStackTrace();
		}  	

	}

	public void sendUser(String name, InetAddress addr) {
		listenerThread.addNewConnectedUser(name, addr);
	}

	public boolean isUserRegistered(String name) {
		return listenerThread.isUserRegistered(name);
	}

}

