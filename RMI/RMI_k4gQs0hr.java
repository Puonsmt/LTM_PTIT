package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
[Mã câu hỏi (qCode): k4gQs0hr].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
Giao diện từ xa:
public interface ByteService extends Remote {
public byte[] requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
}
Trong đó:
•	Interface ByteService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server, đại diện cho một chuỗi văn bản ASCII.
b. Thực hiện mã hóa Caesar cho mảng dữ liệu nhị phân bằng cách dịch chuyển mỗi byte trong mảng đi một số bước cố định trong bảng mã ASCII. Số bước dịch chuyển là số ký tự ASCII trong mảng dữ liệu.
    Ví dụ: Nếu dữ liệu nhị phân nhận được là [72, 101, 108, 108, 111] (tương ứng với chuỗi "Hello"), chương trình sẽ thực hiện mã hóa Caesar với độ dịch là 5. Kết quả mã hóa là mảng [77, 108, 113, 113, 116], tương ứng với chuỗi "Mlqqt".
c. Triệu gọi phương thức submitData để gửi mảng dữ liệu đã được mã hóa bằng Caesar trở lại server.
d. Kết thúc chương trình client.
 */
public class RMI_k4gQs0hr {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);

        ByteService byteService = (ByteService) registry.lookup("RMIByteService");

        String studentCode = "B21DCCN603";
        String qCode = "k4gQs0hr";

        //Nhan du lieu
        byte[] response = byteService.requestData(studentCode, qCode);
        System.out.println("Nhan du lieu thanh cong");

        //Ma hoa
        int len = response.length;
        byte[] result = new byte[len];
        for(byte b : response){
            System.out.print(b);
        }
        System.out.println();

        for(int i=0; i<len; i++){
            result[i] = (byte) ((response[i]+len)%256);
        }
        for(byte b : result){
            System.out.print(b);
        }
        System.out.println();

        //Gui du lieu
        byteService.submitData(studentCode, qCode, result);
        System.out.println("Gui du lieu thanh cong");
    }
}
