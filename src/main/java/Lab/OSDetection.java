package Lab;

import org.apache.commons.lang.SystemUtils;

public class OSDetection {

	public static void main(String[] args) {
		if(SystemUtils.IS_OS_WINDOWS) {
			System.out.println("THIS IS WINDOWS");
		} else if (SystemUtils.IS_OS_MAC) {
			System.out.println("THIS IS MAC");
		} else {
			System.out.println("THIS IS NO OS");
		}

	}

}
