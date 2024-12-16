package UDP;
/*
[Mã câu hỏi (qCode): 0iqsqzeD].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
•	Tên đầy đủ lớp: UDP.Student
•	Các thuộc tính: id String,code String, name String, email String
•	02 Hàm khởi tạo:
o	public Student(String id, String code, String name, String email)
o	public Student(String code)
•	Trường dữ liệu: private static final long serialVersionUID = 20171107
Thực hiện:
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
b.	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
c.	Yêu cầu:
-	Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
-	Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
-	Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
d.	Đóng socket và kết thúc chương trình.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP_0iqsqzeD {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2209;
        
        String mess = ";B21DCCN603;0iqsqzeD";
        byte[] sendData = mess.getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);
        System.out.println("Gui thanh cong");
        
        byte[] responseBuffer = new byte[1024];
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
        socket.receive(responsePacket);
        System.out.println("Nhan thanh cong");
        
        byte[] receivedData = responsePacket.getData();
        String requestId = new String(receivedData, 0, 8).trim();
        byte[] studentData = new byte[responsePacket.getLength()-8];
        System.arraycopy(receivedData, 8, studentData, 0, studentData.length);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(studentData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student student = (Student) ois.readObject();
        
        String name = formatName(student.getName()).trim();
        student.setName(name);
        System.out.println(name);
        String email = formatEmail(student.getName()).trim();
        student.setEmail(email);
        System.out.println(email);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(student);
        oos.flush();
        
        byte[] result = new byte[8 + baos.size()];
        System.arraycopy(requestId.getBytes(), 0, result, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, result, 8, baos.size());
        DatagramPacket resultPacket = new DatagramPacket(result, result.length, serverAddress, serverPort);
        socket.send(resultPacket);
        
        
        //socket.close();
   
    }
    
    public static String formatName(String name){
        String[] str = name.split("\\s+");
        String result = "";
        for(String tp : str){
            String tmp = tp.substring(0,1);
            tmp = tmp.toUpperCase();
            String tmp2 = tp.substring(1).toLowerCase();
            result = result + tmp + tmp2 +" ";
        }
        return result;
    }
    
    public static String formatEmail(String name){
        String[] str = name.split(" ");
        StringBuilder result = new StringBuilder(str[str.length - 1]);
        for (int i = 0; i < str.length - 1; i++) {
            result.append(str[i].substring(0, 1));
        }
        result = new StringBuilder(result.toString().toLowerCase());
        result.append("@ptit.edu.vn");
        return result.toString();
        
//        String[] str = name.split(" ");
//        String result = str[str.length-1];
//        for(int i=0; i<str.length-1; i++){
//            String tmp = str[i].substring(0,1);
//            result += tmp;
//            
//        }
//        result = result.toLowerCase();
//        result += "@ptit.edu.vn";
//        return result;
    }
    
}
