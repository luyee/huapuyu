package chainOfResponsibility;

/**
 * 这个类完全可以省略
 * 
 * @author Anders Zhu
 * 
 */
public class ConcreteHandler extends Handler {
	@Override
	public void handleRequest(String request) {
		if (getNextHandler() != null) {
			System.out.println("无法处理，交给下一步处理");
			nextHandler.handleRequest(request);
		}
		else {
			System.out.println("处理");
		}
	}
}
