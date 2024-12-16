package onthi;

/*
[Mã câu hỏi (qCode): KiJZ4dC3].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;FF49DC02"
b.	Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ","
Ex: 1,3,9,19,33,20
c.	Thực hiện tìm giá trị khoảng cách nhỏ nhất của các phần tử nằm trong chuỗi và hai giá trị lớn nhất tạo nên khoảng cách đó. Gửi lên server chuỗi gồm "khoảng cách nhỏ nhất, số thứ nhất, số thứ hai". Ex: 1,19,20
d.	Đóng kết nối và kết thúc
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Ki4dC3 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);
        socket.setSoTimeout(5000);
        System.out.println("Connect succes");
        
        OutputStream writer = socket.getOutputStream();
        InputStream reader = socket.getInputStream();
        
        writer.write("B21DCCN603;KiJZ4dC3".getBytes());
        writer.flush();
        
        byte[] buff = new byte[1024];
        int data = reader.read(buff);
        String coverData = new String(buff, 0, data, StandardCharsets.UTF_8);
        String[] A = coverData.split(",");
        
        List<Integer> numList = new ArrayList<>();
        for(String num: A){
            numList.add(Integer.parseInt(num.trim()));
        }
        
        Collections.sort(numList);
        
        //Tính khoảng cách nhỏ nhất
        int dis = Integer.MAX_VALUE;
        int min=0, max=0;
        for (int i=1; i<numList.size(); i++){
            int tp_dis = numList.get(i) - numList.get(i-1);
            if(tp_dis <= dis){
                dis = tp_dis;
                min = numList.get(i-1);
                max = numList.get(i);
            }
        }
        String result = dis + "," + min + "," + max;
        writer.write(result.getBytes());
        writer.flush();
        
        writer.close();
        reader.close();
        socket.close();
    }
}
