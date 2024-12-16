package UDP;

/*
[Mã câu hỏi (qCode): S4PecjEm].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;5B35BCC1”
b.	Nhận thông điệp từ server theo định dạng “requestId;data” 
-	requestId là một chuỗi ngẫu nhiên duy nhất
-	data là chuỗi dữ liệu cần xử lý
c.	Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc 
i.	Ký tự đầu tiên của từng từ trong chuỗi là in hoa
ii.	Các ký tự còn lại của chuỗi là in thường
Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng “requestId;data”
d.	Đóng socket và kết thúc chương trình
*/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class S4PecjEm {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2208;
        
        String mess = ";B21DCCN603;S4PecjEm";
        byte[] sendData = mess.getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);
        System.out.println("gui thanh cong");
        
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        
        String receiveMess = new String(receivePacket.getData());
        String[] parts = receiveMess.split(";");
        
        String requestId = parts[0];
        String data = parts[1];
        System.out.println(data);
        
        String[] str = data.split(" ");
        String result = "";
        for(String tp : str){
            String tmp = tp.substring(0,1);
            tmp = tmp.toUpperCase();
            String tmp2 = tp.substring(1).toLowerCase();
            result = result + tmp + tmp2 +" ";
        }
        System.out.println(result);
        
        String responseMess = requestId + ";" + result;
        byte[] responseData = responseMess.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, serverAddress, serverPort);
        socket.send(responsePacket);
        
        socket.close();
 
    }   
    
    
    
    
    
}
