package Chapter_2.Item_9;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
- 자원을 닫아주기 위해 try-finally를 사용함
* */
public class EX_2 {
	//자원을 하나 사용하면 아래 형태가 될 것임
	static String firstLineOfFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			return br.readLine();
		} finally {
			br.close();
		}
	}
	
	//자원을 둘 이상 사용하면 아래처럼 너무 지져분해진다.
	static void copy(String src, String dst) throws IOException {
		InputStream in = new FileInputStream(src);
		try {
			OutputStream out = new FileOutputStream(dst);
			try {
				 byte[] buf = new byte[10];
				 int n;
				 while ((n = in.read(buf)) >= 0)
					 out.write(buf, 0, n);
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}
	
	/*
	try-with-resources를 사용해 위와 같은 문제점을 해결 할 수 있다.
	하지만 이 구조를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야 한다.
	- 닫아야 하는 자원을 뜻하는 클래스를 작성한다면 AutoCloseable을 반드시 구현해야 한다.
	* */
	
	//맨 처음 코드를 수정한 것
	static String resolveFirstLineOfFile(String path) throws IOException {
		try (
				BufferedReader br = new BufferedReader(new FileReader(path))
		) {
			return br.readLine();
		}
	}
	
	//복수 자원일 때 사용한 예시
	
	static void resolveCopy(String src, String dst) throws IOException {
		try (
				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dst)
		) {
			byte[] buf = new byte[10];
			int n;
			while ((n = in.read(buf)) >= 0)
				out.write(buf, 0, n);
		}
	}
}




