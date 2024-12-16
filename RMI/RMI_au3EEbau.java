package RMI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.LinkedHashMap;
import java.util.Map;
/*
[Mã câu hỏi (qCode): au3EEbau].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
Giao diện từ xa:
public interface CharacterService extends Remote {
public String requestCharacter(String studentCode, String qCode) throws RemoteException;
public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}
Trong đó:
•	Interface CharacterService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Chuỗi đầu vào".
b. Thực hiện đếm tần số xuất hiện của mỗi ký tự trong chuỗi đầu vào và tạo ra chuỗi kết quả theo định dạng <Ký tự><Số lần xuất hiện>, sắp xếp theo thứ tự xuất hiện của các ký tự trong chuỗi.
Ví dụ: Chuỗi đầu vào "AAABBC" -> Kết quả: "A3B2C1".
c. Triệu gọi phương thức submitCharacter để gửi chuỗi kết quả trở lại server.
d. Kết thúc chương trình client.
*/
public class RMI_au3EEbau {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
        
        CharacterService characterService = (CharacterService) registry.lookup("RMICharacterService");
        
        String studentCode = "B21DCCN579";
        String qCode = "au3EEbau";
        
        String data = (String) characterService.requestCharacter(studentCode, qCode);
        System.out.println(data);
        
        Map<Character, Integer> result = new LinkedHashMap<>();
        
        for(char ch : data.toCharArray()){
            /*if(Character.isLetterOrDigit(ch))*/{
                result.put(ch, result.getOrDefault(ch, 0) + 1);
            }
        }
        String send = "";
        for(Map.Entry<Character, Integer> entry : result.entrySet()){
            send += String.valueOf(entry.getKey()) + entry.getValue();
        }
        System.out.println(send);
        
        characterService.submitCharacter(studentCode, qCode, send);
        
    }
}
