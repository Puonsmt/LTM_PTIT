package RMI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
/*
[Mã câu hỏi (qCode): xEndCoMp].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
Giao diện từ xa:
public interface DataService extends Remote {
public Object requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
Trong đó:
•	Interface DataService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận hai số nguyên dương N và K từ server, đại diện cho khoảng cần kiểm tra (N <= số < K).
b. Xác định tất cả các số nguyên đối xứng (Palindrome Number) trong khoảng từ N đến K. Kết quả trả về là danh sách các số đối xứng thỏa mãn yêu cầu.
Ví dụ: Với N = 50 và K = 150, kết quả là [55, 66, 77, 88, 99, 101, 111, 121, 131, 141].
c. Triệu gọi phương thức submitData để gửi đối tượng List<Integer> danh sách các số nguyên đối xứng đã tìm được trở lại server.
d. Kết thúc chương trình client.
*/

public class RMI_xEndComp {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        //Ket noi
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
        
        //Lay doi tuong tu xa
        DataService dataService = (DataService) registry.lookup("RMIDataService");
        
        //Goi phuong thuc requestData de nhan N va K
        String studentCode = "B21DCCN603";
        String qCode = "xEndCoMp";
        String response = (String) dataService.requestData(studentCode, qCode);
        response = response.replace(";", "");
        String[] range = response.split("\\s+");
        
        
        int N = Integer.parseInt(range[0]);
        int K = Integer.parseInt(range[1]);
        
        List<Integer> result = findSoDoiXung(N, K);
        
        //Gui lai server
        dataService.submitData(studentCode, qCode, result);
        
    }
    
    public static List<Integer> findSoDoiXung(int n, int k){
        List<Integer> result = new ArrayList<>();
        if(n>k){
            int tp = n;
            n = k;
            k=n;
        }
        for(int i=n; i<=k; i++){
            if(checkDoiXung(i)){
                result.add(i);
            }
        }
        return result;
    }
    
    public static boolean checkDoiXung(int n){
        String s = Integer.toString(n);
        int len = s.length();
        for(int i=0; i< len/2; i++){
            if(s.charAt(i) != s.charAt(len-i-1)){
                return false;
            }
        }
        return true;
    }
}
