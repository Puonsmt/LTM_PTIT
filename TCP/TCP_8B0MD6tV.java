package onthi;

/*
[Mã câu hỏi (qCode): 8B0MD6tV].  Một chương trình servercho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng chương trình client tương tác với server bằng các byte stream (DataInputStream/DataOutputStream) để trao đổi thông tin theo trình tự sau:

a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B10DCCN000;0D135D6A".

b. Nhận từ server một số nguyên n, là số lần tung xúc xắc. Ví dụ: Nếu bạn nhận được n = 21 từ máy chủ, có nghĩa bạn sẽ nhận giá trị tung xúc xắc 21 lần.

b. Nhận từ server các giá trị sau mỗi lần tung xúc xắc. Ví dụ: Server gửi lần lượt 21 giá trị là 1,6,4,4,4,3,2,6,3,4,5,4,5,2,4,5,4,6,1,5,5

c. Tính xác suất xuất hiện của các giá trị [1,2,3,4,5,6] khi tung xúc sắc và gửi lần lượt xác suất này (dưới dạng float) lên server theo đúng thứ tự. Ví dụ gửi lên server lần lượt 6 giá trị là 0.0952381, 0.0952381, 0.0952381, 0.33333334, 0.232209524, 0.14285715

d. Đóng kết nối và kết thúc chương trình.
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCP_8B0MD6tV {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);
        socket.setSoTimeout(5000);
        System.out.println("Connect to server");
        
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        DataInputStream reader = new DataInputStream(socket.getInputStream());
        
        writer.writeUTF("B21DCCN603;8B0MD6tV");
        writer.flush();
        
        int n = reader.readInt();
        System.out.println(n);
        //String str = reader.readUTF();
        //System.out.println(str);
        int[] A = new int[n+1];
        for(int i=0; i<n; i++){
            A[i] = reader.readInt();
            System.out.print(A[i] + " ");
        }
        System.out.println("");
        
        
        int[] P = new int[7];
        for(int i=1; i<7; i++){
            P[i]=0;
        }
        for(int i=0; i<n; i++){
            P[A[i]] ++;
        }
        
        for(int i=1; i<7; i++){
            System.out.print(P[i] + " ");
        }
        System.out.println("");
        

        for (int i = 1; i < 7; i++) {
            float probability = (float) P[i] / n;
            writer.writeFloat(probability);
            writer.flush();
        }
        
        writer.close();
        reader.close();
        socket.close(); 
    }
    
}
