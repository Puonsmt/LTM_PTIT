package RMI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.List;
/*
[Mã câu hỏi (qCode): 9f36dxTF].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
Giao diện từ xa:
public interface DataService extends Remote {
public Object requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
Trong đó:
•	Interface DataService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một số nguyên dương lớn từ server, gọi là N.
b. Thực hiện phân rã số N thành các thừa số nguyên tố. Kết quả trả về là danh sách các thừa số nguyên tố của N.
Ví dụ: Với N = 84, kết quả là danh sách “2, 2, 3, 7”.
c. Triệu gọi phương thức submitData để gửi danh sách các thừa số nguyên tố đã tìm được trở lại server.
d. Kết thúc chương trình client.
*/
public class RMI_9f36dxTF {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
        
        DataService dataService = (DataService) registry.lookup("RMIDataService");
        
        String studentCode = "B21DCCN579";
        String qCode = "9f36dxTF";
        
        int n = (int) dataService.requestData(studentCode, qCode);
        //int n = Integer.parseInt(response.trim());
        System.out.println(n);
        
        List<Integer> result = new ArrayList<>();
        int i = 2;
        while(n != 1){
            if(n%i!=0){
                i++;
            } else{
                n /= i;
                result.add(i);
            }
        }
        for(int a: result){
            System.out.print(a + ",");
        }
        System.out.println("");
        
        dataService.submitData(studentCode, qCode, result);
        
    }
}
