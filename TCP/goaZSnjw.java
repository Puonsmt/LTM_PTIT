package onthi;

/*
[Mã câu hỏi (qCode): goaZSnjw].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau: 
a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;BAA62945"
b.	Nhận một chuỗi ngẫu nhiên từ server
Ví dụ: dgUOo ch2k22ldsOo
c.	Liệt kê các ký tự (là chữ hoặc số) xuất hiện nhiều hơn một lần trong chuỗi và số lần xuất hiện của chúng và gửi lên server
Ví dụ: d:2,O:2,o:2,2:3,
d.	Đóng kết nối và kết thúc chương trình.
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class goaZSnjw {
    public static void main(String[] args) throws IOException {
        String add = "203.162.10.109";
        int port = 2208;
        Socket socket = new Socket(add, port);
        socket.setSoTimeout(5000);
        System.out.println(socket);
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream() ));
        
        writer.write("B21DCCN603;goaZSnjw");
        writer.newLine();
        writer.flush();
        
        String str = reader.readLine();
        String result = "";
        System.out.println(str);
        if (str == null){
            System.out.println("Khong nhan duoc du lieu tu server");
            socket.close();
        }
        
        Map<Character, Integer> charCountMap = new LinkedHashMap<>();

        // Duyệt qua từng ký tự trong chuỗi
        for (char ch : str.toCharArray()) {
            // Chỉ xử lý chữ cái và số
            if (Character.isLetterOrDigit(ch)) {
                charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
            }
        }

        // In các ký tự xuất hiện nhiều hơn 1 lần
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (entry.getValue() > 1) {
                result = result + entry.getKey() + ":" + entry.getValue() + ",";
            }
        }
        
        System.out.println(result);
        writer.write(result);
        writer.newLine();
        writer.flush();
        
        writer.close();
        reader.close();
        socket.close();
    }
}
