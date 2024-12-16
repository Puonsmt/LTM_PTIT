package onthi;

/*
[Mã câu hỏi (qCode): jEaiZUXA].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng(ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:
Biết lớp TCP.Student gồm các thuộc tính (id int,code String, gpa float, gpaLetter String) và 
private static final long serialVersionUID = 20151107;
a.	Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D059A3F"
b.	Nhận một đối tượng là thể hiện của lớp TCP.Student từ server
c.	Chuyển đổi giá trị điểm số gpa của đối tượng nhận được sang dạng điểm chữ và gán cho gpaLetter.  Nguyên tắc chuyển đổi
i.	3.7 – 4 -> A
ii.	3.0 – 3.7 -> B
iii.	2.0 – 3.0 -> C
iv.	1.0 – 2.0 -> D
v.	0 – 1.0 -> F
d.     Gửi đối tượng đã được xử lý ở trên lên server.
e.     Đóng kết nối và kết thúc chương trình
*/
import TCP.Student;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author PC
 */
public class jEaiZUXA {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("203.162.10.109", 2209);
        socket.setSoTimeout(5000);
        
        System.out.println("connect success");
        
        ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
        
        //a. Gui mot doi tuong la chuoi 
        String message = "B21DCCN603;jEaiZUXA";
        writer.writeObject(message);
        writer.flush();
        
        //b. Nhan mmot doi tuong la Student
        Student student = (Student) reader.readObject();
        System.out.println(student);
        
        //c. Chuyen doi gia tri gpa sang diem chu va gan cho gpaLetter
        if (student.getGpa() >= 3.7 && student.getGpa() <= 4.0) {
            student.setGpaLetter("A");
        } else if (student.getGpa() >= 3.0 && student.getGpa() < 3.7) {
            student.setGpaLetter("B");
        } else if (student.getGpa() >= 2.0 && student.getGpa() < 3.0) {
            student.setGpaLetter("C");
        } else if (student.getGpa() >= 1.0 && student.getGpa() < 2.0) {
            student.setGpaLetter("D");
        } else {
            student.setGpaLetter("F");
        }
        System.out.println( student);
        
        //d. Gui doi tuong student toi server
        writer.writeObject(student);
        writer.flush();
        
        writer.close();
        reader.close();
        socket.close();  
    }
}
