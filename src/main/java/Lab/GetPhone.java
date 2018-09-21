//package Lab;
//
//import java.io.IOException;
//
//import com.testinium.deviceinformation.DeviceInfo;
//import com.testinium.deviceinformation.DeviceInfoImpl;
//import com.testinium.deviceinformation.device.DeviceType;
//import com.testinium.deviceinformation.exception.DeviceNotFoundException;
//import com.testinium.deviceinformation.model.Device;
//
//public class GetPhone {
//
//	public static void main(String[] args) throws IOException, DeviceNotFoundException {
//		DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.ANDROID);
//		 
//		Device device = deviceInfo.getFirstDevice();
//		
//		System.out.println(device.getModelNumber() + " : Nama Device");
//		System.out.println(device.getDeviceProductName() + " : Platform Name");
//		System.out.println(device.getUniqueDeviceID() + " UUID");
//	}
//
//}
