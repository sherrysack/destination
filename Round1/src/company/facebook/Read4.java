package company.facebook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * int read4(char[] buf);
 * 
 * 有  read4K_1(),  read4k_2(),  分别从两个文件读，两个文件巨大内存装不下，你最后要返回两个文件内容是不是一样
 */
public class Read4 {

	public int read(char[] buf, int n) {
		List<Character> list = new ArrayList<Character>();

		while (list.size() != n) {
			char[] arr = new char[4];

			int k = read4(arr);

			for (int i = 0; i < k && list.size() < n; i++) {
				list.add(arr[i]);
			}

			if (k < 4) {
				// No more
				break;
			}
		}

		return list.size();
	}
	
	// 有  read4K_1(),  read4k_2(),  分别从两个文件读，两个文件巨大内存装不下，你最后要返回两个文件内容是不是一样
	boolean isSameRead4K() {
		while (true) {
			char[] arr1 = new char[4000];
			char[] arr2 = new char[4000];
			
			int count1 = read4K_1(arr1);
			int count2 = read4K_2(arr2);
			
			if (count1 != count2) {
				return false;
			}
			
			int pos = 0;
			while (pos < count1) {
				if (arr1[pos] != arr2[pos]) {
					return false;
				}
				pos++;
			}
			
			if (count1 < 4000 && count2 < 4000) { // end.
				return true;
			}
		}
	}

	/* 这个代码写的很好
	 * // Read4K - Given a function which reads from a file or network stream
	 * up to 4k at a time, give a function which can satisfy requests for
	 * arbitrary amounts of data 
	 * 
	 * private int read4K(char[] buf) { // GIVEN }
	 * 
	 * // IMPLEMENT: public int read(char[] buf, int toRead) { } 
	 * Due to network latency, if the read4K method return a value < 4k, it does not
	 * necessarily mean that we reach the End of File (EOF), in this case, you
	 * should continue to read the file until you reach toRead or EOF.
	 */
	public int read4(char[] buf, int toRead) {
		int index = 0;
		boolean EOF = false;
		
		while (index < toRead && !EOF) {
			char[] arr = new char[4];
			
			int now = read4K(arr);
			
			for (int i = 0; i < now && index < toRead; i++) {
				buf[index++] = arr[i];
			}
			
			if (index > 0 && buf[index - 1] == EOF) { // 直到读到EOF
				EOF = true;
			}
		}
		
		return index;
	}

	LinkedList<Character> leftOver = new LinkedList<Character>();

	public int readMultipleTimes(char[] buf, int n) {
		int index = 0;
		List<Character> list = new ArrayList<Character>();

		// First read from leftover
		while (index < n && !leftOver.isEmpty()) {
			list.add(leftOver.pollFirst());
			index++;
		}

		while (index < n) {
			char[] arr = new char[4];

			int k = read4(arr);

			int i = 0;
			for (; i < k && index < n; i++) {
				list.add(arr[i]);
				index++;
			}

			while (i < k) { // Means there is leftOver
				leftOver.addLast(arr[i]);
				i++;
			}

			if (k < 4) { // No more from the stream
				break;
			}
		}

		return index;
	}
}
