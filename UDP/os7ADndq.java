package UDP;
/*
[Mã câu hỏi (qCode): os7ADndq].
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN004;99D9F604”
b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;z1,z2,...,z50” requestId là chuỗi ngẫu nhiên duy nhất
    z1 -> z50 là 50 số nguyên ngẫu nhiên
    c. Thực hiện tính số lớn thứ hai và số nhỏ thứ hai của thông điệp trong z1 -> z50 và gửi thông điệp lên server theo định dạng “requestId;secondMax,secondMin”
    d. Đóng socket và kết thúc chương trình
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class os7ADndq {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2207;
        
        String mess = ";B21DCCN603;os7ADndq";
        byte[] sendData = mess.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);
        System.out.println("Gui thanh cong");
        
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        System.out.println("nhan thanh cong");
        
        String receiveMess = new String(receivePacket.getData());
        System.out.println(receiveMess);
        String[] parts = receiveMess.split(";");
        
//        byte[] receiveData = new byte[1024];
//        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//        socket.receive(receivePacket);
//        
//        String receiveMess = new String(receivePacket.getData());
//        String[] parts = receiveMess.split(";");
        
        String requestId = parts[0];
        System.out.println(requestId);
        String data = parts[1];
        
        
        String[] tp = data.split(",");
        List<Integer> nums = new ArrayList<>();
        for(String n : tp){
            System.out.print(n + " ");
            int tmp = Integer.parseInt(n.trim());
            nums.add(tmp);
        }
        
        System.out.println("");
        Collections.sort(nums);
        int secondMin = nums.get(1);
        int secondMax = nums.get(nums.size()-2);
        String result = requestId + ";" + secondMax + "," + secondMin;
        System.out.println(result);
        byte[] responseData = result.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, serverAddress, serverPort);
        socket.send(responsePacket);
        
        socket.close();
         
        
    }
}
