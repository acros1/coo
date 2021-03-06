package chatsystemproject;


import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.Enumeration;

import java.net.NetworkInterface;
import javax.swing.JOptionPane;

public class UDPListener implements Runnable {

    private ListenerThread listenerThread = null;
    private ClientThread clientThread = null; 
    private boolean pseudoGood = false;



    public UDPListener(ListenerThread listenerThread, ClientThread clientThread) {
            this.listenerThread = listenerThread;
            this.clientThread = clientThread;
    }

    public void run() {
        try {
            // Creating datagram socket to send UDP messages
            DatagramSocket dgramSocket = new DatagramSocket(4000);
            // Creating buffer and packet to receive UDP messages
            byte[] buffer = new byte[256];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

            while(true) {
                // Datagram reception
                dgramSocket.receive(inPacket);
                String data = new String(inPacket.getData(), 0, inPacket.getLength());
                InetAddress clientAddr = inPacket.getAddress();
                // If received broadcast is coming from localhost, don't process it
                if ( isItOwnIP(clientAddr) == false ) {

                    // If first char is "#" pseudo has to be deleted from the list (disconection or pseudo already used)
                    if ( Character.toString( data.charAt(0) ).compareTo("#") == 0 ) {
                        String pseudoToDelete = data.substring(1);
                        // Ending server thread associated to user
                        
                        // Delete pseudo from the users list
                        listenerThread.deleteUser(pseudoToDelete);
                    }

                    // If first char is "|", then message is an answer of pseudo broadcast
                    // Answer is format like "|mainUserPseudo|boolean|clientPseudo"
                    else if ( Character.toString( data.charAt(0) ).compareTo("|") == 0 ) {
                        String[] dataSplit = data.split("\\|"); // String array, each element is text between "|"
                        // dataSplit[0] is empty, dataSplit[1] = mainUserPseudo, dataSplit[2] = boolean, dataSplit[3] = clientPseudo, dataSplit[4] = clientLogin
                        System.out.println("0 : \""+dataSplit[0]+"\" 1 : \""+dataSplit[1]+"\" 2 : \""+dataSplit[2]+"\" 3 : \""+dataSplit[3]+"\" 4 : \""+dataSplit[4]+"\"");
                        // if main user pseudo is not equal to dataSplit[1], pseudo has already been changed, then don't process message
                        if ( dataSplit[1].equals(clientThread.getMainUserPseudo()) ) {

                            // if boolean = false, client already has main user pseudo, then ask for a new pseudo
                            if ( dataSplit[2].equals("false") ) {

                                /*clientThread.changePseudoState(false);
                                System.out.println("isPseudoOk = " + clientThread.getIsPseudoOk());*/
                                String response = "#" + clientThread.getMainUserPseudo();
                                DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
                                dgramSocket.send(outPacket);
                                // Asking for new pseudo
                                String newPseudo = JOptionPane.showInputDialog("Please enter your new pseudo");
                                //System.out.println("Your new pseudo :");
                                //String newPseudo = scan.nextLine();
                                clientThread.changePseudo(newPseudo);

                                // if clientUser is not in the list yet, add him
                                if ( listenerThread.isUserExist(dataSplit[3]) == false ) {
                                        listenerThread.addUser(dataSplit[3], dataSplit[4], clientAddr);
                                        // The list has probably been modified so we update it
                                        this.clientThread.getApplicationWindow().updateUsersList();
                                }

                            }

                            // boolean = true, then pseudo is not used by this client so just add client to the users list
                            else {
                                /*clientThread.changePseudoState(true);
                                System.out.println("isPseudoOk = " + clientThread.getIsPseudoOk());*/
                                // if clientUser is not in the list yet, add him
                                this.pseudoGood = true;
                                if ( listenerThread.isUserExist(dataSplit[3]) == false ) {
                                        listenerThread.addUser(dataSplit[3], dataSplit[4], clientAddr);
                                        // The list has probably been modified so we update it
                                        System.out.println(this.clientThread.getApplicationWindow());
                                        this.clientThread.getApplicationWindow().updateUsersList();
                                }
                            }

                        }

                    }
                    // Else it's a pseudo broadcast, then check if pseudo is not equal as main user pseudo
                    else {
                        String[] dataSplit = data.split("\\|"); // String array, each element is text between "|"
                        // dataSplit[0] is pseudo, dataSplit[1] = login
                        // if pseudo is the same as main user pseudo
                        if ( dataSplit[0].equals(clientThread.getMainUserPseudo()) ) {
                            // Answering with false boolean
                            // Pseudo is already mine, sending false
                            String response = "|" + dataSplit[0] + "|" + "false" + "|" + clientThread.getMainUserPseudo() + "|" + clientThread.getLogin();
                            DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
                            dgramSocket.send(outPacket);
                        }
                        // else pseudo is not the same
                        else {
                            // Answering with true boolean
                            // Pseudo is different of mine, sending true
                            String response = "|" + dataSplit[0] + "|" + "true" + "|" + clientThread.getMainUserPseudo() + "|" + clientThread.getLogin();
                            DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
                            dgramSocket.send(outPacket);
                            // Add user to the list
                            if ( listenerThread.isUserExist(dataSplit[0]) == false ) {
                                listenerThread.addUser(dataSplit[0], dataSplit[1], clientAddr);
                                // The list has probably been modified so we update it
                                this.clientThread.getApplicationWindow().updateUsersList();
                            }
                        }
                    }
                    this.clientThread.getApplicationWindow().updateUsersList();
                }
                
            }
        } catch (IOException e) {
                e.printStackTrace();
        }  	

    }

    // Function to test if clientAddr is equal to localhost
    public boolean isItOwnIP(InetAddress clientAddr) {
        try {
            // Getting every interface of the computer
            Enumeration e = NetworkInterface.getNetworkInterfaces();

            while(e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                // Getting address of every interfaces
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    // clientAddr is formated like "/X.X.X.X"
                    // If interface address is equal to clientAddr, return true
                    if ( clientAddr.toString().equals("/" + i.getHostAddress()) ) {
                            return true;
                    }
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return false;
    }

    // Function to test if data is an Integer
    public boolean isDataInteger(String data) {
        try {
                Integer.parseInt(data);
        } catch (NumberFormatException e) {
                return false;
        }
        return true;
    }

    public boolean isPseudoGood(){
        return pseudoGood;
    }

}
